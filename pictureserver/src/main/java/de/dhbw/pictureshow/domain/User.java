package de.dhbw.pictureshow.domain;


/**
 *
 */
public class User extends PersistentObject {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
