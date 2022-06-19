package com.example.extension.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.example.extension.model.dto.ExtensionRequest;
import com.example.extension.model.dto.ExtensionUsedUpdateRequest;
import com.example.extension.model.enums.ExtensionType;
import com.example.extension.service.ExtensionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExtensionController.class)
@AutoConfigureMockMvc
class ExtensionControllerTest {

  @MockBean
  ExtensionService extensionService;

  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @BeforeEach
  public void initAll() {
    this.objectMapper = new ObjectMapper();
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void createWithInvalidFileExtension() throws Exception {
    // given
    ExtensionRequest request =
        ExtensionRequest.builder().fileExtension("js&&").type(ExtensionType.FIXED).used(false)
            .build();

    // then
    mockMvc.perform(post("/api/v1/extensions").content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
  }

  @Test
  void updateUsed() throws Exception {
    // given
    ExtensionUsedUpdateRequest request = ExtensionUsedUpdateRequest.builder().used(false).build();

    // then
    mockMvc.perform(patch("/api/v1/extensions/{id}", UUID.randomUUID()).content(
            objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void delete() throws Exception {
    // then
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/extensions/{id}", UUID.randomUUID())
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
  }

  @Test
  void listByType() throws Exception {
    // then
    mockMvc.perform(get("/api/v1/extensions").queryParam("type", "FIXED"))
        .andExpect(status().isOk());
  }
}
