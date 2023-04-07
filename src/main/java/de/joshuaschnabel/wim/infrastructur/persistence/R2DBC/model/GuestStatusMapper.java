package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.joshuaschnabel.wim.domain.guest.GuestId;
import de.joshuaschnabel.wim.domain.guest.GuestStatus;
import de.joshuaschnabel.wim.domain.guest.GuestStatus.GuestStatusId;
import de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo.GuestStatusPojo;

@Mapper
public interface GuestStatusMapper {

	@Mapping(source = "guestId", target = "guest")
	@Mapping(source = "accepted", target = "accepted.value")
	GuestStatus guestStatusPojoTOGuestStatus(GuestStatusPojo guestStatusPojo);

	@Mapping(target = "invitationId", ignore = true)
	@Mapping(source = "guest", target = "guestId")
	@Mapping(source = "accepted.value", target = "accepted")
	GuestStatusPojo guestStatusTOGuestStatusPojo(GuestStatus guestStatus);

	default String map(GuestId value) {
		return value.get();
	}

	default Integer map(GuestStatusId value) {
		if (value == null) {
			return null;
		}
		return value.get();
	}

	default GuestStatusId map(Integer value) {
		return new GuestStatus.GuestStatusId(value);
	}

	default GuestId map(String value) {
		return GuestId.of(value);
	}

}
