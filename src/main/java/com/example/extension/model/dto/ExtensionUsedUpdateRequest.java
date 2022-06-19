package com.example.extension.model.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 확장자 Used 수정 Dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtensionUsedUpdateRequest {

  @NotNull
  private boolean used;
}
