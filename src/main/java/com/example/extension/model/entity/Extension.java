package com.example.extension.model.entity;

import com.example.extension.model.enums.ExtensionType;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 확장자 Entity.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Extension {

  @Id
  @Column(columnDefinition = "uuid")
  @GeneratedValue
  private UUID id;

  @Column(length = 20, nullable = false)
  private String fileExtension;

  @Column(nullable = false)
  private ExtensionType type;

  @Column(nullable = false)
  private boolean used;
}
