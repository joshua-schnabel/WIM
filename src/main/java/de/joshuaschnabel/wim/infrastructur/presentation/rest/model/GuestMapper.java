package de.joshuaschnabel.wim.infrastructur.presentation.rest.model;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import de.joshuaschnabel.wim.domain.guest.Guest;
import de.joshuaschnabel.wim.domain.guest.GuestId;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.GuestDTO;

@Mapper
public interface GuestMapper {

	@Mapping(target = "invitation", ignore = true)
	@Mapping(source = "guestType", target = "type")
	@Mapping(source = "firstname", target = "name.firstname.value")
	@Mapping(source = "lastname", target = "name.lastname.value")
	Guest guestDTOTOguest(GuestDTO guestDTO);

	public default String guestIdToString(GuestId value) {
		return value.get();
	}

	@Mapping(source = "type", target = "guestType")
	@Mapping(source = "nameNullable.firstname.value", target = "firstname")
	@Mapping(source = "nameNullable.lastname.value", target = "lastname")
	GuestDTO guestTOguestDTO(Guest guest);

	public default GuestId StringToGuestId(String value) {
		return GuestId.of(value);
	}

	@Named("unwrap")
	default <T> T unwrap(Optional<T> optional) {
		return optional.orElse(null);
	}

	@Named("wrap")
	default <T> Optional<T> wrap(T value) {
		return Optional.ofNullable(value);
	}
}
