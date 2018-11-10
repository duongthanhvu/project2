package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.entiry.Job;
import org.fpoly.nhom2.entiry.SavedJob;
import org.fpoly.nhom2.repository.SavedJobRepository;
import org.fpoly.nhom2.service.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SaveJobController
 */
@Controller
public class SaveJobController {

    @Autowired
    private SavedJobRepository savedJobRepo;
    @Autowired
    private LoggedInUser loggedInUser;

    @ResponseBody
    @GetMapping(value="/save/status")
    public String getSaveStatus(@RequestParam("job") Job job) {
        if (loggedInUser.isAnonymousUser()) {
            return "false";
        }
        SavedJob sj = savedJobRepo.findByUserAndJob(loggedInUser.getUser(), job);
        if(sj == null){
            return "false";
        }else{
            return "true";
        }
    }
    
    @ResponseBody
    @GetMapping(value="/save")
    public String toggleSave(@RequestParam("job") Job job) {
        if (loggedInUser.isAnonymousUser()) {
            return "false";
        }
        SavedJob sj = savedJobRepo.findByUserAndJob(loggedInUser.getUser(), job);
        if(sj == null){
            sj = new SavedJob();
            sj.setJob(job);
            sj.setUser(loggedInUser.getUser());
            savedJobRepo.save(sj);
            return "true";
        }else{
            savedJobRepo.delete(sj);
            return "false";
        }
    }
}