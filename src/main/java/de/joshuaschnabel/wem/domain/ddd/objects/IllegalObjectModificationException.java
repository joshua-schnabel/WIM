package de.joshuaschnabel.wem.domain.ddd.objects;

import lombok.Getter;

public class IllegalObjectModificationException extends RuntimeException {

  private static final long serialVersionUID = -4807030031393127526L;

  @Getter
  private final String i18nCode;

  public IllegalObjectModificationException(String i18nCode, String message) {
    super(message);
    this.i18nCode = i18nCode;
  }
}
