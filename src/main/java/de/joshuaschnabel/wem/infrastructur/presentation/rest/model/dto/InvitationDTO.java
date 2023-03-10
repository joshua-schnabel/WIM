package de.joshuaschnabel.wem.infrastructur.presentation.rest.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class InvitationDTO {

    @Data
    @Builder
    @Jacksonized
    public static class GuestStatusDTO {
        private String guestId;
        private Boolean accepted;
    }

    private String id;
    private String invitationCode;
    private List<GuestStatusDTO> guestStati;
    private String specialRequest;
    private Boolean specialRequestAccepted;
    private String specialRequestAnswer;

}
