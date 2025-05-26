package net.adam85w.benchmark.json;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class JsonBenchmarkApplication {

    public static void main(String[] args) throws IOException {
        ApplicationArguments applicationArguments = SpringApplication.run(JsonBenchmarkApplication.class, args).getBean(ApplicationArguments.class);
        if (applicationArguments.getSourceArgs() == null || applicationArguments.getSourceArgs().length == 0 || applicationArguments.getSourceArgs()[0] == null) {
            JsonSerializer.test();
        } else {
            switch (applicationArguments.getSourceArgs()[0]) {
                case "serializer-benchmark":
                    JsonSerializerBenchmark.test();
                    break;
                case "deserializer-benchmark":
                    JsonDeserializerBenchmark.test();
                    break;
                case "serializer":
                    JsonSerializer.test();
                    break;
            }
        }
    }
}
