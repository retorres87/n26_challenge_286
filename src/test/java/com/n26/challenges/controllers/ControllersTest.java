package com.n26.challenges.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEmptyStatistics() throws Exception {
        this.mockMvc.perform(get("/statistics")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"count\":0,\"sum\":0.0,\"avg\":0.0,\"max\":0.0,\"min\":0.0}")));
    }


    @Test
    public void testAddTransaction() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        this.mockMvc.perform(post("/transactions").content("{\n" +
                "\"amount\": 12.3,\n" +
                "\"timestamp\": " + currentTimeMillis + "\n" +
                "}").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());
    }
}
