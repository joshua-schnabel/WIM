package de.joshuaschnabel.wem.domain.ddd.generators;

import java.nio.ByteBuffer;
import java.util.Random;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.digest.DigestUtils;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateId;

public abstract class RandomStringIdGenerator<Id extends AggregateId<String>>
    extends IdGenerator<String, Id> {

  private static Random random = new Random();
  private final Base32 base32 = new Base32();;

  @Override
  public abstract Id generate();

  @Override
  protected String generateID() {
    final var buffer = ByteBuffer.allocate(Long.BYTES).putLong(random.nextLong());
    final var sha3Hex = new DigestUtils("SHA3-256").digest(buffer.array());
    return this.base32.encodeAsString(sha3Hex).toLowerCase().substring(0, 16);
  }

}
