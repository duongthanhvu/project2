package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.Post;
import org.fpoly.nhom2.repository.PostRepository;
import org.fpoly.nhom2.service.ViewCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



/**
 * PostController
 */
@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ViewCountService viewCountService;
    
    @GetMapping(value="/post/{postUrl}")
    public String showPost(Model model,@PathVariable("postUrl") String postUrl) {
        Post post = postRepository.findByUrlTitle(postUrl);
        model.addAttribute("post",post);
        Company company = post.getCompany();
        model.addAttribute("company", company);
        model.addAttribute("suggestPosts", postRepository.findTop10ByCompany(company));
        viewCountService.increaseViewCount(post.getViewCount());
        return "post-detail";
    }
    
}