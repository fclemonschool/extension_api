package com.example.extension.model.dto;

import com.example.extension.model.enums.ExtensionType;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 확장자 Dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtensionRequest {

  private UUID id;

  @Pattern(regexp = "^[A-Za-z\\d]{1,20}$", message = "20자 이하의 영문자와 숫자를 입력해주십시오.")
  @NotEmpty
  private String fileExtension;

  @NotNull
  private ExtensionType type;

  @NotNull
  private boolean used;
}
