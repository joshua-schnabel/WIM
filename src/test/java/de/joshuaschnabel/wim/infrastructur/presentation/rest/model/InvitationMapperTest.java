package de.joshuaschnabel.wim.infrastructur.presentation.rest.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.InvitationDTO;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.InvitationDTO.GuestStatusDTO;

class InvitationMapperTest {

    private InvitationMapper invitationMapper = Mappers.getMapper(InvitationMapper.class);

    @Test
    @DisplayName("Test full DTO to DomainObject mapping both ways")
    void testInvitationMapper() {
        var guestStatusDTO2 = GuestStatusDTO.builder().guestId("JBSWY3DPGEZDGNB2").accepted(true).build();
        var guestStatusDTO3 = GuestStatusDTO.builder().guestId("JHSWY3DPGEZDGNB3").accepted(true).build();
        var guestStatusDTO4 = GuestStatusDTO.builder().guestId("JISWY3DPGEZDGNB4").accepted(true).build();

        var invitationDTO = InvitationDTO.builder().id("MZSXOZTFO4QGMIBA").invitationCode("test123")
                .guestStati(List.of(guestStatusDTO2, guestStatusDTO3, guestStatusDTO4))
                .specialRequest("Kannst du Kuchen bringen?").specialRequestAccepted(false).specialRequestAnswer("NEIN!").build();

        var result = this.invitationMapper.invitationDTOTOInvitation(invitationDTO);

        assertThat(result.getId().get()).isEqualToIgnoringCase("MZSXOZTFO4QGMIBA");
        assertThat(result.getGuestStati()).hasSize(3)
                .anySatisfy(x -> assertThat(x.getGuest().get()).isEqualToIgnoringCase("JBSWY3DPGEZDGNB2"))
                .anySatisfy(x -> assertThat(x.getGuest().get()).isEqualToIgnoringCase("JHSWY3DPGEZDGNB3"))
                .anySatisfy(x -> assertThat(x.getGuest().get()).isEqualToIgnoringCase("JISWY3DPGEZDGNB4"))
                .allSatisfy(x -> assertThat(x.getAccepted().getValue()).isTrue());
        assertThat(result.getInvitationCode().getValue()).isEqualTo("test123");

        assertThat(result.getSpecialRequest().getAccepted().getValue()).isFalse();
        assertThat(result.getSpecialRequest().getRequest().getValue()).isEqualTo("Kannst du Kuchen bringen?");
        assertThat(result.getSpecialRequest().getAnswer().getValue()).isEqualTo("NEIN!");

        var resultBack = this.invitationMapper.invitationTOinvitationDTO(result);

        assertThat(resultBack.getId()).isEqualToIgnoringCase("MZSXOZTFO4QGMIBA");
        assertThat(resultBack.getInvitationCode()).isEqualToIgnoringCase("test123");
        assertThat(resultBack.getSpecialRequest()).isEqualToIgnoringCase("Kannst du Kuchen bringen?");
        assertThat(resultBack.getSpecialRequestAnswer()).isEqualToIgnoringCase("NEIN!");
        assertThat(resultBack.getSpecialRequestAccepted()).isFalse();
        assertThat(resultBack.getGuestStati()).hasSize(3)
                .anySatisfy(x -> assertThat(x.getGuestId()).isEqualToIgnoringCase("JBSWY3DPGEZDGNB2"))
                .anySatisfy(x -> assertThat(x.getGuestId()).isEqualToIgnoringCase("JHSWY3DPGEZDGNB3"))
                .anySatisfy(x -> assertThat(x.getGuestId()).isEqualToIgnoringCase("JISWY3DPGEZDGNB4"))
                .allSatisfy(x -> assertThat(x.getAccepted()).isTrue());
    }

}
