package io.github.stove99.localedemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {
    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/eng")
    public String engMain() {
        return "main-eng";
    }
}