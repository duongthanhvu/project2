package org.fpoly.nhom2.controller;

import java.util.List;

import org.fpoly.nhom2.entiry.Tag;
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
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @ResponseBody
    @GetMapping(value="/api/tag/search")
    public List<Tag> searchTag(@RequestParam String q) {
        return tagRepository.findByTitleContainingIgnoreCase(q);
    }
    
}