package com.example.extension.service;

import com.example.extension.model.dto.ExtensionRequest;
import com.example.extension.model.enums.ExtensionType;
import com.example.extension.model.vo.ExtensionListResponse;
import com.example.extension.model.vo.ExtensionResponse;
import java.util.UUID;

/**
 * 확장자 서비스 인터페이스.
 */
public interface ExtensionService {

  /**
   * 새로운 확장자를 생성한다.
   *
   * @param request 확장자 Dto
   * @return 생성한 확장자 정보를 담고 있는 확장자 Vo
   */
  ExtensionResponse create(ExtensionRequest request);

  /**
   * 입력받은 id와 일치하는 확장자를 조회한다.
   *
   * @param id 입력받은 id
   * @return 확장자 정보를 담고 있는 확장자 Vo
   */
  ExtensionResponse retrieve(UUID id);

  /**
   * 입력받은 id와 일치하는 확장자의 used 값을 수정한다.
   *
   * @param id   수정할 id
   * @param used 수정할 used 값
   * @return 수정한 확장자 정보를 담고 있는 확장자 Vo
   */
  ExtensionResponse updateUsed(UUID id, boolean used);

  /**
   * 입력받은 id와 일치하는 확장자를 삭제한다.
   *
   * @param id 삭제할 id
   */
  void delete(UUID id);

  /**
   * 입력받은 타입과 일치하는 확장자 목록을 조회한다.
   *
   * @param type 조회할 확장자 타입
   * @return 입력받은 확장자 타입과 일치하는 확장자 목록에 대한 Vo
   */
  ExtensionListResponse listByType(ExtensionType type);
}
