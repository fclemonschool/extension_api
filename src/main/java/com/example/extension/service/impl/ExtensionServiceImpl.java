package com.example.extension.service.impl;

import com.example.extension.exception.CustomException;
import com.example.extension.exception.ExceptionType;
import com.example.extension.mapper.ExtensionMapper;
import com.example.extension.model.dto.ExtensionRequest;
import com.example.extension.model.entity.Extension;
import com.example.extension.model.enums.ExtensionType;
import com.example.extension.model.vo.ExtensionListResponse;
import com.example.extension.model.vo.ExtensionResponse;
import com.example.extension.repository.ExtensionRepository;
import com.example.extension.service.ExtensionService;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 확장자 서비스 구현체.
 */
@Service
@Transactional(readOnly = true)
public class ExtensionServiceImpl implements ExtensionService {

  private static final int MAX_CUSTOM_EXTENSIONS = 200;
  private final ExtensionRepository extensionRepository;

  public ExtensionServiceImpl(ExtensionRepository extensionRepository) {
    this.extensionRepository = extensionRepository;
  }

  /**
   * 새로운 확장자를 생성한다.
   *
   * @param request 확장자 Dto
   * @return 생성한 확장자 정보를 담고 있는 확장자 Vo
   */
  @Transactional
  @Override
  public ExtensionResponse create(ExtensionRequest request) {
    if (request.getType() == ExtensionType.CUSTOM
        && extensionRepository.countAllByTypeEquals(request.getType()) > MAX_CUSTOM_EXTENSIONS) {
      throw new CustomException(ExceptionType.MAX_COUNT_EXCEPTION);
    }
    if (extensionRepository.countAllByFileExtension(request.getFileExtension()) == 0) {
      return ExtensionMapper.INSTANCE.toVo(
          extensionRepository.save(ExtensionMapper.INSTANCE.toEntity(request)));
    } else {
      throw new CustomException(ExceptionType.ALREADY_EXISTS_EXCEPTION);
    }
  }

  /**
   * 입력받은 id와 일치하는 확장자를 조회한다.
   *
   * @param id 입력받은 id
   * @return 확장자 정보를 담고 있는 확장자 Vo
   */
  @Override
  public ExtensionResponse retrieve(UUID id) {
    Optional<Extension> extension = extensionRepository.findById(id);
    if (extension.isPresent()) {
      return ExtensionMapper.INSTANCE.toVo(extension.get());
    }
    throw new NoSuchElementException(ExceptionType.NO_SUCH_ELEMENT_EXCEPTION.getMessage());
  }

  /**
   * 입력받은 id와 일치하는 확장자의 used 값을 수정한다.
   *
   * @param id   수정할 id
   * @param used 수정할 used 값
   * @return 수정한 확장자 정보를 담고 있는 확장자 Vo
   */
  @Transactional
  @Override
  public ExtensionResponse updateUsed(UUID id, boolean used) {
    if (extensionRepository.updateUsed(id, used) > 0) {
      return retrieve(id);
    }
    throw new NoSuchElementException(ExceptionType.NO_SUCH_ELEMENT_EXCEPTION.getMessage());
  }

  /**
   * 입력받은 id와 일치하는 확장자를 삭제한다.
   *
   * @param id 삭제할 id
   */
  @Transactional
  @Override
  public void delete(UUID id) {
    extensionRepository.deleteById(id);
  }

  /**
   * 입력받은 타입과 일치하는 확장자 목록을 조회한다.
   *
   * @param type 조회할 확장자 타입
   * @return 입력받은 확장자 타입과 일치하는 확장자 목록에 대한 Vo
   */
  @Override
  public ExtensionListResponse listByType(ExtensionType type) {
    return ExtensionMapper.INSTANCE.entitiesToListVos(
        extensionRepository.findAllByTypeOrderByFileExtensionAsc(type));
  }
}
