package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InvitationDTO {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GuestStatusDTO {

        @JsonCreator
        public static GuestStatusDTO of(@JsonProperty("request") String guestId, @JsonProperty("accepted") Boolean accepted) {
            return new GuestStatusDTO(guestId, accepted);
        }

        private String guestId;
        private Boolean accepted;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SpecialRequestDTO {

        @JsonCreator
        public static SpecialRequestDTO of(@JsonProperty("request") String request, @JsonProperty("accepted") Boolean accepted,
                @JsonProperty("answer") String answer) {
            return new SpecialRequestDTO(request, accepted, answer);
        }

        private String request;
        private Boolean accepted;
        private String answer;
    }

    @JsonCreator
    public static InvitationDTO of(@JsonProperty("id") String id, @JsonProperty("invitationCode") String invitationCode,
            @JsonProperty("guestStati") List<GuestStatusDTO> guestStati,
            @JsonProperty("specialRequest") SpecialRequestDTO specialRequest) {
        return new InvitationDTO(id, invitationCode, guestStati, specialRequest);
    }

    private String id;
    private String invitationCode;
    private List<GuestStatusDTO> guestStati;
    private SpecialRequestDTO specialRequest;

}
