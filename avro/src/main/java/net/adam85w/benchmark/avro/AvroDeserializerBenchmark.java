package net.adam85w.benchmark.avro;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class AvroDeserializerBenchmark {

	private static final Logger LOGGER = LoggerFactory.getLogger(AvroDeserializerBenchmark.class);

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AvroDeserializerBenchmark.class, args);

		int iterations = 10_000_000;
		long totalDeserialization = 0;

		InputStream inputStream = new FileInputStream("user_avro.bin");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readNBytes(Integer.MAX_VALUE));
		DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
		BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(byteArrayInputStream, null);


		for (int i = 0; i < iterations; i++) {
			long startDeserialization = System.nanoTime();
			userDatumReader.read(null, decoder);
			long endDeserialization = System.nanoTime();
			byteArrayInputStream.reset();

			totalDeserialization += (endDeserialization - startDeserialization);
		}

		LOGGER.info("averageDeserializationTimeNs: {}", totalDeserialization / iterations);
		LOGGER.info("totalDeserializationTimeS: {}", (double) totalDeserialization/1_000_000_000);
	}
}
