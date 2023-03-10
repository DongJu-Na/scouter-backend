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

  @DisplayName("?????? ?????? ??????")
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
            parameterWithName("boardId").description("????????? ID"),
            parameterWithName("page").description("Page ?????? - Default(0)").optional(),
            parameterWithName("size").description("Page ????????? ?????? - Default(10)").optional()
        ),
        ApiDocumentationUtils.getResponseFieldsSnippetByFieldDescriptors(
            JsonFieldType.OBJECT,
            fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("?????? Page ????????? ????????? ??????"),
            fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("?????? Page ???????????? ????????? ??????"),
            fieldWithPath("page").type(JsonFieldType.NUMBER).description("?????? Page ??????"),
            fieldWithPath("size").type(JsonFieldType.NUMBER).description("?????? Page ?????? ????????? ??????"),
            fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("?????? Data ??????"),
            fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("?????? Page ??????"),
            fieldWithPath("content").type(JsonFieldType.ARRAY).description("Page ?????? (????????? ??????)"),
            fieldWithPath("content[0].replyId").type(JsonFieldType.NUMBER).description("?????? ID"),
            fieldWithPath("content[0].replyContent").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("content[0].createdDt").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("content[0].replyUserNickName").type(JsonFieldType.STRING).description("?????????"),
            fieldWithPath("content[0].replyUserEmail").type(JsonFieldType.STRING).description("????????? ?????????")
        )
      )
    );
  }

  @DisplayName("?????? ??????")
  @Test
  void CreateReply() throws Exception {

    this.httpHeaders.add("Authorization","Bearer " + getAccessJwt());

    HashMap<String, String> createReplyForm = new HashMap<>();
    createReplyForm.put("replyContent", "????????????");
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
          fieldWithPath("replyContent").type(JsonFieldType.STRING).description("?????? ??????"),
          fieldWithPath("boardId").type(JsonFieldType.STRING).description("????????? ID")
        ),
        ApiDocumentationUtils.getResponseFieldsSnippetByFieldDescriptors(JsonFieldType.NUMBER)
      )
    );
  }

  @DisplayName("????????? ??????")
  @Test
  void UpdateReply() throws Exception {

    this.httpHeaders.add("Authorization","Bearer " + getAccessJwt());

    HashMap<String, String> updateReplyForm = new HashMap<>();
    updateReplyForm.put("replyContent", "?????? ?????? ??????");

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
            parameterWithName("replyId").description("?????? ID")
        ),
        requestFields(
          fieldWithPath("replyContent").type(JsonFieldType.STRING).description("?????? ??????")
        ),
        ApiDocumentationUtils.getResponseFieldsSnippetByFieldDescriptors(JsonFieldType.NUMBER)
      )
    );
  }

  @DisplayName("????????? ??????")
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
          parameterWithName("replyId").description("????????? ID")
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
