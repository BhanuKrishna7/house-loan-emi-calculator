package com.example.houseloan.controller;

import com.example.houseloan.service.EmiService;
import com.example.houseloan.service.EmiService.EmiResult;
import com.example.houseloan.util.PdfGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmiController {

    @Autowired
    private EmiService emiService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(Model model,
                            @RequestParam double principal,
                            @RequestParam double rate,
                            @RequestParam int tenureMonths,
                            @RequestParam(required = false) String name) {

        EmiResult result = emiService.calculateEmi(principal, rate, tenureMonths);
        model.addAttribute("principal", principal);
        model.addAttribute("rate", rate);
        model.addAttribute("tenure", tenureMonths);
        model.addAttribute("emi", String.format("%.2f", result.getEmi()));
        model.addAttribute("totalPayment", String.format("%.2f", result.getTotalPayment()));
        model.addAttribute("totalInterest", String.format("%.2f", result.getTotalInterest()));
        model.addAttribute("name", (name == null || name.isEmpty()) ? "Customer" : name);
        return "index";
    }

    @PostMapping("/downloadPdf")
    public void downloadPdf(HttpServletResponse response,
                            @RequestParam double principal,
                            @RequestParam double rate,
                            @RequestParam int tenureMonths,
                            @RequestParam(required = false) String name) throws Exception {
        EmiResult result = emiService.calculateEmi(principal, rate, tenureMonths);
        byte[] pdf = PdfGenerator.generateEmiReport(name == null ? "Customer" : name,
                principal, rate, tenureMonths,
                result.getEmi(), result.getTotalPayment(), result.getTotalInterest());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=emi-report.pdf");
        response.getOutputStream().write(pdf);
    }
}