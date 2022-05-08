import com.fasterxml.jackson.databind.ObjectMapper;
import me.m0dii.RestApiApplication;
import me.m0dii.payload.request.LoginRequest;
import me.m0dii.repository.MessageRepository;
import me.m0dii.repository.RoleRepository;
import me.m0dii.repository.UserRepository;
import me.m0dii.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = RestApiApplication.class)
@EnableConfigurationProperties
@AutoConfigureMockMvc
class RestApiApplicationTests
{
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    private MockMvc mvc;
    
    @Test
    void contextLoads()
    {
        assertNotNull(userRepository);
        assertNotNull(messageRepository);
        assertNotNull(roleRepository);
    }
    
    @Test
    void checkIfAdminUserExists()
    {
        assertTrue(userRepository.existsByUsername("admin"));
        assertTrue(userRepository.existsByEmail("admin@admin.com"));
    }
    
    @Test
    void getAllUsers() throws Exception
    {
        mvc.perform(get("/api/test/getAllUsers")).andExpect(status().isOk());
    }
    
    @Test
    void checkAllAccessWithoutAuthorization() throws Exception
    {
        mvc.perform(get("/api/test/all")).andExpect(status().isOk());
    }
    
    @Test
    void checkModAccessWithoutAuthorization() throws Exception
    {
        mvc.perform(get("/api/test/mod")).andExpect(status().isUnauthorized());
    }
    
    @Test
    void checkAdminAccessWithoutAuthorization() throws Exception
    {
        mvc.perform(get("/api/test/admin")).andExpect(status().isUnauthorized());
    }
    
    MockHttpServletResponse login(String username, String password) throws Exception
    {
        LoginRequest loginRequest = new LoginRequest(username, password);
        
        ObjectMapper mapper = new ObjectMapper();
        
        String json = mapper.writeValueAsString(loginRequest);
        
        return mvc.perform(post("/api/auth/signin")
                .contentType("application/json")
                .content(json))
                .andReturn()
                .getResponse();
    }
    
    @Test
    void checkAdminAccessWithAuthorizationAsUser() throws Exception
    {
        MockHttpServletResponse response = login("user", "user123");
    
        Cookie cookie = response.getCookie("m0dii-jwt");
    
        assertNotNull(cookie);
    
        mvc.perform(get("/api/test/admin")
            .header("Authorization", "Bearer " + cookie.getValue()))
            .andExpect(status().isForbidden());
    }
    @Test
    void checkAdminAccessWithAuthorizationAsAdmin() throws Exception
    {
        MockHttpServletResponse response = login("admin", "admin123");
        
        Cookie cookie = response.getCookie("m0dii-jwt");
    
        assertNotNull(cookie);
        
        mvc.perform(get("/api/test/admin")
            .header("Authorization", "Bearer " + cookie.getValue()))
            .andExpect(status().isOk());
    }
}
