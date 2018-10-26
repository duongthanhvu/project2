package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.repository.CompanyRepository;
import org.fpoly.nhom2.repository.POCRepository;
import org.fpoly.nhom2.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

	@Autowired CompanyRepository companyRepository;
	@Autowired POCRepository pOCRepository;
	@Autowired TagRepository tagRepository;

	@RequestMapping({"/","home","index.html"})
	public String showIndex(Model model) {
		model.addAttribute("provinces", pOCRepository.findAll());
		model.addAttribute("tags", tagRepository.findAll());
		return "home";
	}
	
}
