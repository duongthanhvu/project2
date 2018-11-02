package org.fpoly.nhom2.controller;

import java.util.List;

import org.fpoly.nhom2.entiry.Skill;
import org.fpoly.nhom2.entiry.Tag;
import org.fpoly.nhom2.repository.SkillRepository;
import org.fpoly.nhom2.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * TagController
 */
@Controller
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @ResponseBody
    @GetMapping(value="/api/skill/search")
    public List<Skill> searchSkill(@RequestParam String q) {
        return skillRepository.findByTitleContainingIgnoreCase(q);
    }
    
}