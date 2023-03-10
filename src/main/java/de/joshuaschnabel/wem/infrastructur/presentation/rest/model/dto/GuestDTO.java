package de.joshuaschnabel.wem.infrastructur.presentation.rest.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GuestDTO {

    @Data
    @Builder
    @Jacksonized
    public static class GuestNameDTO {
        String firstname;
        String lastname;
    }

    private final String id;
    private final GuestNameDTO name;
    private final String guestType;

}
