package com.example.extension.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.extension.mapper.ExtensionMapper;
import com.example.extension.model.dto.ExtensionRequest;
import com.example.extension.model.entity.Extension;
import com.example.extension.model.enums.ExtensionType;
import com.example.extension.model.vo.ExtensionListResponse;
import com.example.extension.model.vo.ExtensionResponse;
import com.example.extension.repository.ExtensionRepository;
import com.example.extension.service.impl.ExtensionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
class ExtensionServiceTest {

  ExtensionService extensionService;

  @Mock
  ExtensionRepository extensionRepository;

  Extension extension;

  ExtensionRequest request;

  @Spy
  private ExtensionMapper mapper = Mappers.getMapper(ExtensionMapper.class);

  @BeforeEach
  public void initAll() {
    this.extensionService = new ExtensionServiceImpl(extensionRepository);
    RequestContextHolder.setRequestAttributes(
        new ServletRequestAttributes(new MockHttpServletRequest()));
    request = ExtensionRequest.builder().id(UUID.randomUUID()).fileExtension("jmc")
        .type(ExtensionType.CUSTOM).used(false).build();
    extension = mapper.toEntity(request);
  }

  @Test
  void create() {
    // given
    when(extensionRepository.countAllByTypeEquals(request.getType())).thenReturn(1);
    when(extensionRepository.countAllByFileExtension(request.getFileExtension())).thenReturn(0);
    when(extensionRepository.save(any())).thenReturn(extension);

    // when
    ExtensionResponse response = extensionService.create(request);

    // then
    verify(extensionRepository).countAllByTypeEquals(request.getType());
    verify(extensionRepository).countAllByFileExtension(request.getFileExtension());
    assertEquals(response, mapper.toVo(extension));
  }

  @Test
  void retrieve() {
    // given
    when(extensionRepository.findById(any())).thenReturn(Optional.of(extension));

    // when
    ExtensionResponse response = extensionService.retrieve(request.getId());

    // then
    verify(extensionRepository).findById(request.getId());
    assertEquals(response, mapper.toVo(extension));
  }

  @Test
  void updateUsed() {
    // given
    when(extensionRepository.updateUsed(request.getId(), true)).thenReturn(1);
    when(extensionRepository.findById(request.getId())).thenReturn(Optional.ofNullable(extension));

    // when
    ExtensionResponse response = extensionService.updateUsed(request.getId(), true);

    // then
    verify(extensionRepository).updateUsed(request.getId(), true);
    verify(extensionRepository).findById(request.getId());
    assertEquals(response, mapper.toVo(extension));
  }

  @Test
  void delete() {
    // when
    extensionService.delete(request.getId());

    // then
    verify(extensionRepository).deleteById(request.getId());
  }

  @Test
  void listByType() {
    // given
    List<Extension> extensionList = new ArrayList<>();
    extensionList.add(mapper.toEntity(request));
    extensionList.add(mapper.toEntity(
        ExtensionRequest.builder().id(UUID.randomUUID()).fileExtension("jmc2").used(false)
            .type(request.getType()).build()));
    when(extensionRepository.findAllByTypeOrderByFileExtensionAsc(any())).thenReturn(extensionList);

    // when
    ExtensionListResponse response = extensionService.listByType(request.getType());

    // then
    verify(extensionRepository).findAllByTypeOrderByFileExtensionAsc(request.getType());
    Assertions.assertEquals(response, mapper.entitiesToListVos(extensionList));
  }
}
