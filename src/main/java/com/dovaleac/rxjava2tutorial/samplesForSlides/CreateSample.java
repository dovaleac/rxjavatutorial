package com.dovaleac.rxjava2tutorial.samplesForSlides;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class CreateSample {

  public <T> Single<T> createSingleFromCompletableFuture(CompletableFuture<T> completableFuture) {
    return Single.create(singleEmitter -> {
      try {
        completableFuture.thenAcceptAsync(singleEmitter::onSuccess);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  public Completable createCompletableFromCompletableFuture(
      CompletableFuture<Void> completableFuture) {
    return Completable.create(completableEmitter -> {
      try {
        completableFuture.thenAcceptAsync(aVoid -> completableEmitter.onComplete());
      } catch (Exception e) {
        completableEmitter.onError(e);
      }
    });
  }

  public <T> Maybe<T> createMaybeFromCompletableFuture(
      CompletableFuture<Optional<T>> completableFuture) {
    return Maybe.create(maybeEmitter -> {
      try {
        completableFuture.thenAcceptAsync(t -> {
              if (t.isPresent()) {
                maybeEmitter.onSuccess(t.get());
              } else {
                maybeEmitter.onComplete();
              }
            }
        );
      } catch (Exception e) {
        maybeEmitter.onError(e);
      }
    });
  }

  public <T> Flowable<T> createFlowableFromStream(Stream<T> stream) {
    return Flowable.create(flowableEmitter -> {
      try {
        stream.forEach(flowableEmitter::onNext);
      } catch (Exception e) {
        flowableEmitter.onError(e);
      }
      flowableEmitter.onComplete();
    }, BackpressureStrategy.BUFFER);
  }
}
