package org.fpoly.nhom2.service;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.FollowedCompany;
import org.fpoly.nhom2.repository.FollowedCompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FollowService
 */
@Component
public class FollowService {

    @Autowired
    private LoggedInUser loggedInUser;
    @Autowired
    private FollowedCompanyRepo followedCompanyRepo;

    public FollowedCompany getFollowStatus(Company company) {
        if (loggedInUser.isAnonymousUser()) {
            return null;
        }
        return followedCompanyRepo.findByUserAndCompany(loggedInUser.getUser(), company);
    }

    public boolean isFollowed(Company company){
        if (loggedInUser.isAnonymousUser()) {
            return false;
        }
        FollowedCompany fc = followedCompanyRepo.findByUserAndCompany(loggedInUser.getUser(), company);
        if(fc == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean followToggle(Company company){
        if (loggedInUser.isAnonymousUser()) {
            return false;
        }
        FollowedCompany fc = followedCompanyRepo.findByUserAndCompany(loggedInUser.getUser(), company);
        if(fc == null){
            fc = new FollowedCompany();
            fc.setNews(true);
            fc.setProduct(true);
            fc.setRecruitment(true);
            fc.setCompany(company);
            fc.setUser(loggedInUser.getUser());
            followedCompanyRepo.save(fc);
            return true;
        }else{
            followedCompanyRepo.delete(fc);
            return false;
        }
    }
}