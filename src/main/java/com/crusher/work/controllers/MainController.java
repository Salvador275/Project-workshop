package com.crusher.work.controllers;

import com.crusher.work.model.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Page page = restTemplate.getForObject("http://www.mocky.io/v2/5c7db5e13100005a00375fda", Page.class);
        String text = page.getResult().replaceAll("\\s+", "_");
        model.addAttribute("title", text);
        return "home";
    }

}
