package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GuestDTO {

    @JsonCreator
    public static GuestDTO of(@JsonProperty("id") String id, @JsonProperty("firstname") String firstname,
            @JsonProperty("lastmame") String lastmame, @JsonProperty("guestType") String guestType) {
        return new GuestDTO(id, firstname, lastmame, guestType);
    }

    private final String id;
    private final String firstname;
    private final String lastmame;
    private final String guestType;

}
