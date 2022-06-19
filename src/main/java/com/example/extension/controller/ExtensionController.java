package com.example.extension.controller;

import com.example.extension.model.dto.ExtensionRequest;
import com.example.extension.model.dto.ExtensionUsedUpdateRequest;
import com.example.extension.model.enums.ExtensionType;
import com.example.extension.model.vo.ExtensionListResponse;
import com.example.extension.model.vo.ExtensionResponse;
import com.example.extension.service.ExtensionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 확장자에 대한 Controller.
 */
@Tag(name = "Extensions", description = "확장자 API")
@CrossOrigin(origins = "http://localhost:9432")
@RestController
@RequestMapping(value = "/api/v1/extensions")
public class ExtensionController {

  private final ExtensionService extensionService;

  public ExtensionController(ExtensionService extensionService) {
    this.extensionService = extensionService;
  }

  /**
   * 새로운 확장자를 등록한다.
   *
   * @param request 확장자 Dto
   * @return 생성한 확장자 Vo의 HTTP 형식 응답 객체
   */
  @Operation(summary = "확장자 등록", description = "새로운 확장자를 등록한다.")
  @PostMapping
  public ResponseEntity<ExtensionResponse> create(@RequestBody @Valid ExtensionRequest request) {
    ExtensionResponse result = extensionService.create(request);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(result.getId()).toUri()).body(result);
  }

  /**
   * 확장자를 Used를 수정한다.
   *
   * @param id      수정할 확장자 id
   * @param request 확장자 Dto
   * @return 수정한 확장자 Vo의 HTTP 형식 응답 객체
   */
  @Operation(summary = "확장자 Used 수정", description = "등록되어 있는 확장자의 Used를 수정한다.")
  @PatchMapping(value = "/{id}")
  public ResponseEntity<ExtensionResponse> updateUsed(@PathVariable UUID id,
                                                  @RequestBody ExtensionUsedUpdateRequest request) {
    return ResponseEntity.ok(extensionService.updateUsed(id, request.isUsed()));
  }

  /**
   * 확장자를 삭제한다.
   *
   * @param id 삭제할 확장자 id
   * @return 컨텐츠 없음 응답
   */
  @Operation(summary = "확장자 삭제", description = "등록되어 있는 확장자를 삭제한다.")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> delete(@PathVariable UUID id) {
    extensionService.delete(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * 타입 별로 확장자 목록을 조회한다.
   *
   * @param type 확장자 타입
   * @return 확장자 목록 Vo의 HTTP 형식 응답 객체
   */
  @Operation(summary = "확장자 목록 타입별 조회", description = "확장자 목록을 타입별로 조회한다.")
  @GetMapping
  public ResponseEntity<ExtensionListResponse> listByType(ExtensionType type) {
    return ResponseEntity.ok(extensionService.listByType(type));
  }
}
