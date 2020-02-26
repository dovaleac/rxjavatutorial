package com.dovaleac.rxjava2tutorial.services;

import com.dovaleac.rxjava2tutorial.domain.Character;
import com.dovaleac.rxjava2tutorial.domain.House;
import com.dovaleac.rxjava2tutorial.domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseServiceTest {

  private Status status;
  private final ExerciseService service = new ExerciseService();
  public static final House gryffindor = new House(1000, "Gryffindor", "Hogwarts",
      "Wingardium leviosa", 7000, -1);
  private static final Character dumbledore = new Character(7000,
      "Albus Percival Wulfric Dumbledore", List.of("Professor", "Headmaster"),
      List.of("Richard Harris", "Other guy"));

  @BeforeEach
  void setUp() throws IOException {
    status = new ImportService().importStatus();
  }

  @Test
  void getAllCharactersNames() throws Exception {
    String[] expected =
        Files.lines(Paths.get("src", "test", "resources", "ex1")).toArray(String[]::new);
    service.getAllCharactersNames(status)
        .sorted()
        .test()
        .assertResult(expected);
  }

  @Test
  void getAllCharactersNamesSortedByNumberOfLetters() throws Exception {
    String[] expected =
        Files.lines(Paths.get("src", "test", "resources", "ex2")).toArray(String[]::new);
    service.getAllCharactersNamesSortedByNumberOfLetters(status)
        .test()
        .assertResult(expected);
  }

  @Test
  void getAllCharactersWithTitles() throws Exception {
    Integer[] expected =
        Files.lines(Paths.get("src", "test", "resources", "ex3"))
            .map(Integer::parseInt)
            .toArray(Integer[]::new);
    service.getAllCharactersWithTitles(status)
        .map(Character::getId)
        .test()
        .assertResult(expected);
  }

  @Test
  void getCharacterWithMostTitles() throws Exception {
    service.getCharacterWithMostTitles(status)
        .toSingle()
        .map(Character::getId)
        .test()
        .assertResult(12);
  }

  @Test
  void getCharacterWithMostTitlesAsSingle() throws Exception {
    service.getCharacterWithMostTitlesAsSingle(status)
        .map(Character::getId)
        .test()
        .assertResult(12);
  }

  @Test
  void getHousesWithMottoLength() throws Exception {
    Map<String, Integer> expected = Files.lines(Paths.get("src", "test", "resources", "ex6"))
        .map(s -> s.split(Pattern.quote("||")))
        .collect(Collectors.toMap(star -> star[0], star -> Integer.parseInt(star[1])));
    service.getHousesWithMottoLength(status)
        .test()
        .assertResult(expected);
  }

  @Test
  void getAllDornishLords() throws Exception {
    Integer[] expected =
        Files.lines(Paths.get("src", "test", "resources", "ex7"))
            .map(Integer::parseInt)
            .toArray(Integer[]::new);
    service.getAllDornishLords(status)
        .map(Character::getId)
        .sorted()
        .test()
        .assertResult(expected);

  }

  @Test
  void getOverlordedsOverlorded() throws Exception {
    Integer[] expected =
        Files.lines(Paths.get("src", "test", "resources", "ex8"))
            .map(Integer::parseInt)
            .toArray(Integer[]::new);
    status.getReadService()
        .getHouseById(16)
        .flatMapPublisher(house -> service.getOverlordedsOverlorded(status, house))
        .map(House::getId)
        .sorted()
        .test()
        .assertResult(expected);
  }

  @Test
  void getDornishLordsShareOfTitles() throws Exception {
    Map<String, String> expected = Files.lines(Paths.get("src", "test", "resources", "ex9"))
        .map(s -> s.split(Pattern.quote("||")))
        .collect(Collectors.toMap(star -> star[0], star -> star[1]));
    service.getDornishLordsShareOfTitles(status)
        .subscribe(stringDoubleMap -> {
          assertEquals(expected.size(), stringDoubleMap.size());
          stringDoubleMap.forEach((s, aDouble) ->
              assertEquals(expected.get(s), String.format("%g", aDouble)));
        });
  }

  @Test
  void getOverlordedOfNewHousesOverlorded() throws Exception {
    List<Integer> expected = List.of(21);
    service.getOverlordedOfNewHousesOverlorded(status, gryffindor)
        .map(House::getId)
        .toList()
        .test()
        .assertResult(expected);
  }

  @Test
  void addHouseAndAddRulerAndCheckItsRuler() throws Exception {
    service.addHouseAndAddRulerAndCheckItsRuler(status, gryffindor, dumbledore)
        .test()
        .assertResult(dumbledore);
  }
}