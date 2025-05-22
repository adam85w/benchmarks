package net.adam85w.benchmark.avro;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@SpringBootApplication
public class AvroApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(AvroApplication.class);

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AvroApplication.class, args);

		User user = new User(1L, "Adam", 28, "adam@example.com");

		int iterations = 10_000_000;
		long totalSerialization = 0;
		long totalDeserialization = 0;
		int totalSize = 0;

		for (int i = 0; i < iterations; i++) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DatumWriter<User> writer = new SpecificDatumWriter<>(User.class);
			BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);

			long startSerialization = System.nanoTime();
			writer.write(user, encoder);
			encoder.flush();
			byte[] avroBytes = out.toByteArray();
			long endSerialization = System.nanoTime();

			totalSize += avroBytes.length;

			ByteArrayInputStream in = new ByteArrayInputStream(avroBytes);
			DatumReader<User> reader = new SpecificDatumReader<>(User.class);
			BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(in, null);

			long startDeserialization = System.nanoTime();
			User deserialized = reader.read(null, decoder);
			long endDeserialization = System.nanoTime();

			totalSerialization += (endSerialization - startSerialization);
			totalDeserialization += (endDeserialization - startDeserialization);
		}

		LOGGER.info("averageSerializedSize: {}", totalSize / iterations);
		LOGGER.info("averageSerializationTimeNs: {}", totalSerialization / iterations);
		LOGGER.info("averageDeserializationTimeNs: {}", totalDeserialization / iterations);
		LOGGER.info("totalSizeMB: {}", (double) totalSize/1_048_576);
		LOGGER.info("totalSerializationTimeS: {}", (double) totalSerialization/1_000_000_000);
		LOGGER.info("totalDeserializationTimeS: {}", (double) totalDeserialization/1_000_000_000);
	}
}
