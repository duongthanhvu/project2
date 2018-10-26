package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * CompanyController
 */
@Controller
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;
    
    @GetMapping(value="/company/{urlName}")
    public String getMethodName(Model model, @PathVariable("urlName") String urlName) {
        model.addAttribute("company",companyRepository.findFirstByUrlName(urlName));
        return "company-detail-page";
    }
    
}