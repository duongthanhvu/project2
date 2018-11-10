package org.fpoly.nhom2.service;

import org.fpoly.nhom2.entiry.Profile;
import org.springframework.stereotype.Component;

/**
 * ProfileScoreService
 */
@Component(value ="scoreService")
public class ProfileScoreService {

    public double getScore(Profile profile){
        double score = 0;
        if(profile.getAvatarPicture() != null && !profile.getAvatarPicture().equals("")){
            score = score + 7;
        }
        if(profile.getDateOfBirth() != null){
            score = score + 7;
        }
        if(profile.getEmploymentHistory() != null && !profile.getEmploymentHistory().equals("")){
            score = score + 7;
        }
        if(profile.getFullname() != null && !profile.getFullname().equals("")){
            score = score + 7;
        }
        if(profile.getGender() != null && !profile.getGender().equals("")){
            score = score + 7;
        }
        if(profile.getJobLevel() != null && !profile.getJobLevel().equals("")){
            score = score + 7;
        }
        if(profile.getMartialStatus() != null){
            score = score + 7;
        }
        if(profile.getPhone() != null && !profile.getPhone().equals("")){
            score = score + 7;
        }
        if(profile.getProfessionalTitle() != null && !profile.getProfessionalTitle().equals("")){
            score = score + 7;
        }
        if(profile.getSummary() != null && !profile.getSummary().equals("")){
            score = score + 7;
        }
        if(profile.getCvs() != null && !profile.getCvs().isEmpty()){
            score = score + 7;
        }
        if(profile.getEducations() != null && !profile.getEducations().isEmpty()){
            score = score + 8;
        }
        if(profile.getAddress() != null){
            score = score + 7;
        }
        if(profile.getSkillLists() != null && !profile.getSkillLists().isEmpty()){
            score = score + 8;
        }
        return score / 100;
    }
}