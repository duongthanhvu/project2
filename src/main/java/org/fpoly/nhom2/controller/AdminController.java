package org.fpoly.nhom2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fpoly.nhom2.repository.CompanyRepository;
import org.fpoly.nhom2.repository.JobRepository;
import org.fpoly.nhom2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * AdminController
 */
@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping(value="/admin")
    public String showAdminDashboard(Model model) {
        model.addAttribute("users", userRepository.findAll(Sort.by(Direction.DESC, "userId")));
        return "admin-home";
    }

    @ResponseBody
    @GetMapping(value="/admin/json/user-per-month")
    public Map<String,List<Long>> getMethodName() {
        Map<String,List<Long>> map = new HashMap<>();
        map.put("data1", userRepository.getUserQuantityPerMonth());
        return map;
    }

    @ResponseBody
    @GetMapping(value="/admin/json/job-per-month")
    public Map<String,List<Long>> getJobCount() {
        Map<String,List<Long>> map = new HashMap<>();
        map.put("data1", jobRepository.getJobQuantityPerMonth());
        return map;
    }

    @ResponseBody
    @GetMapping(value="/admin/json/company-per-month")
    public Map<String,List<Long>> getCompanyCount() {
        Map<String,List<Long>> map = new HashMap<>();
        map.put("data1", companyRepository.getCompanyQuantityPerMonth());
        return map;
    }
    
}