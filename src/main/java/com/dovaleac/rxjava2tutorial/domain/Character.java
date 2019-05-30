package com.dovaleac.rxjava2tutorial.domain;

import java.util.List;

public class Character {

  private final int id;
  private final String name;
  private final List<String> titles;
  private final List<String> playedBy;

  public Character(int id, String name, List<String> titles,
      List<String> playedBy) {
    this.id = id;
    this.name = name;
    this.titles = titles;
    this.playedBy = playedBy;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<String> getTitles() {
    return titles;
  }

  public List<String> getPlayedBy() {
    return playedBy;
  }

  @Override
  public String toString() {
    return "Character{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", titles=" + titles
        + ", playedBy=" + playedBy
        + '}';
  }
}
