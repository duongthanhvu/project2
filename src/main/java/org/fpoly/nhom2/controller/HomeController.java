package org.fpoly.nhom2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.FollowedCompany;
import org.fpoly.nhom2.entiry.User;
import org.fpoly.nhom2.repository.*;
import org.fpoly.nhom2.service.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	private POCRepository pOCRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private FollowedCompanyRepo followedCompanyRepo;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LoggedInUser loggedInUser;

	@RequestMapping({ "/", "home", "index.html" })
	public String showIndex(Model model, HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			model.addAttribute("recommend_companies", companyRepository.getMostFollowedCompanies(PageRequest.of(0, 6)));
			model.addAttribute("recommend_jobs",
					jobRepository.findAll(PageRequest.of(0, 6, Sort.by("jobId").descending())));
			model.addAttribute("jobs_created_today", jobRepository.numberOfJobsCreatedToday());
			model.addAttribute("provinces", pOCRepository.findAll());
			model.addAttribute("tags", tagRepository.findAll());
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
		} else {
			return "choose-account-type";
		}
	}

	@GetMapping("/newsfeed")
	public String showNewsfeed(Model model) {
		if (loggedInUser.isAnonymousUser()) {
			return "redirect:/home";
		}
		model.addAttribute("recommend_companies", companyRepository.getMostFollowedCompanies(PageRequest.of(0, 10)));
		model.addAttribute("recommend_jobs",
				jobRepository.findAll(PageRequest.of(0, 10, Sort.by("jobId").descending())));
		List<Company> companies = new ArrayList<>();
		User user = userRepository.getOne(loggedInUser.getDefaultUserId());
		int quantity = 0;
		for (FollowedCompany fc : followedCompanyRepo.findByUser(user)) {
			companies.add(fc.getCompany());
			quantity++;
		}
		if(quantity == 0){
			companies = null;
		}
		model.addAttribute("posts", postRepository.findPostFromFollowedC(companies, quantity,
				PageRequest.of(0, 10, Sort.by("postId").descending())));
		model.addAttribute("jobs", jobRepository.findJobFromFollowedC(companies, quantity,
				PageRequest.of(0, 10, Sort.by("jobId").descending())));
		return "newsfeed";
	}
}
