/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.company.prime.service.number.ws;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.prime.service.number.ws.model.ErrorInfo;
import com.company.prime.service.number.ws.model.PrimeNumberResult;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class AppIntegrationTest {

  private @LocalServerPort int port;

  private @Value("${local.management.port}") int mgt;

  private @Autowired TestRestTemplate testRestTemplate;

  @Test
  public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
    @SuppressWarnings("rawtypes")
    ResponseEntity<Map> entity =
        this.testRestTemplate.getForEntity(
            "http://localhost:" + this.mgt + "/actuator/info", Map.class);
    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void shouldReturnPrimesWhenUsingDefaultAlgorithm() throws Exception {
    ResponseEntity<PrimeNumberResult> entity =
        this.testRestTemplate.getForEntity(
            "http://localhost:" + this.port + "/primes/4", PrimeNumberResult.class);
    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    then(entity.getBody()).isEqualTo(new PrimeNumberResult(4, Arrays.asList(1, 2, 3)));
  }

  @Test
  public void shouldReturnPrimesWhenUsingBruteForceAlgorithm() throws Exception {
    ResponseEntity<PrimeNumberResult> entity =
        this.testRestTemplate.getForEntity(
            "http://localhost:" + this.port + "/primes/6?algorithm=bruteForce",
            PrimeNumberResult.class);
    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    then(entity.getBody()).isEqualTo(new PrimeNumberResult(6, Arrays.asList(1, 2, 3, 5)));
  }

  @Test
  public void shouldReturnPrimesWhenUsingHeuristicAlgorithm() throws Exception {
    ResponseEntity<PrimeNumberResult> entity =
        this.testRestTemplate.getForEntity(
            "http://localhost:" + this.port + "/primes/7?algorithm=heuristic",
            PrimeNumberResult.class);
    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    then(entity.getBody()).isEqualTo(new PrimeNumberResult(7, Arrays.asList(1, 2, 3, 5, 7)));
  }

  @Test
  public void shouldReturnErrorWhenUsingUnknownAlgorithm() throws Exception {
    ResponseEntity<ErrorInfo> entity =
        this.testRestTemplate.getForEntity(
            "http://localhost:" + this.port + "/primes/7?algorithm=unknown", ErrorInfo.class);
    then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    then(entity.getBody())
        .isEqualTo(
            new ErrorInfo(
                "http://localhost:" + this.port + "/primes/7?algorithm=unknown",
                "Prime number generator 'unknown' not found"));
  }

  @Test
  public void shouldReturnErrorWhenSendingNegativeNumber() throws Exception {
    ResponseEntity<ErrorInfo> entity =
        this.testRestTemplate.getForEntity(
            "http://localhost:" + this.port + "/primes/-2", ErrorInfo.class);
    then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    then(entity.getBody())
        .isEqualTo(
            new ErrorInfo(
                "http://localhost:" + this.port + "/primes/-2",
                "Parameter must be greater than 0"));
  }
}
