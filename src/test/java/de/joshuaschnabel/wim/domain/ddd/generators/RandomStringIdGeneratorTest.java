package de.joshuaschnabel.wim.domain.ddd.generators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import de.joshuaschnabel.wim.domain.ddd.objects.AggregateId;

@Tag("UnitTest")
class RandomStringIdGeneratorTest {

	static class Id extends AggregateId<String> {
		public Id(String value) {
			super(value);
		}
	}

	static class IdGenerator extends RandomStringIdGenerator<Id> {
		@Override
		public Id generate() {
			return new Id(this.generateID());
		}
	}

	@Test
	@DisplayName("Test random ID")
	void randomId() {
		final var generator = new IdGenerator();
		assertThat(generator.generate().get()).hasSize(16).matches("[a-z2-7]*");
	}

	@Test
	@DisplayName("Test validate ID")
	void validateId() {
		final var generator = new IdGenerator();
		assertThat(generator.validate("abcdefgh2345abcd")).isTrue();
		assertThat(generator.validate("abc#efgh2345abcd")).isFalse();
		assertThat(generator.validate("abc")).isFalse();
		assertThat(generator.getSpecification()).isEqualTo("String 16 alphanumeric");
	}

}
