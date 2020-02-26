package com.dovaleac.rxjava2tutorial.samplesForSlides;

import io.reactivex.Single;

public abstract class SimpleMapping<T, U> {

  protected abstract Single<T> getT();

  protected abstract U processT(T t);

  public Single<U> returnUSingle() {
    return getT()
        .map(this::processT);
  }

  public U returnU() {
    Single<T> t = getT();
    return processT(null);
  }
}
