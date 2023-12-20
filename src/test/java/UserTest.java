import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import app.Main;
import app.dto.UserStoreRequest;
import app.services.UserService;
import app.controllers.UserController;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes=Main.class)
public class UserTest {
    
    private Integer numberOfTests       = 0;
    private Integer numberOfPassedTests = 0;
    private static final String PATH = "/api/v1/users/";

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper = new ObjectMapper();
    @MockBean  private UserService userService;

    @BeforeClass
    public static void init() { 
        System.out.println("Testing phase.");
    }

    @AfterClass public static void destroy() { }

    @Before public  void executeBefore() { numberOfTests++; }
    @After  public  void executeAfter() { System.out.println("Test finished."); }

    @Test
    public void createUserTest() throws Exception {
        UserStoreRequest payload = new UserStoreRequest(
            "Testing", 
            "test@email.com", 
            "password", 
            "40766692709", 
            "rol_uFMaGQfnzY2siIlDJWTqUDSDI"
        );

        String requestBody = mapper.writeValueAsString(payload);

        mockMvc.perform(post(PATH)
            .contentType("application/json").content(requestBody))
            .andExpect(status().isOk());
        numberOfPassedTests++;
    }
}
