package com.example.likeherotozero.controller;

import com.example.likeherotozero.entity.Country;
import com.example.likeherotozero.service.EmissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {

    private final EmissionService emissionService;

    public HomeController(EmissionService emissionService) {
        this.emissionService = emissionService;
    }

    @GetMapping("/")
    public String showHomePage(
            @RequestParam(required = false) String country,
            Model model
    ) {
        model.addAttribute("countries", emissionService.getAllCountries());

        if (country == null || country.isBlank()) {
            return "index";
        }

        Optional<Country> selectedCountry = emissionService.getCountryByIsoCode(country);

        if (selectedCountry.isEmpty()) {
            model.addAttribute("countryNotFound", true);
            return "index";
        }

        Country foundCountry = selectedCountry.get();

        model.addAttribute("selectedCountry", foundCountry);
        model.addAttribute("latestEmission",
                emissionService.getLatestEmission(foundCountry.getId()).orElse(null));
        model.addAttribute("emissionHistory",
                emissionService.getEmissionChartData(foundCountry.getId()));

        return "index";
    }
}