package net.adam85w.benchmark.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class JsonDeserializerBenchmark {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonDeserializerBenchmark.class);

	public static void main(String[] args) throws IOException {
		SpringApplication.run(JsonDeserializerBenchmark.class, args);

		ObjectMapper objectMapper = new ObjectMapper();

		InputStream inputStream = new FileInputStream("user_json.bin");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readNBytes(Integer.MAX_VALUE));

		int iterations = 10_000_000;
		long totalDeserialization = 0;

		for (int i = 0; i < iterations; i++) {
			long startDeserialization = System.nanoTime();
			objectMapper.readValue(byteArrayInputStream, User.class);
			long endDeserialization = System.nanoTime();
			byteArrayInputStream.reset();

			totalDeserialization += (endDeserialization - startDeserialization);
		}


		LOGGER.info("averageDeserializationTimeNs: {}", totalDeserialization / iterations);
		LOGGER.info("totalDeserializationTimeS: {}", (double) totalDeserialization/1_000_000_000);
	}

}
