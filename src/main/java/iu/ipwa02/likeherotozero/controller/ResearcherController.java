package iu.ipwa02.likeherotozero.controller;

import iu.ipwa02.likeherotozero.dto.EmissionRecordForm;
import iu.ipwa02.likeherotozero.service.EmissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResearcherController {

    private final EmissionService emissionService;

    private void addCountriesToModel(Model model) {
        model.addAttribute("countries", emissionService.getAllCountries());
    }

    public ResearcherController(EmissionService emissionService) {
        this.emissionService = emissionService;
    }

    @GetMapping("/researcher")
    public String showResearcherDashboard(Model model) {
        model.addAttribute("emissionRecords", emissionService.getAllEmissionRecords());
        return "researcher/dashboard";
    }

    @GetMapping("/researcher/emissions/new")
    public String showCreateEmissionForm(Model model) {
        model.addAttribute("emissionRecordForm", new EmissionRecordForm());
        addCountriesToModel(model);

        return "researcher/emission-form";
    }

    @PostMapping("/researcher/emissions")
    public String createEmissionRecord(
            @Valid EmissionRecordForm emissionRecordForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            addCountriesToModel(model);
            return "researcher/emission-form";
        }

        try {
            emissionService.createEmissionRecord(emissionRecordForm);
        } catch (IllegalArgumentException exception) {
            addCountriesToModel(model);
            model.addAttribute("formError", exception.getMessage());
            return "researcher/emission-form";
        }

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Emission record saved successfully."
        );

        return "redirect:/researcher";
    }

    @GetMapping("/researcher/emissions/{id}/edit")
    public String showEditEmissionForm(
            @PathVariable Long id,
            Model model
    ) {
        try {
            model.addAttribute(
                    "emissionRecordForm",
                    emissionService.getEmissionRecordForEditing(id)
            );
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Emission record not found."
            );
        }

        addCountriesToModel(model);
        model.addAttribute("recordId", id);

        return "researcher/emission-form";
    }

    @PostMapping("/researcher/emissions/{id}")
    public String updateEmissionRecord(
            @PathVariable Long id,
            @Valid EmissionRecordForm emissionRecordForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (!emissionService.emissionRecordExists(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Emission record not found."
            );
        }
        if (bindingResult.hasErrors()) {
            addCountriesToModel(model);
            model.addAttribute("recordId", id);
            return "researcher/emission-form";
        }

        try {
            emissionService.updateEmissionRecord(id, emissionRecordForm);
        } catch (IllegalArgumentException exception) {
            addCountriesToModel(model);
            model.addAttribute("recordId", id);
            model.addAttribute("formError", exception.getMessage());
            return "researcher/emission-form";
        }

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Emission record updated successfully."
        );

        return "redirect:/researcher";
    }

    @PostMapping("/researcher/emissions/{id}/delete")
    public String deleteEmissionRecord(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            emissionService.deleteEmissionRecord(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Emission record deleted successfully."
            );

        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Emission record not found."
            );
        }

        return "redirect:/researcher";
    }
}