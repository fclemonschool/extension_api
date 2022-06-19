package com.example.extension.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.extension.mapper.ExtensionMapper;
import com.example.extension.model.dto.ExtensionRequest;
import com.example.extension.model.entity.Extension;
import com.example.extension.model.enums.ExtensionType;
import com.example.extension.model.vo.ExtensionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExtensionRepositoryTest {

  Extension extension;

  ExtensionRequest request;

  @Autowired
  private ExtensionRepository extensionRepository;

  @Spy
  private ExtensionMapper mapper = Mappers.getMapper(ExtensionMapper.class);

  @BeforeEach
  public void initAll() {
    request = ExtensionRequest.builder().fileExtension("jmc").used(false).type(ExtensionType.CUSTOM)
        .build();
    extension = mapper.toEntity(request);
  }

  @Test
  void findAllByTypeOrderByFileExtensionAsc() {
    // when
    extensionRepository.save(extension);

    // then
    assertFalse(
        extensionRepository.findAllByTypeOrderByFileExtensionAsc(ExtensionType.CUSTOM).isEmpty());
  }

  @Test
  void countAllByFileExtension() {
    // when
    extensionRepository.save(extension);

    // then
    assertTrue(extensionRepository.countAllByFileExtension(extension.getFileExtension()) > 0);
  }

  @Test
  void countAllByTypeEquals() {
    // when
    extensionRepository.save(extension);

    // then
    assertTrue(extensionRepository.countAllByTypeEquals(ExtensionType.CUSTOM) > 0);
  }

  @Test
  void updateUsed() {
    // given
    ExtensionResponse response = mapper.toVo(extensionRepository.save(extension));

    // then
    assertTrue(extensionRepository.updateUsed(response.getId(), true) > 0);
  }
}
