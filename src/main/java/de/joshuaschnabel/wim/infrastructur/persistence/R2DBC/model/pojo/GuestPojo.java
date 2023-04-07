package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(value = "guests")
@NoArgsConstructor
@AllArgsConstructor
public class GuestPojo implements Persistable<String> {

	@Id
	@Column("id")
	private String id;

	@Column("firstname")
	private String firstname;

	@Column("lastname")
	private String lastname;

	@Column("guestType")
	private GuestTypePojo guestType;

	@Column("invitationId")
	private String invitationId;

	@Transient
	private boolean create = false;

	@Override
	public boolean isNew() {
		return this.create;
	}
}
