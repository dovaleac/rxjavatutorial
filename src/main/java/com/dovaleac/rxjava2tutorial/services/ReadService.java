package com.dovaleac.rxjava2tutorial.services;

import com.dovaleac.rxjava2tutorial.domain.Character;
import com.dovaleac.rxjava2tutorial.domain.House;
import com.dovaleac.rxjava2tutorial.util.TimeWaiter;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.List;
import java.util.Random;

public class ReadService {
  private final List<Character> characters;
  private final List<House> houses;
  private final TimeWaiter waiter = TimeWaiter.READ_WAITER;

  public ReadService(List<Character> characters,
      List<House> houses) {
    this.characters = characters;
    this.houses = houses;
  }

  public Flowable<Character> getAllCharacters() {
    return Flowable.create(flowableEmitter -> {
      waiter.waitRandom();
      characters.forEach(flowableEmitter::onNext);
      flowableEmitter.onComplete();
    }, BackpressureStrategy.BUFFER);
  }

  public Flowable<House> getAllHouses() {
    return Flowable.create(flowableEmitter -> {
      waiter.waitRandom();
      houses.forEach(flowableEmitter::onNext);
      flowableEmitter.onComplete();
    }, BackpressureStrategy.BUFFER);
  }

  public Single<Character> getRandomCharacter() {
    return Single.create(singleEmitter -> {
      waiter.waitRandom();
      singleEmitter.onSuccess(characters.get(new Random().nextInt(characters.size() - 1)));
    });
  }

  public Single<House> getRandomHouse() {
    return Single.create(singleEmitter -> {
      waiter.waitRandom();
      singleEmitter.onSuccess(houses.get(new Random().nextInt(houses.size()-1)));
    });
  }

  public Maybe<Character> getCharacterById(int id) {
    return getAllCharacters()
        .filter(character -> character.getId() == id)
        .firstElement();
  }

  public Maybe<House> getHouseById(int id) {
    return getAllHouses()
        .filter(house -> house.getId() == id)
        .firstElement();
  }
}
