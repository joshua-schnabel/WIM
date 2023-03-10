package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import org.mapstruct.Mapper;
import de.joshuaschnabel.wem.domain.guest.GuestName;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.model.dto.GuestDTO.GuestNameDTO;

@Mapper
public interface GuestNameMapper {

    GuestName guestNameDTOTOguestName(GuestNameDTO guestNameDTO);

    GuestNameDTO guestTOguestNameDTO(GuestName guest);

}
