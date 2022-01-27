package no.ntnu.appdevapi.model;

import io.swagger.annotations.ApiModelProperty;

public class Product {
  @ApiModelProperty("Name of the product.")
  private String name;
  @ApiModelProperty("Price of the product, decimal in NOK.")
  private double price;
  @ApiModelProperty("Where the product origins from.")
  private String origin;
  @ApiModelProperty("Some info about the product.")
  private String info;

  public Product(String name, double price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
}