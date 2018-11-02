package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.CompanyAdmin;
import org.fpoly.nhom2.entiry.Role;
import org.fpoly.nhom2.entiry.User;
import org.fpoly.nhom2.repository.CompanyAdminRepository;
import org.fpoly.nhom2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * AdminCompanyAdminController
 */
@Controller
public class CAAdminController {

    @Autowired
    private CompanyAdminRepository CARepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value="/admin/company-admin/add")
    public String addCompanyAdmin(@RequestParam Company company,@RequestParam User user) {
        if(CARepository.existsByCompanyAndUser(company, user)){
            
        }else{
            CompanyAdmin ca = new CompanyAdmin();
            ca.setCompany(company);
            ca.setUser(user);
            CARepository.save(ca);
            Role role = new Role();
            role.setRoleId(2);
            ca.getUser().setRole(role);
            userRepository.save(ca.getUser());
        }
        return "redirect:/admin/company/detail?company="+company.getCompanyId();
    }

    @GetMapping(value="/admin/company-admin/delete")
    public String getMethodName(@RequestParam CompanyAdmin ca, @RequestParam Integer company) {
        Role role = new Role();
        role.setRoleId(3);
        ca.getUser().setRole(role);
        userRepository.save(ca.getUser());
        CARepository.delete(ca);
        return "redirect:/admin/company/detail?company="+company;
    }
    
}