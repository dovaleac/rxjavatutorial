package com.dovaleac.rxjava2tutorial.services;

import com.dovaleac.rxjava2tutorial.domain.Character;
import com.dovaleac.rxjava2tutorial.domain.House;
import com.dovaleac.rxjava2tutorial.exceptions.InexistentHouseException;
import com.dovaleac.rxjava2tutorial.util.TimeWaiter;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.List;
import java.util.Random;

public class WriteService {
  private final List<Character> characters;
  private final List<House> houses;
  private final TimeWaiter waiter = TimeWaiter.WRITE_WAITER;

  public WriteService(List<Character> characters,
      List<House> houses) {
    this.characters = characters;
    this.houses = houses;
  }

  public Completable addNewHouse(House house) {
    return Completable.fromAction(() -> {
      waiter.waitRandom();
      houses.add(house);
    });
  }

  public Completable addNewCharacter(Character character) {
    return Completable.fromAction(() -> {
      waiter.waitRandom();
      characters.add(character);
    });
  }

  public Completable changeHouseRuler(House house, Character newRuler) {
    return Completable.fromAction(() -> {
      waiter.waitRandom();
      if (!houses.remove(house)) {
        throw new InexistentHouseException(house);
      }
      houses.add(house.withNewRuler(newRuler.getId()));
    });
  }
}
