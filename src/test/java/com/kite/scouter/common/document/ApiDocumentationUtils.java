package com.kite.scouter.common.document;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public interface ApiDocumentationUtils {

  static ResponseFieldsSnippet getResponseFieldsSnippetByFieldDescriptors(final JsonFieldType responseDataType,
                                                                          final FieldDescriptor... targetDescriptors) {

    if (targetDescriptors == null) {
      return responseFields(getCommonFieldDescriptors(responseDataType));
    } else {
      FieldDescriptor[] formattedFieldDescriptors = Arrays.stream(targetDescriptors)
        .map(ApiDocumentationUtils::getFormattedFieldDescriptor)
        .toArray(FieldDescriptor[]::new);

      return responseFields(
        ArrayUtils.addAll(getCommonFieldDescriptors(responseDataType), formattedFieldDescriptors)
      );
    }
  }

  private static FieldDescriptor getFormattedFieldDescriptor(final FieldDescriptor targetDescriptor) {

    FieldDescriptor fieldDescriptor = fieldWithPath("data." + targetDescriptor.getPath())
      .type(targetDescriptor.getType())
      .description(targetDescriptor.getDescription());

    if (targetDescriptor.isOptional()) {
      fieldDescriptor.optional();
    }

    if (targetDescriptor.isIgnored()) {
      fieldDescriptor.ignored();
    }

    return fieldDescriptor;
  }

  private static FieldDescriptor[] getCommonFieldDescriptors(final JsonFieldType responseDataType) {
   return new FieldDescriptor[]{
      fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
      fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
      fieldWithPath("data").type(responseDataType).optional().description("응답 데이터")
    };
  }

}
