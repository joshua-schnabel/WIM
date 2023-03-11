package de.joshuaschnabel.wim.infrastructur.presentation.rest.model;

import org.mapstruct.Mapper;
import de.joshuaschnabel.wim.domain.guest.GuestName;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.GuestDTO.GuestNameDTO;

@Mapper
public interface GuestNameMapper {

    GuestName guestNameDTOTOguestName(GuestNameDTO guestNameDTO);

    GuestNameDTO guestTOguestNameDTO(GuestName guest);

}
