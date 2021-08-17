package com.prabhash.requestlogger.controller;

import com.prabhash.requestlogger.model.RequestDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class RequestLoggerController {

    @GetMapping("/**")
    public ResponseEntity<RequestDetails> getRequestDetailsForGetApi(HttpServletRequest request, HttpServletResponse response) {
        RequestDetails requestDetails = new RequestDetails();
        String uri = request.getRequestURI();
        String queryParam = request.getQueryString();
        StringBuilder requestUri = new StringBuilder();
        if(null != uri) {
            requestUri.append(uri);
        }
        if(null != queryParam) {
            requestUri.append("?"+queryParam);
        }

        Enumeration<String> headersEnum = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headersEnum.hasMoreElements()) {
            String key = headersEnum.nextElement();
            headers.put(key, request.getHeader(key));
            System.out.println(key + " : " + request.getHeader(key));
        }

        requestDetails.setHeaders(headers);
        requestDetails.setRequestUrl(requestUri.toString());
        return new ResponseEntity<>(requestDetails, HttpStatus.OK);
    }

    @PostMapping("/**")
    public ResponseEntity<RequestDetails> getRequestDetailsForPostApi(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Optional<String> requestBody) {
        RequestDetails requestDetails = new RequestDetails();
        String uri = request.getRequestURI();
        String queryParam = request.getQueryString();
        StringBuilder requestUri = new StringBuilder();
        if(null != uri) {
            requestUri.append(uri);
        }
        if(null != queryParam) {
            requestUri.append("?"+queryParam);
        }

        Enumeration<String> headersEnum = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headersEnum.hasMoreElements()) {
            String key = headersEnum.nextElement();
            headers.put(key, request.getHeader(key));
        }

        if(requestBody.isPresent()) {
            requestDetails.setRequestBody(requestBody.get());
        }

        requestDetails.setHeaders(headers);
        requestDetails.setRequestUrl(requestUri.toString());
        return new ResponseEntity<>(requestDetails, HttpStatus.CREATED);
    }


}
