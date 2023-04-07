package de.joshuaschnabel.wim.domain.ddd.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public abstract class AggregateRoot<Id extends AggregateRootId<?>> {

	@Getter
	private boolean create = false;

	public abstract Id getId();

	protected boolean isNew() {
		return this.create;
	}

	protected void setId(Id id) {
		if (this.getId() != null) {
			throw new RuntimeException("Id is already set");
		}
		this.setIdInternal(id);
	}

	protected abstract void setIdInternal(Id id);

	protected void setNew() {
		this.create = true;
	}

}
