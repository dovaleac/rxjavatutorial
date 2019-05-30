package com.dovaleac.rxjava2tutorial.jackson;

import com.dovaleac.rxjava2tutorial.domain.House;

public class MutableHouse {

  private String id;
  private String name;
  private String region;
  private String words;
  private String currentLord;
  private String overlord;

  public MutableHouse() {
  }

  public House toDomain() {
    return new House(
        Integer.parseInt(id),
        name,
        region,
        words,
        parseIntWithEmpty(currentLord),
        parseIntWithEmpty(overlord)
    );
  }

  private int parseIntWithEmpty(String text) {
    if (text.isEmpty()) {
      return -1;
    } else {
      return Integer.parseInt(text);
    }
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void setWords(String words) {
    this.words = words;
  }

  public void setCurrentLord(String currentLord) {
    this.currentLord = currentLord;
  }

  public void setOverlord(String overlord) {
    this.overlord = overlord;
  }


}
