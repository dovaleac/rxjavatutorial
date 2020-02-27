package com.dovaleac.rxjava2tutorial.services;

import com.dovaleac.rxjava2tutorial.domain.Character;
import com.dovaleac.rxjava2tutorial.domain.House;
import com.dovaleac.rxjava2tutorial.domain.EntryPoint;
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

  private EntryPoint entryPoint;
  private final ExerciseService service = new ExerciseService();
  public static final House gryffindor = new House(1000, "Gryffindor", "Hogwarts",
      "Wingardium leviosa", 7000, -1);
  private static final Character dumbledore = new Character(7000,
      "Albus Percival Wulfric Dumbledore", List.of("Professor", "Headmaster"),
      List.of("Richard Harris", "Other guy"));

  @BeforeEach
  void setUp() throws IOException {
    entryPoint =  new ImportService().importStatus();
  }

  @Test
  void ex1() throws Exception {
    String[] expected =
        Files.lines(Paths.get("src", "test", "resources", "ex1")).toArray(String[]::new);
    service.getAllCharactersNames(entryPoint)
        .sorted()
        .test()
        .assertResult(expected);
  }

  @Test
  void ex2() throws Exception {
    String[] expected =
        Files.lines(Paths.get("src", "test", "resources", "ex2")).toArray(String[]::new);
    service.getAllCharactersNamesSortedByNumberOfLetters(entryPoint)
        .test()
        .assertResult(expected);
  }

  @Test
  void ex3() throws Exception {
    Integer[] expected =
        Files.lines(Paths.get("src", "test", "resources", "ex3"))
            .map(Integer::parseInt)
            .toArray(Integer[]::new);
    service.getAllCharactersWithTitles(entryPoint)
        .map(Character::getId)
        .test()
        .assertResult(expected);
  }

  @Test
  void ex4() throws Exception {
//        .forEach(System.out::println);
    service.getCharacterWithMostTitles(entryPoint)
        .toSingle()
        .map(Character::getId)
        .test()
        .assertResult(12);
  }

  @Test
  void ex5() throws Exception {
//        .forEach(System.out::println);
    service.getCharacterWithMostTitlesAsSingle(entryPoint)
        .map(Character::getId)
        .test()
        .assertResult(12);
  }

  @Test
  void ex6() throws Exception {
    Map<String, String> expected = Files.lines(Paths.get("src", "test", "resources", "ex6"))
        .map(s -> s.split(Pattern.quote("||")))
        .collect(Collectors.toMap(star -> star[0], star -> {
          if (star.length < 2) {
            return "";
          }
          return star[1];
        }));

    service.getHousesWithMotto(entryPoint)
        .test()
        .assertResult(expected);
  }

  @Test
  void ex7() throws Exception {
    Integer[] expected =
        Files.lines(Paths.get("src", "test", "resources", "ex7"))
            .map(Integer::parseInt)
            .toArray(Integer[]::new);
    service.getAllDornishLords(entryPoint)
        .map(Character::getId)
        .sorted()
        .test()
        .assertResult(expected);

  }

  @Test
  void ex8() throws Exception {
    Integer[] expected =
        Files.lines(Paths.get("src", "test", "resources", "ex8"))
            .map(Integer::parseInt)
            .toArray(Integer[]::new);
    entryPoint.getReadService()
        .getHouseById(16)
        .flatMapPublisher(house -> service.getOverlordedsOverlorded(entryPoint, house))
        .map(House::getId)
        .sorted()
        .test()
        .assertResult(expected);
  }

  @Test
  void ex9() throws Exception {
    Map<String, String> expected = Files.lines(Paths.get("src", "test", "resources", "ex9"))
        .map(s -> s.split(Pattern.quote("||")))
        .collect(Collectors.toMap(star -> star[0], star -> star[1]));
    service.getDornishLordsShareOfTitles(entryPoint)
        .subscribe(stringDoubleMap -> {
          assertEquals(expected.size(), stringDoubleMap.size());
          stringDoubleMap.forEach((s, aDouble) ->
              assertEquals(expected.get(s), String.format("%g", aDouble)));
        });
  }

  @Test
  void ex10() throws Exception {
    List<Integer> expected = List.of(21);
    service.getOverlordedOfNewHousesOverlorded(entryPoint, gryffindor)
        .map(House::getId)
        .toList()
        .test()
        .assertResult(expected);
  }

  @Test
  void ex11() throws Exception {
    service.addHouseAndAddRulerAndCheckItsRuler(entryPoint, gryffindor, dumbledore)
        .test()
        .assertResult(dumbledore);
  }
}