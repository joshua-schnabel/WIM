package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(value = "guestStati")
@NoArgsConstructor
@AllArgsConstructor
public class GuestStatusPojo {

	@Id
	@Column("id")
	private Integer id;

	@Column("guestId")
	private String guestId;

	@Column("invitationId")
	private String invitationId;

	@Column("accepted")
	private Boolean accepted;
}