package com.prabhash.requestlogger.controller;

import com.google.gson.Gson;
import com.prabhash.requestlogger.model.RequestDetails;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RequestLoggerController.class)
@ExtendWith(SpringExtension.class)
public class RequestLoggerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void checkGetApiResponse2XXForDefaultPath() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }


    @Test
    void checkGetApiForDefaultRequestUri() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(jsonPath("$.requestUrl").value("/"));
    }

    @Test
    void checkGetApiForCustomSingleRequestUri() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(jsonPath("$.requestUrl").value("/test"));
    }

    @Test
    void checkGetApiForCustomMultipleRequestUri() throws Exception {
        mockMvc.perform(get("/test/1234"))
                .andExpect(jsonPath("$.requestUrl").value("/test/1234"));
    }

    @Test
    void checkGetApiForPathWithSingleQueryParam() throws Exception {
        mockMvc.perform(get("/abcd?q=123"))
                .andExpect(jsonPath("$.requestUrl").value("/abcd?q=123"));
    }

    @Test
    void checkGetApiForPathWithMultipleQueryParam() throws Exception {
        mockMvc.perform(get("/abcd?q=123&x=123"))
                .andExpect(jsonPath("$.requestUrl").value("/abcd?q=123&x=123"));
    }

    //@Test
    void checkGetApiForPathWithSpace() throws Exception {
        mockMvc.perform(get(new URI("/abcd 123")))
                .andExpect(jsonPath("$.requestUrl").value("/abcd 123"));
    }

    @Test
    void checkGetApiForRequestHeaders() throws Exception {
        mockMvc.perform(get("/test").header("name","nik").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.headers", Matchers.hasEntry("name","nik")));
    }

    @Test
    void checkPostApiForEmptyRequestBody() throws Exception {
        mockMvc.perform(post("/test").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void checkPostApiForStringRequestBody() throws Exception {
        mockMvc.perform(post("/test").content("Hello From Test!"))
                .andExpect(jsonPath("$.requestBody").value("Hello From Test!"));
    }

    @Test
    void checkPostApiForJsonRequestBody() throws Exception {
        RequestDetails requestDetails = new RequestDetails(new HashMap<>(), "/test", "test");
        Gson gson = new Gson();
        String json = gson.toJson(requestDetails);
        mockMvc.perform(post("/test").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.requestBody").value(json));
    }

}
