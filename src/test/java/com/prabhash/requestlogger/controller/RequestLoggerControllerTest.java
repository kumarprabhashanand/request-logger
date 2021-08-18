package com.prabhash.requestlogger.controller;

import com.google.gson.Gson;
import com.prabhash.requestlogger.model.RequestDetails;
import com.prabhash.requestlogger.service.IRequestLoggerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RequestLoggerController.class)
@ExtendWith(SpringExtension.class)
public class RequestLoggerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IRequestLoggerService requestLoggerService;

    @Test
    void checkGetApiResponse2XXForDefaultPath() throws Exception {
        given(requestLoggerService.getRequestDetails(any()))
                .willReturn(RequestDetails.builder().requestUri("/")
                        .requestMethod(HttpMethod.GET.toString()).build());
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
        verify(requestLoggerService).getRequestDetails(any());
    }

    @Test
    void checkGetApiForRequestMethod() throws Exception {
        given(requestLoggerService.getRequestDetails(any()))
                .willReturn(RequestDetails.builder().requestUri("/")
                        .requestMethod(HttpMethod.GET.toString()).build());
        mockMvc.perform(get("/"))
                .andExpect(jsonPath("$.requestMethod").value(HttpMethod.GET.toString()));
        verify(requestLoggerService).getRequestDetails(any());
    }


    @Test
    void checkGetApiForDefaultRequestUri() throws Exception {
        given(requestLoggerService.getRequestDetails(any()))
                .willReturn(RequestDetails.builder().requestUri("/")
                        .requestMethod(HttpMethod.GET.toString()).build());
        mockMvc.perform(get("/"))
                .andExpect(jsonPath("$.requestUri").value("/"));
        verify(requestLoggerService).getRequestDetails(any());
    }

    @Test
    void checkGetApiForCustomSingleRequestUri() throws Exception {
        given(requestLoggerService.getRequestDetails(any()))
                .willReturn(RequestDetails.builder().requestUri("/test")
                        .requestMethod(HttpMethod.GET.toString()).build());
        mockMvc.perform(get("/test"))
                .andExpect(jsonPath("$.requestUri").value("/test"));
        verify(requestLoggerService).getRequestDetails(any());
    }

    @Test
    void checkGetApiForCustomMultipleRequestUri() throws Exception {
        given(requestLoggerService.getRequestDetails(any()))
                .willReturn(RequestDetails.builder().requestUri("/test/1234")
                        .requestMethod(HttpMethod.GET.toString()).build());
        mockMvc.perform(get("/test/1234"))
                .andExpect(jsonPath("$.requestUri").value("/test/1234"));
        verify(requestLoggerService).getRequestDetails(any());
    }

    @Test
    void checkGetApiForPathWithSingleQueryParam() throws Exception {
        given(requestLoggerService.getRequestDetails(any()))
                .willReturn(RequestDetails.builder().requestUri("/abcd?q=123")
                        .requestMethod(HttpMethod.GET.toString()).build());
        mockMvc.perform(get("/abcd?q=123"))
                .andExpect(jsonPath("$.requestUri").value("/abcd?q=123"));
        verify(requestLoggerService).getRequestDetails(any());
    }

    @Test
    void checkGetApiForPathWithMultipleQueryParam() throws Exception {
        given(requestLoggerService.getRequestDetails(any()))
                .willReturn(RequestDetails.builder().requestUri("/abcd?q=123&x=123")
                        .requestMethod(HttpMethod.GET.toString()).build());
        mockMvc.perform(get("/abcd?q=123&x=123"))
                .andExpect(jsonPath("$.requestUri").value("/abcd?q=123&x=123"));
        verify(requestLoggerService).getRequestDetails(any());
    }

    //@Test
    void checkGetApiForPathWithSpace() throws Exception {
        mockMvc.perform(get(new URI("/abcd 123")))
                .andExpect(jsonPath("$.requestUri").value("/abcd 123"));
    }

    @Test
    void checkGetApiForRequestHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("name", "nik");
        headers.put("content-type", "application/json");
        given(requestLoggerService.getRequestDetails(any()))
                .willReturn(RequestDetails.builder().requestUri("/abcd?q=123&x=123")
                        .requestHeaders(headers)
                        .requestMethod(HttpMethod.GET.toString()).build());
        mockMvc.perform(get("/test").header("name","nik").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.requestHeaders", Matchers.hasEntry("name","nik")));
        verify(requestLoggerService).getRequestDetails(any());
    }

    @Test
    void checkPostApiForEmptyRequestBody() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("name", "nik");
        headers.put("content-type", "application/json");
        given(requestLoggerService.getRequestDetails(any(), any()))
                .willReturn(RequestDetails.builder().requestUri("/test")
                        .requestHeaders(headers)
                        .requestMethod(HttpMethod.POST.toString()).build());
        mockMvc.perform(post("/test").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(requestLoggerService).getRequestDetails(any(), any());
    }

    @Test
    void checkPostApiForRequestMethod() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("name", "nik");
        headers.put("content-type", "application/json");
        given(requestLoggerService.getRequestDetails(any(), any()))
                .willReturn(RequestDetails.builder().requestUri("/test")
                        .requestHeaders(headers)
                        .requestMethod(HttpMethod.POST.toString()).build());
        mockMvc.perform(post("/test").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.requestMethod").value(HttpMethod.POST.toString()));
        verify(requestLoggerService).getRequestDetails(any(), any());
    }

    @Test
    void checkPostApiForStringRequestBody() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("name", "nik");
        headers.put("content-type", "application/json");
        given(requestLoggerService.getRequestDetails(any(), any()))
                .willReturn(RequestDetails.builder().requestUri("/test")
                        .requestHeaders(headers)
                        .requestBody("Hello From Test!")
                        .requestMethod(HttpMethod.POST.toString()).build());
        mockMvc.perform(post("/test").content("Hello From Test!"))
                .andExpect(jsonPath("$.requestBody").value("Hello From Test!"));
        verify(requestLoggerService).getRequestDetails(any(), any());
    }

    @Test
    void checkPostApiForJsonRequestBody() throws Exception {
        RequestDetails requestDetails = new RequestDetails(new HashMap<>(), "/test", "test", HttpMethod.POST.toString());
        Gson gson = new Gson();
        String json = gson.toJson(requestDetails);

        Map<String, String> headers = new HashMap<>();
        headers.put("name", "nik");
        headers.put("content-type", "application/json");
        given(requestLoggerService.getRequestDetails(any(), any()))
                .willReturn(RequestDetails.builder().requestUri("/test")
                        .requestHeaders(headers)
                        .requestBody(json)
                        .requestMethod(HttpMethod.POST.toString()).build());
        mockMvc.perform(post("/test").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.requestBody").value(json));
        verify(requestLoggerService).getRequestDetails(any(), any());
    }

}
