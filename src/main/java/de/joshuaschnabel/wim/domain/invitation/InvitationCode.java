package de.joshuaschnabel.wim.domain.invitation;

import java.nio.ByteBuffer;
import java.util.Random;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.digest.DigestUtils;

import de.joshuaschnabel.wim.domain.ddd.type.BasicType;

public class InvitationCode extends BasicType<String> {

	private static Random random = new Random();
	private static Base32 base32 = new Base32();

	public static InvitationCode generate() {
		return new InvitationCode(generateCode());
	}

	private static String generateCode() {
		final var buffer = ByteBuffer.allocate(Long.BYTES).putLong(random.nextLong());
		final var sha3Hex = new DigestUtils("SHA3-256").digest(buffer.array());
		return base32.encodeAsString(sha3Hex).toLowerCase().substring(0, 8);
	}

	public static boolean validate(String id) {
		return id.length() == 8 && id.matches("[a-zA-Z0-9+/]*");
	}

	public InvitationCode(String value) {
		super(value);
	}
}