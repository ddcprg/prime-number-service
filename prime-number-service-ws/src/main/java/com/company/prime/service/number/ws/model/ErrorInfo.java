package com.company.prime.service.number.ws.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Container for errors")
public class ErrorInfo {

  private String url;
  private String message;

  public ErrorInfo(String url, String message) {
    this.url = url;
    this.message = message;
  }

  @ApiModelProperty("Requested URL")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @ApiModelProperty("Error message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
