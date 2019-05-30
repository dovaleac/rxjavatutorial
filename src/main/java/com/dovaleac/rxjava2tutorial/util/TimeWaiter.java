package com.dovaleac.rxjava2tutorial.util;

import java.util.Random;

public class TimeWaiter {

  private final long maxRandom;

  private TimeWaiter(long maxRandom) {
    this.maxRandom = maxRandom;
  }

  public static TimeWaiter READ_WAITER = new TimeWaiter(40);
  public static TimeWaiter WRITE_WAITER = new TimeWaiter(60);

  public void waitRandom() {
    try {
      Thread.sleep((new Random().nextLong() % maxRandom) + maxRandom);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
