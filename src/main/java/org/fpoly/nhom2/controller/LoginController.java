package org.fpoly.nhom2.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.fpoly.nhom2.service.GooglePojo;
import org.fpoly.nhom2.service.GoogleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * LoginController
 */
@Controller
public class LoginController {

    @Autowired
    private GoogleUtils googleUtils;
    
    @GetMapping(value="/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/login-google")
	public String loginGoogle(HttpServletRequest request) throws IOException {
		String code = request.getParameter("code");
		
		if(code == null || code.isEmpty()) {
			return "redirect:/sign-in?google=error";
		}
		String accessToken = googleUtils.getToken(code);
		GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
		UserDetails userDetail = googleUtils.builUser(googlePojo);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}

	@RequestMapping("/403")
	public String accessDenied() {
	  return "403";
	}

}