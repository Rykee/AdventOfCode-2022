package hu.rhykee.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HttpUtils {

    private static final String BASE_URL = "https://adventofcode.com/2022/day/";
    private static final Path TEMP = Paths.get("C:/Temp/");

    public static List<String> getInput(int day, String cookie) {
        String fileName = "input" + (day < 10 ? "0" + day : day) + ".txt";
        Path filePath = TEMP.resolve(fileName);
        List<String> input = getFileContent(filePath);
        if (input != null) {
            return input;
        }
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + day + "/input");
            request.addHeader("cookie", cookie);
            CloseableHttpResponse response = client.execute(request);
            String puzzleInput = new BasicResponseHandler().handleResponse(response);
            Files.writeString(filePath, puzzleInput);
            return Arrays.stream(puzzleInput.split("\n"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getFileContent(Path fileName) {
        if (fileName.toFile().exists()) {
            try {
                return Files.readAllLines(fileName);
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }

}
