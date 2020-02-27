package com.dovaleac.rxjava2tutorial.domain;

import com.dovaleac.rxjava2tutorial.services.ReadService;
import com.dovaleac.rxjava2tutorial.services.WriteService;

public class EntryPoint {
  private final ReadService readService;
  private final WriteService writeService;

  public EntryPoint(ReadService readService,
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
