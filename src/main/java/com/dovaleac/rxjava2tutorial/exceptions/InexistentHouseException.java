package com.dovaleac.rxjava2tutorial.exceptions;

import com.dovaleac.rxjava2tutorial.domain.House;

public class InexistentHouseException extends Exception {
  public InexistentHouseException(House house) {
    super(String.format("House %s does not exist", house.getName()));
  }
}
