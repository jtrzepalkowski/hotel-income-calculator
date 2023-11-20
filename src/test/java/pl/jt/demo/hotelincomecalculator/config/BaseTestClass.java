package pl.jt.demo.hotelincomecalculator.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTestClass {

  @Autowired
  private ResourceLoader resourceLoader;
  public List<Double> willingnessToPay;

  @BeforeAll
  void setUp() throws IOException {
    Resource resource = resourceLoader.getResource("classpath:willingnessToPayTestData.json");
    File file = resource.getFile();
    willingnessToPay = List.of(new ObjectMapper().readValue(file, Double[].class));

  }
}
