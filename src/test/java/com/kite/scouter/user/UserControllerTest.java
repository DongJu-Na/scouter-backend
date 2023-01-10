package com.kite.scouter.user;

import com.kite.scouter.common.document.ApiDocumentationUtils;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import org.springframework.restdocs.templates.TemplateFormat;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
  @Autowired
  private PasswordEncoder passwordEncoder;

  private HttpHeaders httpHeaders;
  private String userAuthorization;

  @BeforeEach
  void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        //.apply(springSecurity())
        .apply(documentationConfiguration(restDocumentation))
        .build();

    this.httpHeaders = new HttpHeaders();

  }

  @DisplayName("유저 로그인")
  @Test
  void getUserLoing() throws Exception {

    HashMap<String, String> loginForm = new HashMap<>();
    loginForm.put("nickName", "test1234");
    loginForm.put("email", "test1234@naver.com");
    loginForm.put("password", "1234");

    ResultActions result = mockMvc.perform(post("/api/v1/auth/register")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding(StandardCharsets.UTF_8.name())
        .content(objectMapper.writeValueAsString(loginForm))
        .headers(this.httpHeaders));

    HashMap<String, String> loginForm2 = new HashMap<>();
    loginForm2.put("email", "test1234@naver.com");
    loginForm2.put("password", "1234");

    ResultActions result2 = mockMvc.perform(post("/api/v1/auth/authenticate")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON)
      .characterEncoding(StandardCharsets.UTF_8.name())
      .content(objectMapper.writeValueAsString(loginForm2))
      .headers(this.httpHeaders));

    result2.andExpect(status().isOk())
      .andDo(document("/post",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
            fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
        ),
        responseFields(
            fieldWithPath("token").description("jwt 토큰")
        )
      )
    );
  }

}
