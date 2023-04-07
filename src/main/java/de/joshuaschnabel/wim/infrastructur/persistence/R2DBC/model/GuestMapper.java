package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.joshuaschnabel.wim.domain.guest.Guest;
import de.joshuaschnabel.wim.domain.guest.GuestId;
import de.joshuaschnabel.wim.domain.invitation.InvitationId;
import de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo.GuestPojo;

@Mapper
public interface GuestMapper {

	public default String guestIdToString(GuestId value) {
		return value.get();
	}

	@Mapping(source = "invitationId", target = "invitation")
	@Mapping(source = "guestType", target = "type")
	@Mapping(source = "firstname", target = "name.firstname.value")
	@Mapping(source = "lastname", target = "name.lastname.value")
	Guest guestPojoTOGuest(GuestPojo guestPojo);

	@Mapping(source = "type", target = "guestType")
	@Mapping(source = "nameNullable.firstname.value", target = "firstname")
	@Mapping(source = "nameNullable.lastname.value", target = "lastname")
	@Mapping(source = "invitation", target = "invitationId")
	@Mapping(source = "create", target = "create")
	GuestPojo guestTOGuestPojo(Guest guest);

	public default String map(Optional<InvitationId> value) {
		if (value.isEmpty()) {
			return null;
		}
		return value.get().get();
	}

	public default Optional<InvitationId> map(String value) {
		if (value == null) {
			return Optional.empty();
		}
		return Optional.of(InvitationId.of(value));
	}

	public default GuestId StringToGuestId(String value) {
		if (value == null) {
			return GuestId.empty();
		}
		return GuestId.of(value);
	}
}
