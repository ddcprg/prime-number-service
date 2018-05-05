package com.company.prime.service.number.ws.controller;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.prime.service.number.app.PrimeNumberGenerator;
import com.company.prime.service.number.ws.model.ErrorInfo;
import com.company.prime.service.number.ws.model.PrimeNumberResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class PrimeNumberControllerTest {

  private @Mock PrimeNumberGenerator generator;

  private @InjectMocks PrimeNumberController primeNumberController;

  // These objects will be magically initialised by the initFields method below
  private JacksonTester<PrimeNumberResult> primeNumberResultSerDe;
  private JacksonTester<ErrorInfo> errorInfoSerDe;

  private MockMvc mvc;

  @Before
  public void init() {
    // Initialises the JacksonTester
    JacksonTester.initFields(this, new ObjectMapper());
    // MockMvc stand-alone approach
    mvc = MockMvcBuilders.standaloneSetup(primeNumberController)
        .build();
  }

  @Test
  public void getPrimeNumbers() throws Exception {
    given(generator.primesTill(6)).willReturn(Arrays.asList(1, 2, 3, 5));

    MockHttpServletResponse response = mvc.perform(get("/primes/6").accept(MediaType.APPLICATION_JSON))
        .andReturn()
        .getResponse();

    then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    then(response.getContentAsString()).isEqualTo(
        primeNumberResultSerDe.write(new PrimeNumberResult(6, Arrays.asList(1, 2, 3, 5)))
            .getJson());
  }

  @Test
  public void getPrimeNumbersForInvalidLimit() throws Exception {
    given(generator.primesTill(-1)).willThrow(new IllegalArgumentException("Error message"));

    MockHttpServletResponse response = mvc.perform(get("/primes/-1").accept(MediaType.APPLICATION_JSON))
        .andReturn()
        .getResponse();

    then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    then(response.getContentAsString()).isEqualTo(
        errorInfoSerDe.write(new ErrorInfo("http://localhost/primes/-1", "Error message"))
            .getJson());
  }

  @Test
  public void getPrimeNumberForNotANumber() throws Exception {
    MockHttpServletResponse response = mvc.perform(get("/primes/a").accept(MediaType.APPLICATION_JSON))
        .andReturn()
        .getResponse();

    then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    then(response.getContentAsString()).isEqualTo(
        errorInfoSerDe.write(new ErrorInfo("http://localhost/primes/a", "Not a number"))
            .getJson());
  }

}
