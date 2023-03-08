package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import java.util.function.Function;

public interface DtoMapper<DomainType, DtoType, DomainIdType, DtoIdType> {

    public default DomainType mapToDomain(DtoType dto) {
        return this.mapToDomainFn().apply(dto);
    }

    public abstract Function<DtoType, DomainType> mapToDomainFn();

    public default DomainIdType mapToDomainId(DtoIdType dto) {
        return this.mapToDomainIdFn().apply(dto);
    }

    public abstract Function<DtoIdType, DomainIdType> mapToDomainIdFn();

    public default DtoType mapToDto(DomainType dto) {
        return this.mapToDtoFn().apply(dto);
    };

    public abstract Function<DomainType, DtoType> mapToDtoFn();;

    public default DtoIdType mapToDtoId(DomainIdType dto) {
        return this.mapToDtoIdFn().apply(dto);
    };

    public abstract Function<DomainIdType, DtoIdType> mapToDtoIdFn();;
}
