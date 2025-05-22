package net.adam85w.benchmark.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JsonApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonApplication.class);

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(JsonApplication.class, args);

		ObjectMapper objectMapper = new ObjectMapper();

		User user = new User(1,"Adam", 28, "adam@example.com");

		int iterations = 10_000_000;
		long totalSerialization = 0;
		long totalDeserialization = 0;
		int totalSize = 0;

		for (int i = 0; i < iterations; i++) {
			long startSerialization = System.nanoTime();
			String json = objectMapper.writeValueAsString(user);
			byte[] jsonBytes = json.getBytes();
			long endSerialization = System.nanoTime();

			totalSize += jsonBytes.length;

			long startDeserialization = System.nanoTime();
			User deserialized = objectMapper.readValue(json, User.class);
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
