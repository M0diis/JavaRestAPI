import me.m0dii.RestApiApplication;
import me.m0dii.repository.MessageRepository;
import me.m0dii.repository.RoleRepository;
import me.m0dii.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
    private MockMvc mvc;
    
    @Test
    void contextLoads()
    {
        assertNotNull(userRepository);
        assertNotNull(messageRepository);
        assertNotNull(roleRepository);
    }
    
    @Test
    void getAllUsers() throws Exception
    {
        mvc.perform(get("/api/test/getAllUsers")).andExpect(status().isOk());
    }
    
    @Test
    void checkAllAccess() throws Exception
    {
        mvc.perform(get("/api/test/all")).andExpect(status().isOk());
    }
    
    @Test
    void checkModAccess() throws Exception
    {
        mvc.perform(get("/api/test/mod")).andExpect(status().isUnauthorized());
    }
    
    @Test
    void checkAdminAccess() throws Exception
    {
        mvc.perform(get("/api/test/admin")).andExpect(status().isUnauthorized());
    }
    
    
}
