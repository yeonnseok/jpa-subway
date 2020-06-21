package woowa.bossdog.subway.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceIndexController {
    @GetMapping(value = "/service", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "service/index";
    }
}