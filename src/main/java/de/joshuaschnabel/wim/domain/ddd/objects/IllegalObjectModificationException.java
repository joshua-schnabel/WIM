package de.joshuaschnabel.wim.domain.ddd.objects;

import lombok.Getter;

public class IllegalObjectModificationException extends RuntimeException {

    private static final long serialVersionUID = -4807030031393127526L;

    @Getter
    private final String i18nCode;

    public IllegalObjectModificationException(DomainError error) {
        super(error.getDescription());
        this.i18nCode = error.getCode();
    }
}
