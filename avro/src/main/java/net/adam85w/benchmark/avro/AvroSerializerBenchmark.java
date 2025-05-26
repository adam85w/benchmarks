package net.adam85w.benchmark.avro;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroSerializerBenchmark {

	private static final Logger LOGGER = LoggerFactory.getLogger(AvroSerializerBenchmark.class);

	static void test() throws IOException {
		User user = new User(1L, "Adam", 40, "adam@example.com");

		int iterations = 10_000_000;
		long totalSerialization = 0;

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DatumWriter<User> writer = new SpecificDatumWriter<>(User.class);
		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);

		for (int i = 0; i < iterations; i++) {
			long startSerialization = System.nanoTime();
			writer.write(user, encoder);
			encoder.flush();
			long endSerialization = System.nanoTime();
			totalSerialization += (endSerialization - startSerialization);
		}

		LOGGER.info("averageSerializationTimeNs: {}", totalSerialization/iterations);
		LOGGER.info("totalSerializationTimeS: {}", (double) totalSerialization/1_000_000_000);
	}
}
