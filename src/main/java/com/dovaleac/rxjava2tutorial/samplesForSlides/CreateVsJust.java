package com.dovaleac.rxjava2tutorial.samplesForSlides;

import io.reactivex.Single;

public abstract class CreateVsJust<T> {

  protected abstract T getT(); //potentially costly

  public Single<T> getSingleByJust() {
    return Single.just(getT());
  }

  public Single<T> getSingleByCreate() {
    return Single.create(emitter -> {
          try {
            T t = getT();
            emitter.onSuccess(t);
          } catch (Exception e) {
            emitter.onError(e);
          }
        }
    );
  }
}
