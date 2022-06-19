package com.example.extension.mapper;

import com.example.extension.model.dto.ExtensionRequest;
import com.example.extension.model.entity.Extension;
import com.example.extension.model.vo.ExtensionListResponse;
import com.example.extension.model.vo.ExtensionResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct 라이브러리를 통해 Dto, Entity, Vo 사이의 변환을 처리한다.
 */
@Mapper(componentModel = "spring")
public interface ExtensionMapper
    extends EntityMapper<ExtensionRequest, ExtensionResponse, Extension> {

  ExtensionMapper INSTANCE = Mappers.getMapper(ExtensionMapper.class);

  List<ExtensionResponse> entitiesToVos(List<Extension> extensionList);

  /**
   * 엔티티 목록을 Vo 목록에 대한 Response Vo로 변경한다.
   *
   * @param extensionList 확장자 엔티티 목록
   * @return 확장자 목록에 대한 Response Vo
   */
  default ExtensionListResponse entitiesToListVos(List<Extension> extensionList) {
    if (extensionList == null) {
      return null;
    }
    ExtensionListResponse.ExtensionListResponseBuilder extensionResponse =
        ExtensionListResponse.builder();
    extensionResponse.contents(entitiesToVos(extensionList));
    extensionResponse.totalCount(extensionList.size());

    return extensionResponse.build();
  }
}
