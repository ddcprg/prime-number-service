package com.company.prime.service.number.ws.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.company.prime.service.number.app.PrimeNumberGenerator;
import com.company.prime.service.number.ws.model.ErrorInfo;
import com.company.prime.service.number.ws.model.PrimeNumberResult;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(path = "/primes", produces = "application/json")
@Api(description = "Prime number generator")
@ResponseBody
@Timed
public class PrimeNumberController {

  private @Autowired PrimeNumberGenerator generator;

  @ApiOperation("Return all prime numbers up to the given number")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Successfully retrieved list of prime numbers"),
      @ApiResponse(code = 400, message = "The given value is not a number or is less than/equal to zero", response = ErrorInfo.class) })
  @GetMapping(path = "/{number}")
  public PrimeNumberResult getPrimes(
      @PathVariable("number") @ApiParam(value = "Number up to which the service will generate prime numbers", example = "6") int number) {
    return new PrimeNumberResult(number, generator.primesTill(number));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorInfo invalidInput(HttpServletRequest req, IllegalArgumentException e) {
    return new ErrorInfo(
        req.getRequestURL()
            .toString(),
        e.getMessage());
  }

  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorInfo notANumber(HttpServletRequest req, NumberFormatException e) {
    return new ErrorInfo(
        req.getRequestURL()
            .toString(),
        "Not a number");
  }

}
