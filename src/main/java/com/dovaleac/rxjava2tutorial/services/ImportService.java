package com.dovaleac.rxjava2tutorial.services;

import com.dovaleac.rxjava2tutorial.domain.Character;
import com.dovaleac.rxjava2tutorial.domain.House;
import com.dovaleac.rxjava2tutorial.jackson.MutableCharacter;
import com.dovaleac.rxjava2tutorial.jackson.MutableHouse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImportService {

  public List<Character> importAllCharacters() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return Stream.of(objectMapper.readValue(Paths.get("src", "main", "resources",
        "characters.txt").toFile(),
        MutableCharacter[].class))
        .map(MutableCharacter::toDomain)
        .collect(Collectors.toList());
  }

  public List<House> importAllHouses() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return Stream.of(objectMapper.readValue(Paths.get("src", "main", "resources",
        "houses.txt").toFile(),
        MutableHouse[].class))
        .map(MutableHouse::toDomain)
        .collect(Collectors.toList());
  }

}
