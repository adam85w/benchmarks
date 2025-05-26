package net.adam85w.benchmark.proto;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ProtobufBenchmarkApplication {

    public static void main(String[] args) throws IOException {
        ApplicationArguments applicationArguments = SpringApplication.run(ProtobufBenchmarkApplication.class, args).getBean(ApplicationArguments.class);
        if (applicationArguments.getSourceArgs() == null || applicationArguments.getSourceArgs().length == 0 || applicationArguments.getSourceArgs()[0] == null) {
            ProtobufSerializer.test();
        } else {
            switch (applicationArguments.getSourceArgs()[0]) {
                case "serializer-benchmark":
                    ProtobufSerializerBenchmark.test();
                    break;
                case "deserializer-benchmark":
                    ProtobufDeserializerBenchmark.test();
                    break;
                case "serializer":
                    ProtobufSerializer.test();
                    break;
            }
        }
    }
}
