package com.prabhash.requestlogger.service;

import com.prabhash.requestlogger.model.RequestDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public interface IRequestLoggerService {

    public RequestDetails getRequestDetails(HttpServletRequest request);

    public RequestDetails getRequestDetails(HttpServletRequest request, Optional<String> requestBody);

}
