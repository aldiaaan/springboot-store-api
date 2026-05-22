package com.aldian.store.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping
public class HealthCheckController {
    @RequestMapping(path = "/ping")
    public void ping(HttpServletResponse response) throws IOException {
        response.getWriter().println("pong");
    }
}
