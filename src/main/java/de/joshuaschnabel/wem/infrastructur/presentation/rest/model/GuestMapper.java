package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import java.util.Optional;
import java.util.function.Function;

import de.joshuaschnabel.wem.domain.guest.Guest;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import de.joshuaschnabel.wem.domain.guest.GuestType;
import de.joshuaschnabel.wem.domain.guest.Name;

public class GuestMapper {

	private static final class MapperDTOToGuest
			implements
				Function<GuestDTO, Guest> {
		@Override
		public Guest apply(GuestDTO dto) {
			final var id = mapDtoIdToGuest.apply(dto.getId());
			return Guest.builder().id(id).name(this.mapToName(dto))
					.type(this.mapTypeToDTO(dto)).build();
		}

		private Optional<Name> mapToName(GuestDTO dto) {
			if (dto.getLastmame() == null || dto.getFirstname() == null) {
				return Optional.empty();
			}
			return Optional.of(Name.of(dto.getFirstname(), dto.getLastmame()));
		}

		private GuestType mapTypeToDTO(GuestDTO guest) {
			return GuestType.valueOf(guest.getGuestType());
		}
	}

	private static final class MapperGuestToDTO
			implements
				Function<Guest, GuestDTO> {
		@Override
		public GuestDTO apply(Guest guest) {
			return GuestDTO.of(this.mapIdToDTO(guest),
					this.mapFirstnameToDTO(guest), this.mapLastnameToDTO(guest),
					this.mapTypeToDTO(guest));
		}

		private String mapFirstnameToDTO(Guest guest) {
			if (guest.getName().isPresent()) {
				return guest.getName().get().getFirstname();
			}
			return null;
		}

		private String mapIdToDTO(Guest guest) {
			return guest.getId().get();
		}

		private String mapLastnameToDTO(Guest guest) {
			if (guest.getName().isPresent()) {
				return guest.getName().get().getLastname();
			}
			return null;
		}

		private String mapTypeToDTO(Guest guest) {
			if (guest.getType() == null) {
				throw new IllegalStateException("Type not defind");
			}
			return guest.getType().name();
		}
	}

	public static Function<Guest, GuestDTO> mapToDTO = new MapperGuestToDTO();
	public static Function<String, GuestId> mapDtoIdToGuest = GuestId::of;
	public static Function<GuestDTO, Guest> mapDtoToGuest = new MapperDTOToGuest();

}
