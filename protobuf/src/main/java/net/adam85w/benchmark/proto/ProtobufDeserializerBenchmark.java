package net.adam85w.benchmark.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ProtobufDeserializerBenchmark {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProtobufDeserializerBenchmark.class);

	static void test() throws IOException {
		FileInputStream inputStream = new FileInputStream("user_protobuf.bin");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readAllBytes());

		int iterations = 10_000_000;
		long totalDeserialization = 0;

		for (int i = 0; i < iterations; i++) {
			long startDeserialization = System.nanoTime();
			User.parseFrom(byteArrayInputStream);
			long endDeserialization = System.nanoTime();
			byteArrayInputStream.reset();

			totalDeserialization += (endDeserialization - startDeserialization);
		}

		LOGGER.info("averageDeserializationTimeNs: {}", totalDeserialization / iterations);
		LOGGER.info("totalDeserializationTimeS: {}", (double) totalDeserialization/1_000_000_000);
	}
}
