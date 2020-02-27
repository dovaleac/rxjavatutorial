package com.dovaleac.rxjava2tutorial.services;

import com.dovaleac.rxjava2tutorial.domain.Character;
import com.dovaleac.rxjava2tutorial.domain.EntryPoint;
import com.dovaleac.rxjava2tutorial.domain.House;
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
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Flowable<String> getAllCharactersNames(EntryPoint entryPoint) {
    return entryPoint.getReadService().getAllCharacters().map(Character::getName);
  }

  /**
   * Obtain the names of all the characters and sort them by the number of letters they have
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Flowable<String> getAllCharactersNamesSortedByNumberOfLetters(EntryPoint entryPoint) {
    return entryPoint.getReadService().getAllCharacters().map(Character::getName)
        .sorted(Comparator.comparingInt(String::length));
  }

  /**
   * Obtain all the characters who possess at least one non-empty title.
   * Note: some characters have only one title and it's empty. Those shouldn't be shown.
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Flowable<Character> getAllCharactersWithTitles(EntryPoint entryPoint) {
    return entryPoint.getReadService().getAllCharacters()
        .filter(character -> character.getTitles().stream()
            .anyMatch(s -> !s.isEmpty()));
  }

  /**
   * Obtain the character who has the most titles.
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Maybe<Character> getCharacterWithMostTitles(EntryPoint entryPoint) {
    return entryPoint.getReadService().getAllCharacters()
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
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Single<Character> getCharacterWithMostTitlesAsSingle(EntryPoint entryPoint) {
    return getCharacterWithMostTitles(entryPoint)
        .toSingle();
  }

  /**
   * Obtain a map that, for each house, obtains its motto (the house's words)
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Single<Map<String, String>> getHousesWithMotto(EntryPoint entryPoint) {
    return entryPoint.getReadService().getAllHouses()
        .toMap(House::getName, House::getWords);
  }

  /**
   * Obtain all the characters who are lords of anything in the "Dorne" region
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Flowable<Character> getAllDornishLords(EntryPoint entryPoint) {
    return entryPoint.getReadService().getAllHouses()
        .filter(house -> Objects.equals(house.getRegion(), "Dorne"))
        .map(House::getCurrentLord)
        .flatMapMaybe(id -> entryPoint.getReadService().getCharacterById(id));
  }

  /**
   * Get all the houses whose overlord house's overlord house is the parameterized one
   * Note: please note the method getOverlordedByHouse in ReadService
   *
   * @param entryPoint The way to access everything
   * @param house      The house to search for
   * @return
   */
  public Flowable<House> getOverlordedsOverlorded(EntryPoint entryPoint, House house) {
    return entryPoint.getReadService().getOverlordedByHouse(house)
        .flatMap(house1 -> entryPoint.getReadService().getOverlordedByHouse(house1))
        .distinct();
  }

  /**
   * Create a map that for each dornish lord (remember we have an exercise in which you had to
   * calculate all the dornish lords) specifies which % of all the titles among dornish lords
   * s/he possesses. E.g: if there are only 2 noblemen and both have 2 titles, both would have 50%
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Single<Map<String, Double>> getDornishLordsShareOfTitles(EntryPoint entryPoint) {

    return getAllDornishLords(entryPoint)
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
   *
   * @param entryPoint The way to access everything
   * @param house      The house to add
   * @return
   */
  public Flowable<House> getOverlordedOfNewHousesOverlorded(EntryPoint entryPoint, House house) {
    return entryPoint.getWriteService().addNewHouse(house)
        .andThen(getOverlordedsOverlorded(entryPoint, house));
  }

  /**
   * Add a new house, add a new character, set the character as the ruler of the house and then
   * check the house's ruler
   *
   * @param entryPoint The way to access everything
   * @param newHouse   The house to add
   * @param newRuler   The house's new ruler
   * @return
   */
  public Single<Character> addHouseAndAddRulerAndCheckItsRuler(EntryPoint entryPoint,
      House newHouse,
      Character newRuler) {
    return Completable.concatArray(
        entryPoint.getWriteService().addNewHouse(newHouse),
        entryPoint.getWriteService().addNewCharacter(newRuler)
    ).andThen(entryPoint.getWriteService().changeHouseRuler(newHouse, newRuler))
        .andThen(entryPoint.getReadService().getHouseById(newHouse.getId()))
        .map(House::getCurrentLord)
        .flatMap(id -> entryPoint.getReadService().getCharacterById(id))
        .toSingle();
  }

}
