package course.udemy.springboot.accounts.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @GetMapping("/greetings")
    public String greeting() {
        return "Hello World!";
    }
}
