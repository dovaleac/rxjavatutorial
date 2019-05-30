package com.dovaleac.rxjava2tutorial.domain;

import java.util.Objects;

public class House {

  private final int id;
  private final String name;
  private final String region;
  private final String words;
  private final int currentLord;
  private final int overlord;

  public House(int id, String name, String region, String words, int currentLord, int overlord) {
    this.id = id;
    this.name = name;
    this.region = region;
    this.words = words;
    this.currentLord = currentLord;
    this.overlord = overlord;
  }

  public House withNewRuler(int newRulerId) {
    return new House(id, name, region, words, newRulerId, overlord);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getRegion() {
    return region;
  }

  public String getWords() {
    return words;
  }

  public int getCurrentLord() {
    return currentLord;
  }

  public int getOverlord() {
    return overlord;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    House house = (House) obj;

    if (id != house.id) {
      return false;
    }
    if (currentLord != house.currentLord) {
      return false;
    }
    if (overlord != house.overlord) {
      return false;
    }
    if (!Objects.equals(name, house.name)) {
      return false;
    }
    if (!Objects.equals(region, house.region)) {
      return false;
    }
    return Objects.equals(words, house.words);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (region != null ? region.hashCode() : 0);
    result = 31 * result + (words != null ? words.hashCode() : 0);
    result = 31 * result + currentLord;
    result = 31 * result + overlord;
    return result;
  }

  @Override
  public String toString() {
    return "House{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", region='" + region + '\''
        + ", words='" + words + '\''
        + ", currentLord=" + currentLord
        + ", overlord=" + overlord
        + '}';
  }
}
