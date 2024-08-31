package com.prototype.springP1.repository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController
{

    @RequestMapping("/error")
    private String error(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {

            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {

                return "error-404";

            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {

                return "error-500";

            }

        }

        return "error";

    }

}
