package com.example.oauth2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;


    @RestController
    public class ExampleController {

        @RequestMapping("/")
        public String email(Principal principal) {
            return "Hello " + principal.getName();
        }

    }
