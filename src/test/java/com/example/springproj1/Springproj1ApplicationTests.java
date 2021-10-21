package com.example.springproj1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// @ContextConfiguration(classes = { ApplicationConfig.class })
@WebAppConfiguration
class Springproj1ApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testSuccessfulResponse() {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult.getResponse());
    }

    @Test
    public void testSuccessfulResponse2() {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos/1/branches"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult.getResponse());
    }

    @Test
    public void testSuccessfulResponse3() {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos/1/branches/1/commits"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult.getResponse());
    }
}