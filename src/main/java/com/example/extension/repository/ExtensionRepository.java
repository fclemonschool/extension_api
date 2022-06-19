package com.example.extension.repository;

import com.example.extension.model.entity.Extension;
import com.example.extension.model.enums.ExtensionType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * JPA를 통한 CRUD 등의 쿼리를 처리하는 확장자 Repository.
 */
public interface ExtensionRepository extends JpaRepository<Extension, UUID> {

  /**
   * 입력받은 타입과 일치하는 확장자 목록을 출력한다.
   * 정렬은 확장자명에 대한 오름차순
   *
   * @param type 대상 타입
   * @return 입력받은 타입과 일치하는 확장자 목록
   */
  List<Extension> findAllByTypeOrderByFileExtensionAsc(ExtensionType type);

  /**
   * 입력받은 확장자명과 일치하는 개수를 출력한다.
   *
   * @param fileExtension 대상 확장자명
   * @return 입력받은 확장자명과 일치하는 개수
   */
  Integer countAllByFileExtension(String fileExtension);

  /**
   * 입력받은 타입과 일치하는 개수를 출력한다.
   *
   * @param type 대상 타입
   * @return 입력받은 타입과 일치하는 개수
   */
  Integer countAllByTypeEquals(ExtensionType type);

  /**
   * 입력받은 id와 일치하는 확장자의 used 값을 수정한다.
   *
   * @param id 수정할 확장자 id
   * @param used 수정할 used 값
   * @return 수정된 개수
   */
  @Modifying
  @Query("update Extension ext set ext.used = :used where ext.id = :id")
  Integer updateUsed(@Param(value = "id") UUID id, @Param(value = "used") boolean used);
}
