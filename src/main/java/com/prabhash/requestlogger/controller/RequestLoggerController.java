package com.prabhash.requestlogger.controller;

import com.prabhash.requestlogger.model.RequestDetails;
import com.prabhash.requestlogger.service.IRequestLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class RequestLoggerController {

    @Autowired
    private IRequestLoggerService requestLoggerService;

    @GetMapping("/**")
    public ResponseEntity<RequestDetails> getRequestDetailsForGetApi(HttpServletRequest request, HttpServletResponse response) {
        RequestDetails requestDetails = requestLoggerService.getRequestDetails(request);
        return new ResponseEntity<>(requestDetails, HttpStatus.OK);
    }

    @PostMapping("/**")
    public ResponseEntity<RequestDetails> getRequestDetailsForPostApi(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Optional<String> requestBody) {
        RequestDetails requestDetails = requestLoggerService.getRequestDetails(request, requestBody);
        return new ResponseEntity<>(requestDetails, HttpStatus.CREATED);
    }


}
