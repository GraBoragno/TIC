package uy.edu.um.proyectoTIC.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuccessfulPurchase {
    //Solo devuelve la pagina
    @GetMapping("/successfulPurchase")
    public String successfulPurchase() {return "successfulPurchase";}
}
