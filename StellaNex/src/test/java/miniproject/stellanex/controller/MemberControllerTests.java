//package miniproject.stellanex.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import miniproject.stellanex.dto.MemberJoinRequest;
//import miniproject.stellanex.service.MemberService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest
//public class MemberControllerTests {
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    MemberService memberService;
//
//    @Test
//    @DisplayName("회원 가입 성공!")
//    @WithMockUser
//    void join() throws Exception {
//        String email = "sonen@naver.com";
//        String password = "test";
//        String name = "test";
//        String birth = "1992-11-25";
//
//        mockMvc.perform(post("/member/join").with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new MemberJoinRequest(email, password, name, birth))))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//}