package org.fpoly.nhom2.controller;

import javax.servlet.http.HttpServletRequest;

import org.fpoly.nhom2.repository.CompanyRepository;
import org.fpoly.nhom2.repository.POCRepository;
import org.fpoly.nhom2.repository.TagRepository;
import org.fpoly.nhom2.service.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private POCRepository pOCRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	LoggedInUser loggedInUser;

	@RequestMapping({ "/", "home", "index.html" })
	public String showIndex(Model model, HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "home";
		}
		if (request.isUserInRole("ROLE_admin")) {
			return "redirect:/admin";
		}
		if (request.isUserInRole("ROLE_ca")) {
			return "redirect:/ca/home";
		}
		if (request.isUserInRole("ROLE_user") && loggedInUser.getUser().getProfile() != null) {
			model.addAttribute("provinces", pOCRepository.findAll());
			model.addAttribute("tags", tagRepository.findAll());
			return "home";
		}else{
			return "choose-account-type";
		}
	}
	
}
