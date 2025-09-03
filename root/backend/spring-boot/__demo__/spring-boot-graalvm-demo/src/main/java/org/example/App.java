package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class App {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostMapping("/sort")
    public <T> List<T> sort(@RequestBody InputWrapper<T> wrapper) {
        // 检验
        if (wrapper.input().isEmpty() || !(wrapper.input().get(0) instanceof Comparable)) {
            System.out.println(wrapper.input().get(0).getClass());
            return Collections.emptyList();
        }
        // 排序
        List<T> output = wrapper.input().stream().sorted().toList();
        // 入库
        CompletableFuture.supplyAsync(() -> {
            int result;
            try {
                String sql =
                    """
                    INSERT INTO example.sort_input_to_output (input, output)
                    VALUES (?, ?)
                    """;
                Object[] args = { objectMapper.writeValueAsString(wrapper.input()), objectMapper.writeValueAsString(output) };
                result = jdbcTemplate.update(sql, args);
            } catch (Exception e) {
                result = 0;
            }
            return result;
        });
        // 返回
        return output;
    }

    public record InputWrapper<T>(List<T> input) {}
}
