package com.dovaleac.rxjava2tutorial.services;

import com.dovaleac.rxjava2tutorial.domain.Character;
import com.dovaleac.rxjava2tutorial.domain.House;
import com.dovaleac.rxjava2tutorial.domain.Status;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.Map;

public class ExerciseService {

  /**
   * Obtain the names of all the characters
   * @param status The way to access everything
   * @return
   */
  public Flowable<String> getAllCharactersNames(Status status) {
    return null;
  }

  /**
   * Obtain the names of all the characters and sort them by the number of letters they have
   * @param status The way to access everything
   * @return
   */
  public Flowable<String> getAllCharactersNamesSortedByNumberOfLetters(Status status) {
    return null;
  }

  /**
   * Obtain all the characters who possess at least one non-empty title.
   * Note: some characters have only one title and it's empty. Those shouldn't be shown. Only
   * non-empty titles
   * @param status The way to access everything
   * @return
   */
  public Flowable<Character> getAllCharactersWithTitles(Status status) {
    return null;
  }

  /**
   * Obtain the character who has the most titles.
   * @param status The way to access everything
   * @return
   */
  public Maybe<Character> getCharacterWithMostTitles(Status status) {
    return null;
  }

  /**
   * The same as the previous exercise, but returning a Single, as we know at least 1 character
   * exists
   * @param status The way to access everything
   * @return
   */
  public Single<Character> getCharacterWithMostTitlesAsSingle(Status status) {
    return null;
  }

  /**
   * Obtain a map that, for each house, obtains the length of its motto (the house's words)
   * @param status The way to access everything
   * @return
   */
  public Single<Map<String, Integer>> getHousesWithMottoLength(Status status) {
    return null;
  }

  /**
   * Obtain all the characters who are lords of anything in the "Dorne" region. That is, obtain
   * all houses in Dorne and get their lords.
   * @param status The way to access everything
   * @return
   */
  public Flowable<Character> getAllDornishLords(Status status) {
    return null;
  }

  /**
   * Overlord = the house who rules over this house
   * Overlorded by house X = all the houses whose overlord is house X
   *
   * That said, given house X, obtain all houses whose overlord is overlorded itself by house X.
   * That is, if being some house's overlord is being that house's father, you have to find house
   * X's grandsons.
   * Note: please note the method getOverlordedByHouse in ReadService, which returns the "sons"
   * of the given house.
   *
   * @param status The way to access everything
   * @param house The house to search for
   * @return
   */
  public Flowable<House> getOverlordedsOverlorded(Status status, House house) {
    return null;
  }

  /**
   * Create a map that for each dornish lord (remember we have an exercise in which you had to
   * calculate all the dornish lords) specifies which % of all the titles among dornish lords
   * s/he possesses.
   *
   * Examples:
   * - if there are only 2 noblemen and both have 2 titles, both would have 50%
   * - if there are 2 noblemen, nobleman A with 3 titles and nobleman B with 2, A would have 60%
   * and B would have 40%
   *
   * @param status The way to access everything
   * @return
   */
  public Single<Map<String, Double>> getDornishLordsShareOfTitles(Status status) {

    return null;
  }

  /**
   * Overlord = the house who rules over this house
   * Overlorded by house X = all the houses whose overlord is house X
   *
   * In this exercise you have to add a new house, and then return that house's overlordeds'
   * overlordeds (remember we have an exercise for this last part, so you can call it when you
   * have inserted that house, in order to get its "grandsons")
   * @param status The way to access everything
   * @param house The house to add
   * @return
   */
  public Flowable<House> getOverlordedOfNewHousesOverlorded(Status status, House house) {
    return null;
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
    return null;
  }

}
