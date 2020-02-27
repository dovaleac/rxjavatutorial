package com.dovaleac.rxjava2tutorial.services;

import com.dovaleac.rxjava2tutorial.domain.Character;
import com.dovaleac.rxjava2tutorial.domain.House;
import com.dovaleac.rxjava2tutorial.domain.EntryPoint;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.Map;

public class ExerciseService {

  /**
   * Obtain the names of all the characters
   * @param entryPoint The way to access everything
   * @return
   */
  public Flowable<String> getAllCharactersNames(EntryPoint entryPoint) {
    return null;
  }

  /**
   * Obtain the names of all the characters and sort them by the number of letters they have
   * @param entryPoint The way to access everything
   * @return
   */
  public Flowable<String> getAllCharactersNamesSortedByNumberOfLetters(EntryPoint entryPoint) {
    return null;
  }

  /**
   * Obtain all the characters who possess at least one non-empty title.
   * Note: some characters have only one title and it's empty. Those shouldn't be shown. Only
   * non-empty titles
   *
   * Hint: Java 8's Stream class has a very interesting anyMatch(Predicate) method...
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Flowable<Character> getAllCharactersWithTitles(EntryPoint entryPoint) {
    return null;
  }

  /**
   * Obtain the character who has the most titles.
   * @param entryPoint The way to access everything
   * @return
   */
  public Maybe<Character> getCharacterWithMostTitles(EntryPoint entryPoint) {
    return null;
  }

  /**
   * The same as the previous exercise, but returning a Single, as we know at least 1 character
   * exists
   * @param entryPoint The way to access everything
   * @return
   */
  public Single<Character> getCharacterWithMostTitlesAsSingle(EntryPoint entryPoint) {
    return null;
  }

  /**
   * Obtain a map that, for each house, obtains the length of its motto (the house's words, which
   * in the House class appear as the attribute "words"). Please note that the expected map will
   * contain, as key, the house's name, and as value, the number of characters in the house's
   * words attribute.
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Single<Map<String, Integer>> getHousesWithMottoLength(EntryPoint entryPoint) {
    return null;
  }

  /**
   * Obtain all the characters who are lords of anything in the "Dorne" region. That is, obtain
   * all houses in Dorne and afterwards get their lords.
   *
   * Hint: remember there's a getCharacterById method in the ReadService
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Flowable<Character> getAllDornishLords(EntryPoint entryPoint) {
    return null;
  }

  /**
   * The fastest way to put this: get the house's grandsons. Further explanation:
   *
   * Overlord = the house who rules over this house
   * Overlorded by house X = all the houses whose overlord is house X
   *
   * That said, given house X, obtain all houses whose overlord is overlorded itself by house X.
   * That is, if being some house's overlord is being that house's father, you have to find house
   * X's grandsons.
   *
   * Hint: please note the method getOverlordedByHouse in ReadService, which returns the "sons"
   * of the given house.
   *
   * @param entryPoint The way to access everything
   * @param house The house to search for
   * @return
   */
  public Flowable<House> getOverlordedsOverlorded(EntryPoint entryPoint, House house) {
    return null;
  }

  /**
   * Overlord = the house who rules over this house
   * Overlorded by house X = all the houses whose overlord is house X
   *
   * In this exercise you have to add a new house, and afterwards return that house's overlordeds'
   * overlordeds (remember we have an exercise for this last part, so you can call it when you
   * have inserted that house, in order to get its "grandsons")
   * @param entryPoint The way to access everything
   * @param house The house to add
   * @return
   */
  public Flowable<House> getOverlordedOfNewHousesOverlorded(EntryPoint entryPoint, House house) {
    return null;
  }

  /**
   * Add a new house, add a new character, set the character as the ruler of the house and then
   * check the house's ruler
   * @param entryPoint The way to access everything
   * @param newHouse The house to add
   * @param newRuler The house's new ruler
   * @return
   */
  public Single<Character> addHouseAndAddRulerAndCheckItsRuler(EntryPoint entryPoint, House newHouse,
      Character newRuler) {
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
   * Hint: you can't consume a Flowable twice. There must be another way, although not
   * necessarily a very efficient one...
   *
   * @param entryPoint The way to access everything
   * @return
   */
  public Single<Map<String, Double>> getDornishLordsShareOfTitles(EntryPoint entryPoint) {

    return null;
  }


}
