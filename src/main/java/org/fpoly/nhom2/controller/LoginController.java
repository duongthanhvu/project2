package org.fpoly.nhom2.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.fpoly.nhom2.entiry.Token;
import org.fpoly.nhom2.entiry.User;
import org.fpoly.nhom2.repository.TokenRepository;
import org.fpoly.nhom2.repository.UserRepository;
import org.fpoly.nhom2.service.EmailService;
import org.fpoly.nhom2.service.GooglePojo;
import org.fpoly.nhom2.service.GoogleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;





/**
 * LoginController
 */
@Controller
public class LoginController {

    @Autowired
	private GoogleUtils googleUtils;
	@Autowired
	private EmailService mailService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenRepository tokenRepository;
    
    @GetMapping(value="/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/login-google")
	public String loginGoogle(HttpServletRequest request) throws IOException {
		String code = request.getParameter("code");
		
		if(code == null || code.isEmpty()) {
			return "redirect:/login?google=error";
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

	@GetMapping(value="/forgot-password")
	public String getEmail() {
		return "forgot-password";
	}
	
	@PostMapping(value="/reset-password")
	public String resetPassword(Model model, @RequestParam String email,HttpServletRequest request) {
		model.addAttribute("email", email);
		if(userRepository.findByEmail(email).isEmpty()){
			model.addAttribute("error", "Chưa có tài khoản nào đăng ký sử dụng email này, vui lòng đăng ký tài khoản mới");
			return "email-sent";
		}
		mailService.sendEmailResetPassword(email, request.getServerName());
		return "email-sent";
	}
	
	@GetMapping(value="/reset-password")
	public String showChangePassword(Model model,@RequestParam("token") String tokenString) {
		Token token = tokenRepository.findByToken(tokenString);
        if(token == null){
            model.addAttribute("error", "Mã token không hợp lệ!");
            return "reset-password";
        }else{
            return "reset-password";
        }
	}
	
	@PostMapping(value="/change-password")
	public String changePassword(Model model,@RequestParam String password, @RequestParam("token") String tokenString) {
		Token token = tokenRepository.findByToken(tokenString);
        if(token == null){
            model.addAttribute("error", "Mã token không hợp lệ!");
            return "reset-password";
        }else{
			User user = userRepository.findFirstByUsernameOrEmail("", token.getEmail());
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            userRepository.save(user);
            tokenRepository.delete(token);
            return "redirect:/login";
        }
	}
	
}