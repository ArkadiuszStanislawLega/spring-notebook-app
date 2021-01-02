package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JobsListControllerTest {
    @MockBean
    private JobsList service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void notLoginUserAndRedirect() throws Exception {
        mockMvc.perform(get("/jobsList/home"))
                .andExpect(status().isFound())
                .andDo(MockMvcResultHandlers.print());
    }
}

