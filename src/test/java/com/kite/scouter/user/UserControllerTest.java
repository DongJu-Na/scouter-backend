package com.kite.scouter.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureRestDocs
@SpringBootTest(properties = "spring.profiles.active=junit")
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class})
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  private HttpHeaders httpHeaders;
  private String userAuthorization;

  @BeforeEach
  void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .apply(documentationConfiguration(restDocumentation))
        .build();

    this.httpHeaders = new HttpHeaders();

  }


  @DisplayName("유저 가입")
  @Test
  void getUserRegister() throws Exception {
    HashMap<String, String> loginForm = new HashMap<>();
    loginForm.put("nickName", "test1234");
    loginForm.put("email", "test1234@naver.com");
    loginForm.put("password", "1234");

    mockMvc.perform(post("/api/v1/auth/register")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .characterEncoding(StandardCharsets.UTF_8.name())
      .content(objectMapper.writeValueAsString(loginForm))
      .headers(this.httpHeaders))

      .andExpect(status().isCreated())
      .andDo(document("/user-join",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
            fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
            fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
        ),
        ApiDocumentationUtils.getResponseFieldsSnippetByFieldDescriptors(
          JsonFieldType.OBJECT,
          fieldWithPath("access_token").type(JsonFieldType.STRING).description("jwt 토큰"),
          fieldWithPath("refresh_token").type(JsonFieldType.STRING).description("refresh 토큰"),
          fieldWithPath("accessTokenExpirationTime").type(JsonFieldType.NUMBER).description("토큰 만료시간")
        )
      )
    );
  }

  @DisplayName("유저 로그인")
  @Test
  void getUserLogin() throws Exception {

    HashMap<String, String> loginForm = new HashMap<>();
    loginForm.put("email", "test1@naver.com");
    loginForm.put("password", "1234");

    mockMvc.perform(post("/api/v1/auth/authenticate")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .characterEncoding(StandardCharsets.UTF_8.name())
      .content(objectMapper.writeValueAsString(loginForm))
      .headers(this.httpHeaders))

      .andExpect(status().isCreated())
      .andDo(document("/user-login",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
          fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
          fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
        ),
        ApiDocumentationUtils.getResponseFieldsSnippetByFieldDescriptors(
          JsonFieldType.OBJECT,
          fieldWithPath("access_token").type(JsonFieldType.STRING).description("jwt 토큰"),
          fieldWithPath("refresh_token").type(JsonFieldType.STRING).description("refresh 토큰"),
          fieldWithPath("accessTokenExpirationTime").type(JsonFieldType.NUMBER).description("토큰 만료시간")
        )
      )
    );
  }

}
