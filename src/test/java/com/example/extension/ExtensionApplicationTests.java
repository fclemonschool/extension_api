package com.example.extension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class ExtensionApplicationTests {

  @Test
  void contextLoads(ApplicationContext context) {
    assertNotNull(context);
  }
}
