package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.MovieService;

@Controller
public class OperationController {
    
    @Autowired
    MovieService movieService;

    @GetMapping("/")
    public String root(Model model) {
        model.addAttribute("MovieList", movieService.getAllMovie());
        return "/index";
    }

}
