package com.example.springproj12;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class VersionController {

    @PostMapping("/isbadversionlinear")
    public Integer isbadversionlinear(@RequestBody String data) {
        List<String> dataList = Arrays.asList(data.split(","));

        for (int i = 0; i < dataList.size(); i++) {
            String version = dataList.get(i);
            if (version.equals("bad")) {
                return i + 1;
            }
        }
        return 0;
    }

    @PostMapping("/isbadversionbinary")
    public Integer isbadversionbinary(@RequestBody String data) {
        List<String> dataList = Arrays.asList(data.split(","));

        int left = 1;
        int right = dataList.size();

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (dataList.get(mid - 1).equals("bad")) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    @GetMapping("/generate_input_data")
    public String generateInputData(Integer size) throws IOException {
        int firstBad = ThreadLocalRandom.current().nextInt(1, size + 1);

        StringBuilder dataBuilder = new StringBuilder("");

        for (int i = 1; i < size + 1; i++) {
            if (i >= firstBad) {
                dataBuilder.append("bad,");
            } else {
                dataBuilder.append("good,");
            }
        }

        String data = dataBuilder.substring(0, dataBuilder.length() - 1);

        BufferedWriter writer = new BufferedWriter(new FileWriter("input_data.txt"));
        writer.write(data);
        writer.close();

        return String.format("Generated succesfully. First bad version is %d", firstBad);
    }
}