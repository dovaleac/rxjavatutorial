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

  /**
   * Obtain the names of all the characters
   * @param status The way to access everything
   * @return
   */
  public Flowable<String> getAllCharactersNames(Status status) {
    return status.getReadService().getAllCharacters().map(Character::getName);
  }

  /**
   * Obtain the names of all the characters and sort them by the number of letters they have
   * @param status The way to access everything
   * @return
   */
  public Flowable<String> getAllCharactersNamesSortedByNumberOfLetters(Status status) {
    return status.getReadService().getAllCharacters().map(Character::getName)
        .sorted(Comparator.comparingInt(String::length));
  }

  /**
   * Obtain all the characters who possess at least one non-empty title.
   * Note: some characters have only one title and it's empty. Those shouldn't be shown.
   * @param status The way to access everything
   * @return
   */
  public Flowable<Character> getAllCharactersWithTitles(Status status) {
    return status.getReadService().getAllCharacters()
        .filter(character -> !character.getTitles().isEmpty()
            && !(character.getTitles().size() == 1 && character.getTitles().get(0).isEmpty()));
  }

  /**
   * Obtain the character who has the most titles.
   * @param status The way to access everything
   * @return
   */
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

  /**
   * The same as the previous exercise, but returning a Single, as we know at least 1 character
   * exists
   * @param status The way to access everything
   * @return
   */
  public Single<Character> getCharacterWithMostTitlesAsSingle(Status status) {
    return getCharacterWithMostTitles(status)
        .toSingle();
  }

  /**
   * Obtain a map that, for each house, obtains the length of its motto (the house's words)
   * @param status The way to access everything
   * @return
   */
  public Single<Map<String, Integer>> getHousesWithMottoLength(Status status) {
    return status.getReadService().getAllHouses()
        .toMap(House::getName, house -> house.getWords().length());
  }

  /**
   * Obtain all the characters who are lords of anything in the "Dorne" region
   * @param status The way to access everything
   * @return
   */
  public Flowable<Character> getAllDornishLords(Status status) {
    return status.getReadService().getAllHouses()
        .filter(house -> Objects.equals(house.getRegion(), "Dorne"))
        .map(House::getCurrentLord)
        .flatMapMaybe(id -> status.getReadService().getCharacterById(id));
  }

  /**
   * Get all the houses whose overlord house's overlord house is the parameterized one
   * Note: please note the method getOverlordedByHouse in ReadService
   * @param status The way to access everything
   * @param house The house to search for
   * @return
   */
  public Flowable<House> getOverlordedsOverlorded(Status status, House house) {
    return status.getReadService().getOverlordedByHouse(house)
        .flatMap(house1 -> status.getReadService().getOverlordedByHouse(house1))
        .distinct();
  }

  /**
   * Create a map that for each dornish lord (remember we have an exercise in which you had to
   * calculate all the dornish lords) specifies which % of all the titles among dornish lords
   * s/he possesses. E.g: if there are only 2 noblemen and both have 2 titles, both would have 50%
   * @param status The way to access everything
   * @return
   */
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

  /**
   * In this exercise you have to add a new house, and then return that house's overlordeds'
   * overlordeds (remember we have an exercise for this last part)
   * @param status The way to access everything
   * @param house The house to add
   * @return
   */
  public Flowable<House> getOverlordedOfNewHousesOverlorded(Status status, House house) {
    return status.getWriteService().addNewHouse(house)
        .andThen(getOverlordedsOverlorded(status, house));
  }

  /**
   * Add a new house, add a new character, set the character as the ruler of the house and then
   * check the house's ruler
   * @param status The way to access everything
   * @param newHouse The house to add
   * @param newRuler The house's new ruler
   * @return
   */
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

}
