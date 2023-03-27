package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class RollDiceController {

    private Random random = new Random();

    @GetMapping("/roll-dice")
    public String showRollDiceForm() {
        return "roll-dice";
    }

    @GetMapping ("/roll-dice/{guess}")
    public String rollDice(@PathVariable int guess, Model model) {
        int roll = random.nextInt(6) + 1;
        model.addAttribute("roll", roll);
        model.addAttribute("guess", guess);
        model.addAttribute("match", roll == guess);
        return "roll-result";


    }
}


