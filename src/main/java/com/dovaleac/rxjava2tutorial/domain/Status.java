package com.dovaleac.rxjava2tutorial.domain;

import com.dovaleac.rxjava2tutorial.services.ReadService;
import com.dovaleac.rxjava2tutorial.services.WriteService;

public class Status {
  private final ReadService readService;
  private final WriteService writeService;

  public Status(ReadService readService,
      WriteService writeService) {
    this.readService = readService;
    this.writeService = writeService;
  }

  public ReadService getReadService() {
    return readService;
  }

  public WriteService getWriteService() {
    return writeService;
  }
}
