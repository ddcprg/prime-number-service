package com.company.prime.service.number.ws.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.company.prime.service.number.app.PrimeNumberGenerator;
import com.company.prime.service.number.ws.Algorithms;
import com.company.prime.service.number.ws.metrics.MetricFactory;
import com.company.prime.service.number.ws.model.ErrorInfo;
import com.company.prime.service.number.ws.model.PrimeNumberResult;
import com.company.prime.service.number.ws.service.PrimeNumberGeneratorSupplier;
import com.google.common.collect.ImmutableMap;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(
  path = "/primes",
  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
@Api(description = "Prime number generator")
@ResponseBody
public class PrimeNumberController {

  private static final String ALGORITHMS = Algorithms.HEURISTIC + "," + Algorithms.BRUTE_FORCE;

  private @Autowired PrimeNumberGeneratorSupplier generatorSupplier;

  private final MetricFactory metricFactory = new MetricFactory(this);

  @ApiOperation("Return all prime numbers up to the given number")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successfully retrieved list of prime numbers"),
    @ApiResponse(
      code = 400,
      message = "The given value is not a number or is less than/equal to zero",
      response = ErrorInfo.class
    )
  })
  @GetMapping(path = "/{number}")
  public PrimeNumberResult getPrimes(
      @PathVariable("number")
          @ApiParam(
            value = "Number up to which the service will generate prime numbers",
            required = true
          )
          final int number,
      @RequestParam(name = "algorithm", defaultValue = Algorithms.HEURISTIC)
          @ApiParam(
            value = "Algorithm to use for prime number verification",
            allowableValues = ALGORITHMS
          )
          final String algorithm) {
    try {
      Map<String, String> tags = ImmutableMap.of("algorithm", algorithm);
      metricFactory.counter("getPrimes", tags).increment();
      return metricFactory
          .timer("getPrimes", tags)
          .recordCallable(
              () -> {
                PrimeNumberGenerator generator = generatorSupplier.get(algorithm);
                return new PrimeNumberResult(number, generator.primesTill(number));
              });
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorInfo invalidInput(HttpServletRequest req, IllegalArgumentException e) {
    return new ErrorInfo(reqUrl(req), e.getMessage());
  }

  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorInfo notANumber(HttpServletRequest req, NumberFormatException e) {
    return new ErrorInfo(reqUrl(req), "Not a number");
  }

  private String reqUrl(HttpServletRequest req) {
    String queryParams = req.getQueryString() == null ? "" : "?" + req.getQueryString();
    return req.getRequestURL().append(queryParams).toString();
  }
}
