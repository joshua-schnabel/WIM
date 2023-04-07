package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo.GuestPojo;

interface GuestDBRepository extends R2dbcRepository<GuestPojo, String> {
}