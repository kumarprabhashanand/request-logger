package com.prabhash.requestlogger.service;

import com.prabhash.requestlogger.model.RequestDetails;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class RequestLoggerServiceTest {

    @Autowired
    IRequestLoggerService requestLoggerService;

    private static MockHttpServletRequest request;
    private static RequestDetails requestDetails;

    @BeforeAll
    static void setUp() {
        request = new MockHttpServletRequest();
        request.setRequestURI("/test");
        request.setMethod(HttpMethod.GET.toString());
        request.addHeader("name", "nik");
        request.addHeader("Content-Type", MediaType.APPLICATION_JSON.toString());

        Map<String, String> headers = new HashMap<>();
        headers.put("name", "nik");
        headers.put("Content-Type", MediaType.APPLICATION_JSON.toString());
        requestDetails = RequestDetails.builder()
                .requestHeaders(headers)
                .requestMethod(HttpMethod.GET.toString())
                .requestUri("/test").build();

    }

    @Configuration
    public static class Config {
        @Bean
        public IRequestLoggerService getRequestLoggerService() {
            return new RequestLoggerServiceImpl();
        }
    }

    @Test
    void getRequestDetailsWithoutBodyTest() {
        assertThat(requestDetails).usingRecursiveComparison().ignoringFields("requestBody")
                .isEqualTo(requestLoggerService.getRequestDetails(request));
    }

    @Test
    void getRequestDetailsWithBodyTest() {
        requestDetails.setRequestBody("This is request body");
        assertThat(requestDetails).usingRecursiveComparison()
                .isEqualTo(requestLoggerService.getRequestDetails(request, Optional.of("This is request body")));
    }
}