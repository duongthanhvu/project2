package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * FollowController
 */
@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    @ResponseBody
    @GetMapping(value="/follow/status")
    public String getFollowStatus(@RequestParam("company") Company company) {
        if(followService.isFollowed(company)){
            return "true";
        }
        return "false";
    }
    
    @ResponseBody
    @GetMapping(value="/follow")
    public String follow(@RequestParam("company") Company company) {
        if(followService.followToggle(company)){
            return "true";
        }
        return "false";
    }
    
}