package com.prabhash.requestlogger.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDetails {

    private Map<String, String> requestHeaders;

    private String requestUri;

    private String requestBody;

    private String requestMethod;

}
