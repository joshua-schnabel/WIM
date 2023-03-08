package de.joshuaschnabel.wem.domain.ddd.objects;

public interface DomainError {

    public abstract String getCode();

    public abstract String getDescription();

}
