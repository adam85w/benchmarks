package net.adam85w.benchmark.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class JsonSerializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonSerializer.class);

	public static void main(String[] args) throws IOException {
		SpringApplication.run(JsonSerializer.class, args);

		ObjectMapper objectMapper = new ObjectMapper();
		User userSerialized = new User(1,"Adam", 40, "adam@example.com");

		OutputStream outputStream = new FileOutputStream("user_json.bin");
		outputStream.write(objectMapper.writeValueAsBytes(userSerialized));
		outputStream.close();

		InputStream inputStream = new FileInputStream("user_json.bin");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readAllBytes());
		User userDeserialized = objectMapper.readValue(byteArrayInputStream, User.class);

		LOGGER.info("user.id: {}, user.name: {}, user.age: {}, user.email: {}", userDeserialized.id(), userDeserialized.name(), userDeserialized.age(), userDeserialized.email());
		byteArrayInputStream.reset();
		LOGGER.info("Size: {}", byteArrayInputStream.readAllBytes().length);
	}
}
