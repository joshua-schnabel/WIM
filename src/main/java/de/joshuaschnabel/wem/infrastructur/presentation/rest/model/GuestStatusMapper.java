package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import de.joshuaschnabel.wem.domain.guest.GuestStatus;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.model.dto.InvitationDTO.GuestStatusDTO;

@Mapper
public interface GuestStatusMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "guestId", target = "guest")
    @Mapping(source = "accepted", target = "accepted.value")
    GuestStatus guestStatusDTOTOGuestStatus(GuestStatusDTO guestStatusDTO);

    @Mapping(source = "guest", target = "guestId")
    @Mapping(source = "accepted.value", target = "accepted")
    GuestStatusDTO guestStatusTOGuestStatusDTO(GuestStatus guestStatus);

    default String map(GuestId value) {
        return value.get();
    }

    default GuestId map(String value) {
        return GuestId.of(value);
    }
}
