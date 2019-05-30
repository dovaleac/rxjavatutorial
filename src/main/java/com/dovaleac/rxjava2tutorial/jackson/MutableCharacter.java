package com.dovaleac.rxjava2tutorial.jackson;

import com.dovaleac.rxjava2tutorial.domain.Character;

import java.util.List;
import java.util.Objects;

public class MutableCharacter {
  private String id;
  private String name;
  private List<String> titles;
  private List<String> playedBy;

  public MutableCharacter() {
  }

  public Character toDomain() {
    return new Character(Integer.parseInt(id), name, titles, playedBy);
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setTitles(List<String> titles) {
    this.titles = titles;
  }

  public void setPlayedBy(List<String> playedBy) {
    this.playedBy = playedBy;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    MutableCharacter that = (MutableCharacter) obj;

    if (!Objects.equals(id, that.id)) {
      return false;
    }
    if (!Objects.equals(name, that.name)) {
      return false;
    }
    if (!Objects.equals(titles, that.titles)) {
      return false;
    }
    return Objects.equals(playedBy, that.playedBy);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (titles != null ? titles.hashCode() : 0);
    result = 31 * result + (playedBy != null ? playedBy.hashCode() : 0);
    return result;
  }
}
