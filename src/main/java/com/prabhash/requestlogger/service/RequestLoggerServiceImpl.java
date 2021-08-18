package com.prabhash.requestlogger.service;

import com.prabhash.requestlogger.model.RequestDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RequestLoggerServiceImpl implements IRequestLoggerService {

    @Override
    public RequestDetails getRequestDetails(HttpServletRequest request) {

        String requestUri = getRequestUri(request);
        Map<String, String> headers = getRequestHeaders(request);

        RequestDetails requestDetails = RequestDetails.builder()
                .requestHeaders(headers)
                .requestUri(requestUri)
                .requestMethod(request.getMethod())
                .build();

        return requestDetails;
    }

    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headersEnum = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headersEnum.hasMoreElements()) {
            String key = headersEnum.nextElement();
            headers.put(key, request.getHeader(key));
            System.out.println(key + " : " + request.getHeader(key));
        }
        return headers;
    }


    private String getRequestUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String queryParam = request.getQueryString();
        StringBuilder requestUri = new StringBuilder();
        if(null != uri) {
            requestUri.append(uri);
        }
        if(null != queryParam) {
            requestUri.append("?"+queryParam);
        }
        return requestUri.toString();
    }

    @Override
    public RequestDetails getRequestDetails(HttpServletRequest request, Optional<String> requestBody) {

        String requestUri = getRequestUri(request);
        Map<String, String> headers = getRequestHeaders(request);

        String body = requestBody.isPresent() ? requestBody.get() : null;

        RequestDetails requestDetails = RequestDetails.builder()
                .requestHeaders(headers)
                .requestUri(requestUri)
                .requestMethod(request.getMethod())
                .requestBody(body)
                .build();

        return requestDetails;
    }

}
