package org.fpoly.nhom2.service;

import java.util.List;

import org.fpoly.nhom2.entiry.CompanyAdmin;
import org.fpoly.nhom2.entiry.User;
import org.fpoly.nhom2.repository.CompanyAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component(value = "loggedinuser")
public class LoggedInUser {

    @Autowired
    CompanyAdminRepository caRepository;

    public User getUser() {
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }

    public int getDefaultUserId(){
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser().getUserId();
    };

    public List<CompanyAdmin> getCA(){
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getCa();
    }

    public int getDefaultCompanyId(){
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getDefaultCompanyId();
    }

    public void setDefaultCompanyId(int id){
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setDefaultCompanyId(id);
    }
}