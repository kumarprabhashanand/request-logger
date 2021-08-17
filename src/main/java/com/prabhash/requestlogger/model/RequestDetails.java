package com.prabhash.requestlogger.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDetails {

    private Map<String, String> headers;

    private String requestUrl;

    private String requestBody;

}
