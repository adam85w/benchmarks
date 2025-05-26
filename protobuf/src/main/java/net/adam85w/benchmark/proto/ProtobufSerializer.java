package net.adam85w.benchmark.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

class ProtobufSerializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProtobufSerializer.class);

	static void test() throws IOException {
		User userSerialized = User.newBuilder()
				.setId(1L)
				.setName("Adam")
				.setAge(40)
				.setEmail("adam@example.com")
				.build();
		FileOutputStream outputStream = new FileOutputStream("user_protobuf.bin");
		userSerialized.writeTo(outputStream);
		outputStream.close();


		FileInputStream inputStream = new FileInputStream("user_protobuf.bin");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readAllBytes());
		User userDeserialized = User.parseFrom(byteArrayInputStream);

		LOGGER.info("user.id: {}, user.name: {}, user.age: {}, user.email: {}", userDeserialized.getId(), userDeserialized.getName(), userDeserialized.getAge(), userDeserialized.getEmail());
		byteArrayInputStream.reset();
		LOGGER.info("Size: {}", byteArrayInputStream.readAllBytes().length);
	}
}
