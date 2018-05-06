package com.company.prime.service.number.ws.controller;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.prime.service.number.app.PrimeNumberGenerator;
import com.company.prime.service.number.ws.Algorithms;
import com.company.prime.service.number.ws.model.ErrorInfo;
import com.company.prime.service.number.ws.model.PrimeNumberResult;
import com.company.prime.service.number.ws.service.PrimeNumberGeneratorSupplier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@RunWith(Parameterized.class)
public class PrimeNumberControllerTest {

  @Parameters
  public static List<Object[]> parameters() {
    return Arrays.asList(
        new Object[][] {
          {new ObjectMapper(), MediaType.APPLICATION_JSON},
          {new XmlMapper(), MediaType.APPLICATION_XML}
        });
  }

  public @Parameter(0) ObjectMapper mapper;
  public @Parameter(1) MediaType mediaType;

  public @Rule MockitoRule mockito = MockitoJUnit.rule();

  private @Mock PrimeNumberGenerator generator;
  private @Mock PrimeNumberGeneratorSupplier generatorSupplier;

  private @InjectMocks PrimeNumberController primeNumberController;

  // These objects will be magically initialised by the initFields method below
  private JacksonTester<PrimeNumberResult> primeNumberResultSerDe;
  private JacksonTester<ErrorInfo> errorInfoSerDe;

  private MockMvc mvc;

  @Before
  public void init() {
    // Initialises the JacksonTester
    JacksonTester.initFields(this, mapper);
    // MockMvc stand-alone approach
    mvc = MockMvcBuilders.standaloneSetup(primeNumberController).build();
  }

  @Test
  public void shouldReturnPrimesWhenSendingPositiveNumberAndAlgortihmIsDefault() throws Exception {
    given(generatorSupplier.get(Algorithms.DEFAULT)).willReturn(generator);
    given(generator.primesTill(6)).willReturn(Arrays.asList(1, 2, 3, 5));

    MockHttpServletResponse response =
        mvc.perform(get("/primes/6").accept(mediaType)).andReturn().getResponse();

    then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    then(response.getContentAsString())
        .isEqualTo(
            primeNumberResultSerDe
                .write(new PrimeNumberResult(6, Arrays.asList(1, 2, 3, 5)))
                .getJson());
  }

  @Test
  public void shouldReturnPrimesWhenSendingPositiveNumberAndAlgortihmIsBruteForce()
      throws Exception {
    given(generatorSupplier.get(Algorithms.BRUTE_FORCE)).willReturn(generator);
    given(generator.primesTill(8)).willReturn(Arrays.asList(1, 2, 3, 5, 7));

    MockHttpServletResponse response =
        mvc.perform(get("/primes/8?algorithm=bruteForce").accept(mediaType))
            .andReturn()
            .getResponse();

    then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    then(response.getContentAsString())
        .isEqualTo(
            primeNumberResultSerDe
                .write(new PrimeNumberResult(8, Arrays.asList(1, 2, 3, 5, 7)))
                .getJson());
  }

  @Test
  public void shouldReturnPrimesWhenSendingPositiveNumberAndAlgortihmIsHeuristic()
      throws Exception {
    given(generatorSupplier.get(Algorithms.HEURISTIC)).willReturn(generator);
    given(generator.primesTill(8)).willReturn(Arrays.asList(1, 2, 3, 5, 7));

    MockHttpServletResponse response =
        mvc.perform(get("/primes/8?algorithm=heuristic").accept(mediaType))
            .andReturn()
            .getResponse();

    then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    then(response.getContentAsString())
        .isEqualTo(
            primeNumberResultSerDe
                .write(new PrimeNumberResult(8, Arrays.asList(1, 2, 3, 5, 7)))
                .getJson());
  }

  @Test
  public void shouldReturnErrorWhenSendingPositiveNumberAndAlgortihmIsUnknown() throws Exception {
    given(generatorSupplier.get(anyString()))
        .willThrow(new IllegalArgumentException("Unkown algorithm"));

    MockHttpServletResponse response =
        mvc.perform(get("/primes/6?algorithm=unknown").accept(mediaType)).andReturn().getResponse();

    then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    then(response.getContentAsString())
        .isEqualTo(
            errorInfoSerDe
                .write(
                    new ErrorInfo(
                        "http://localhost/primes/6?algorithm=unknown", "Unkown algorithm"))
                .getJson());
  }

  @Test
  public void shouldReturnErrorWhenSendingNegativeNumberAndAlgortihmIsDefault() throws Exception {
    given(generatorSupplier.get(Algorithms.DEFAULT)).willReturn(generator);
    given(generator.primesTill(-1)).willThrow(new IllegalArgumentException("Error message"));

    MockHttpServletResponse response =
        mvc.perform(get("/primes/-1").accept(mediaType)).andReturn().getResponse();

    then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    then(response.getContentAsString())
        .isEqualTo(
            errorInfoSerDe
                .write(new ErrorInfo("http://localhost/primes/-1", "Error message"))
                .getJson());
  }

  @Test
  public void shouldReturnErrorWhenSendingNotANumber() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/primes/a").accept(mediaType)).andReturn().getResponse();

    then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    then(response.getContentAsString())
        .isEqualTo(
            errorInfoSerDe
                .write(new ErrorInfo("http://localhost/primes/a", "Not a number"))
                .getJson());
  }
}
