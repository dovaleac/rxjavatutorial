package com.dovaleac.rxjava2tutorial.services;

import com.dovaleac.rxjava2tutorial.domain.Character;
import com.dovaleac.rxjava2tutorial.domain.House;
import com.dovaleac.rxjava2tutorial.domain.Status;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExerciseService {

  public Flowable<String> getAllCharactersNames(Status status) {
    return status.getReadService().getAllCharacters().map(Character::getName);
  }

  public Flowable<String> getAllCharactersNamesSortedByNumberOfLetters(Status status) {
    return status.getReadService().getAllCharacters().map(Character::getName)
        .sorted(Comparator.comparingInt(String::length));
  }

  public Flowable<Character> getAllCharactersWithTitles(Status status) {
    return status.getReadService().getAllCharacters()
        .filter(character -> !character.getTitles().isEmpty()
            && !(character.getTitles().size() == 1 && character.getTitles().get(0).isEmpty()));
  }

  public Maybe<Character> getCharacterWithMostTitles(Status status) {
    return status.getReadService().getAllCharacters()
        .reduce((character, character2) -> {
          int titles1 = character.getTitles().size();
          int titles2 = character2.getTitles().size();
          if (titles2 > titles1) {
            return character2;
          } else {
            return character;
          }
        });
  }

  public Single<Character> getCharacterWithMostTitlesAsSingle(Status status) {
    return getCharacterWithMostTitles(status)
        .toSingle();
  }

  public Single<Map<String, Integer>> getHousesWithMottoLength(Status status) {
    return status.getReadService().getAllHouses()
        .toMap(House::getName, house -> house.getWords().length());
  }

  public Flowable<Character> getAllDornishLords(Status status) {
    return status.getReadService().getAllHouses()
        .filter(house -> Objects.equals(house.getRegion(), "Dorne"))
        .map(House::getCurrentLord)
        .flatMapMaybe(id -> status.getReadService().getCharacterById(id));
  }

  public Flowable<House> getOverlordedsOverlorded(Status status, House house) {
    return status.getReadService().getOverlordedByHouse(house)
        .flatMap(house1 -> status.getReadService().getOverlordedByHouse(house1))
        .distinct();
  }

  public Single<Map<String, Double>> getDornishLordsShareOfTitles(Status status) {

    return getAllDornishLords(status)
        .toList()
        .map(list -> {
          int totalTitles = list.stream()
              .map(Character::getTitles)
              .map(List::size).mapToInt(x -> x)
              .sum();
          return list.stream()
              .collect(Collectors.toMap(Character::getName,
                  character -> character.getTitles().size() / (double) totalTitles));
        });
  }

  public Flowable<House> getOverlordedOfNewHousesOverlorded(Status status, House house) {
    return status.getWriteService().addNewHouse(house)
        .andThen(getOverlordedsOverlorded(status, house));
  }

  public Single<Character> addHouseAndAddRulerAndCheckItsRuler(Status status, House newHouse,
      Character newRuler) {
    return Completable.concatArray(
        status.getWriteService().addNewHouse(newHouse),
        status.getWriteService().addNewCharacter(newRuler)
    ).andThen(status.getWriteService().changeHouseRuler(newHouse, newRuler))
        .andThen(status.getReadService().getHouseById(newHouse.getId()))
        .map(House::getCurrentLord)
        .flatMap(id -> status.getReadService().getCharacterById(id))
        .toSingle();
  }

  /*
  * recursively get overlorded
  * */
  public Flowable<House> getAllOverlordedByHouse(Status status, House overlord) {
    return status.getReadService()
        .getOverlordedByHouse(overlord)
        .flatMap()
  }

}
