package com.example.extension.model.vo;

import com.example.extension.model.enums.ExtensionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 확장자 Vo.
 */
@Getter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtensionResponse {

  private UUID id;

  private String fileExtension;

  private ExtensionType type;

  private boolean used;
}
