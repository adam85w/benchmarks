package net.adam85w.benchmark.avro;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class AvroSerializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(AvroSerializer.class);

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AvroSerializer.class, args);
		User userSerialized = new User(1L, "Adam", 40, "adam@example.com");

		OutputStream outputStream = new FileOutputStream("user_avro.bin");
		DatumWriter<User> writer = new SpecificDatumWriter<>(User.class);
		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
		writer.write(userSerialized, encoder);
		encoder.flush();
		outputStream.close();

		InputStream inputStream = new FileInputStream("user_avro.bin");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readNBytes(Integer.MAX_VALUE));
		DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
		BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(byteArrayInputStream, null);
		User userDeserialized = userDatumReader.read(null, decoder);

		LOGGER.info("user.id: {}, user.name: {}, user.age: {}, user.email: {}", userDeserialized.getId(), userDeserialized.getName(), userDeserialized.getAge(), userDeserialized.getEmail());
		byteArrayInputStream.reset();
		LOGGER.info("Size: {}", byteArrayInputStream.readAllBytes().length);
	}
}
