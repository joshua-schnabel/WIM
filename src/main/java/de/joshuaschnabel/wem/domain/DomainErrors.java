package de.joshuaschnabel.wem.domain;

import de.joshuaschnabel.wem.domain.ddd.objects.DomainError;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DomainErrors implements DomainError {
    GUEST_name_cannot_be_changed_afterwards("GUEST_1", "name cannot be change afterwards"), //
    GUEST_guest_is_already_assigned_to_an_invitation("GUEST_2", "guest is already assigned to an invitation");

    private String code;
    private String description;
}
