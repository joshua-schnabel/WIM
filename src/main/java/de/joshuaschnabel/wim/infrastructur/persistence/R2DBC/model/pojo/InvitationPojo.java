package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(value = "invitations")
@NoArgsConstructor
@AllArgsConstructor
public class InvitationPojo implements Persistable<String> {

	@Id
	@Column("id")
	private String id;

	@Column("code")
	private String code;

	@Column("name")
	private String name;

	@Column("specialRequest")
	private String specialRequest;

	@Column("specialRequestAccepted")
	private Boolean specialRequestAccepted;

	@Column("specialRequestAnswer")
	private String specialRequestAnswer;

	@Column("status")
	private InvitationStatusPojo status;

	@Transient
	private List<GuestStatusPojo> guestStati;

	@Transient
	private boolean create = false;

	@Override
	public boolean isNew() {
		return this.create;
	}
}
