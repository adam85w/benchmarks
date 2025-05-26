package net.adam85w.benchmark.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSerializerBenchmark {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonSerializerBenchmark.class);

	static void test() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		User user = new User(1,"Adam", 40, "adam@example.com");

		int iterations = 10_000_000;
		long totalSerialization = 0;

		for (int i = 0; i < iterations; i++) {
			long startSerialization = System.nanoTime();
			objectMapper.writeValueAsBytes(user);
			long endSerialization = System.nanoTime();

			totalSerialization += (endSerialization - startSerialization);
		}

		LOGGER.info("averageSerializationTimeNs: {}", totalSerialization/iterations);
		LOGGER.info("totalSerializationTimeS: {}", (double) totalSerialization/1_000_000_000);
	}
}
