package net.adam85w.benchmark.avro;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AvroBenchmarkApplication {

    public static void main(String[] args) throws IOException {
        ApplicationArguments applicationArguments = SpringApplication.run(AvroBenchmarkApplication.class, args).getBean(ApplicationArguments.class);
        if (applicationArguments.getSourceArgs() == null || applicationArguments.getSourceArgs().length == 0 || applicationArguments.getSourceArgs()[0] == null) {
            AvroSerializer.test();
        } else {
            switch (applicationArguments.getSourceArgs()[0]) {
                case "serializer-benchmark":
                    AvroSerializerBenchmark.test();
                    break;
                case "deserializer-benchmark":
                    AvroDeserializerBenchmark.test();
                    break;
                case "serializer":
                    AvroSerializer.test();
                    break;
            }
        }
    }
}
