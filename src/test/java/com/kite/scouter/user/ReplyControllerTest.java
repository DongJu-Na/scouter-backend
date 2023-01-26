package com.kite.scouter.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kite.scouter.common.document.ApiDocumentationUtils;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureRestDocs
@SpringBootTest(properties = "spring.profiles.active=junit")
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class})
public class ReplyControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  private HttpHeaders httpHeaders;

  @BeforeEach
  void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .apply(documentationConfiguration(restDocumentation))
        .build();

    this.httpHeaders = new HttpHeaders();
  }

  @DisplayName("댓글 목록 조회")
  @Test
  void getReplys() throws Exception {
     mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/replys")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .characterEncoding(StandardCharsets.UTF_8.name())
      .queryParam("boardId","1"))

      .andExpect(status().isOk())
      .andDo(document("/get-replys",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        queryParameters(
            parameterWithName("boardId").description("게시판 ID"),
            parameterWithName("page").description("Page 번호 - Default(0)").optional(),
            parameterWithName("size").description("Page 보여줄 갯수 - Default(10)").optional()
        ),
        ApiDocumentationUtils.getResponseFieldsSnippetByFieldDescriptors(
            JsonFieldType.OBJECT,
            fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("현재 Page 첫번째 페이지 여부"),
            fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("현재 Page 마지막째 페이지 여부"),
            fieldWithPath("page").type(JsonFieldType.NUMBER).description("현재 Page 번호"),
            fieldWithPath("size").type(JsonFieldType.NUMBER).description("현재 Page 에서 보여줄 갯수"),
            fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("전체 Data 갯수"),
            fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("전체 Page 갯수"),
            fieldWithPath("content").type(JsonFieldType.ARRAY).description("Page 내용 (게시글 목록)"),
            fieldWithPath("content[0].replyId").type(JsonFieldType.NUMBER).description("댓글 ID"),
            fieldWithPath("content[0].replyContent").type(JsonFieldType.STRING).description("댓글내용"),
            fieldWithPath("content[0].createdDt").type(JsonFieldType.STRING).description("등록일시")
        )
      )
    );
  }

  @DisplayName("댓글 생성")
  @Test
  void CreateReply() throws Exception {

    this.httpHeaders.add("Authorization","Bearer " + getAccessJwt());

    HashMap<String, String> createReplyForm = new HashMap<>();
    createReplyForm.put("replyContent", "댓글내용");
    createReplyForm.put("boardId","1");

    mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/replys")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .characterEncoding(StandardCharsets.UTF_8.name())
      .content(objectMapper.writeValueAsString(createReplyForm))
      .headers(this.httpHeaders))
      .andExpect(status().isCreated())

      .andDo(document("/post-replys",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
          fieldWithPath("replyContent").type(JsonFieldType.STRING).description("댓글 내용"),
          fieldWithPath("boardId").type(JsonFieldType.STRING).description("게시판 ID")
        ),
        ApiDocumentationUtils.getResponseFieldsSnippetByFieldDescriptors(JsonFieldType.NUMBER)
      )
    );
  }

  @DisplayName("게시글 수정")
  @Test
  void UpdateReply() throws Exception {

    this.httpHeaders.add("Authorization","Bearer " + getAccessJwt());

    HashMap<String, String> updateReplyForm = new HashMap<>();
    updateReplyForm.put("replyContent", "댓글 내용 수정");

    mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/replys/{replyId}","1")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .characterEncoding(StandardCharsets.UTF_8.name())
      .content(objectMapper.writeValueAsString(updateReplyForm))
      .headers(this.httpHeaders))
      .andExpect(status().isCreated())

        .andDo(document("/put-replys",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
            parameterWithName("replyId").description("댓글 ID")
        ),
        requestFields(
          fieldWithPath("replyContent").type(JsonFieldType.STRING).description("댓글 내용")
        ),
        ApiDocumentationUtils.getResponseFieldsSnippetByFieldDescriptors(JsonFieldType.NUMBER)
      )
    );
  }

  @DisplayName("게시글 삭제")
  @Test
  void DeleteReply() throws Exception {

    this.httpHeaders.add("Authorization","Bearer " + getAccessJwt());

    mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/replys/{replyId}","2")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .characterEncoding(StandardCharsets.UTF_8.name())
      .headers(this.httpHeaders))
      .andExpect(status().isCreated())

        .andDo(document("/delete-replys",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("replyId").description("게시판 ID")
        ),
        ApiDocumentationUtils.getResponseFieldsSnippetByFieldDescriptors(JsonFieldType.NUMBER)
      )
    );
  }

  private String getAccessJwt() throws Exception {

    HashMap<String, String> loginForm = new HashMap<>();
    loginForm.put("email", "test1@naver.com");
    loginForm.put("password", "1234");

    MvcResult getToken = mockMvc.perform(post("/api/v1/auth/authenticate")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8.name())
            .content(objectMapper.writeValueAsString(loginForm)))
        .andReturn();

    HashMap<String, Object> resultMap1 = objectMapper.readValue(getToken.getResponse().getContentAsString(), new TypeReference<>() {});
    HashMap<String, Object> resultMap2 = objectMapper.readValue(objectMapper.writeValueAsString(resultMap1.get("data")), new TypeReference<>() {});

    return resultMap2.get("access_token").toString();
  }


}
