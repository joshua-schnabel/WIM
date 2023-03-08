package de.joshuaschnabel.wem.domain.invitation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class InvitationCode {

    private String code;

}
