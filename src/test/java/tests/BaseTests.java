package tests;

import org.junit.jupiter.api.BeforeAll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class BaseTests {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Start tests");
    }

    static Stream<String[]> getCredentialsFromTxt(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        return reader.lines()
                .map(line -> line.split(" "))
                .filter(parts -> parts.length == 2) // Проверяем наличие пары значений
                .map(parts -> new String[]{parts[0], parts[1]})
                .onClose(() -> {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}

