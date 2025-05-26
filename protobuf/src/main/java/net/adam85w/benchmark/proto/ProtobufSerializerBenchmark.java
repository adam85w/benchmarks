package net.adam85w.benchmark.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProtobufSerializerBenchmark {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProtobufSerializerBenchmark.class);

	static void test() throws IOException {
		User userSerialized = User.newBuilder()
				.setId(1L)
				.setName("Adam")
				.setAge(40)
				.setEmail("adam@example.com")
				.build();

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		int iterations = 10_000_000;
		long totalSerialization = 0;

		for (int i = 0; i < iterations; i++) {
			long startSerialization = System.nanoTime();
			userSerialized.writeTo(out);
			out.flush();
			long endSerialization = System.nanoTime();

			totalSerialization += (endSerialization - startSerialization);
		}

		LOGGER.info("averageSerializationTimeNs: {}", totalSerialization/iterations);
		LOGGER.info("totalSerializationTimeS: {}", (double) totalSerialization/1_000_000_000);
	}
}
