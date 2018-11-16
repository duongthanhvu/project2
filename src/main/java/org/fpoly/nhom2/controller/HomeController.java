package org.fpoly.nhom2.controller;

import javax.servlet.http.HttpServletRequest;

import org.fpoly.nhom2.repository.*;
import org.fpoly.nhom2.service.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PostRepository postRepository;
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
			return "redirect:/newsfeed";
		}else{
			return "choose-account-type";
		}
	}
	
	@GetMapping("/newsfeed")
	public String showNewsfeed(Model model){
		if(loggedInUser.isAnonymousUser()){
			return "redirect:/home";
		}
		model.addAttribute("recommend_companies", companyRepository.findAll());
		model.addAttribute("recommend_jobs", jobRepository.findAll());
		model.addAttribute("posts", postRepository.findAll());
		return "newsfeed";
	}
}
