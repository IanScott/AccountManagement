package my.project.accman.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import my.project.accman.TestConfiguration;
import my.project.accman.api.exceptionhandlers.ExceptionHandlerAdvice;
import my.project.accman.exceptions.InvalidAccountException;
import my.project.accman.exceptions.MissingAccountException;
import my.project.accman.model.Account;
import my.project.accman.testutils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest()
@ContextConfiguration(classes = {AccountController.class, TestConfiguration.class})
class AccountControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AccountController controller;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerAdvice())
                .build();
    }

    @Test
    void createAccountTest(){
        final var testAccount = new Account();
        assertTrue(post("/v1/account/", testAccount) instanceof InvalidAccountException);

        final var validAccount = TestUtils.createValidAccount();
        assertTrue(post("/v1/account/", validAccount) instanceof Account);
    }

    @Test
    void getAccountTest()  {
        assertTrue(get("/v1/account/0") instanceof MissingAccountException);
        assertTrue(get("/v1/account/1") instanceof Account);
    }

    @Test
    void deleteAccountTest(){
        assertTrue(delete("/v1/account/0") instanceof MissingAccountException);

        Boolean isSuccessfull = (Boolean) ((Map)delete("/v1/account/1")).get("success");
        assertTrue(isSuccessfull);
    }

    @Test
    void updateAccountTest(){
        final var testAccount = new Account();
        assertTrue(put("/v1/account/1", testAccount) instanceof InvalidAccountException);


        Account account = get("/v1/account/1");

        assertTrue(put("/v1/account/1", account) instanceof Account);

        assertTrue(put("/v1/account/0", account) instanceof MissingAccountException);
    }

    @Test
    void inValidUpdate(){
        Account account = get("/v1/account/1");
        System.out.println(account);

        Account update = TestUtils.clone(account);
        update.setUuid("new value");

        assertTrue(put("/v1/account/1", update) instanceof InvalidAccountException);

        update.setUuid("1");
        update.setTemporary(true);
        update.setClosureDate(new Date());
        assertTrue(put("/v1/account/1", update) instanceof InvalidAccountException);

    }

    @SneakyThrows
    private <T> T post(String url, Account body)  {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(url)
                .content(mapper.writeValueAsString(body))
                .contentType(APPLICATION_JSON);

        Object res = mockMvc
                .perform(request)

                .andExpect(request().asyncStarted())
                .andReturn()
                .getAsyncResult();

        return ((T)res);
    }

    @SneakyThrows
    private <T> T put(String url, Account body)  {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(url)
                .content(mapper.writeValueAsString(body))
                .contentType(APPLICATION_JSON);

        Object res = mockMvc
                .perform(request)

                .andExpect(request().asyncStarted())
                .andReturn()
                .getAsyncResult();

        return ((T)res);
    }

    @SneakyThrows
    private <T> T get(String url) {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(url)
                .contentType(APPLICATION_JSON);

        Object res = mockMvc.perform(request)
                .andExpect(request().asyncStarted())
                .andReturn()
                .getAsyncResult();

        return ((T)res);
    }

    @SneakyThrows
    private <T> T delete(String url) {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(url)
                .contentType(APPLICATION_JSON);

        Object res = mockMvc.perform(request)
                .andExpect(request().asyncStarted())
                .andReturn()
                .getAsyncResult();

        return ((T)res);
    }
}