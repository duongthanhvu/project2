package org.fpoly.nhom2.controller;

import javax.servlet.http.HttpServletRequest;

import org.fpoly.nhom2.entiry.Role;
import org.fpoly.nhom2.entiry.Token;
import org.fpoly.nhom2.entiry.User;
import org.fpoly.nhom2.repository.TokenRepository;
import org.fpoly.nhom2.repository.UserRepository;
import org.fpoly.nhom2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * RegisterController
 */
@Controller
public class RegisterController {

    @Autowired
    EmailService mailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    
    @GetMapping(value = "/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(value="/registerProcess")
    public String registerProcess(Model model, @ModelAttribute("user") User user,HttpServletRequest request) {
        if(userRepository.findFirstByUsernameOrEmail(user.getUsername(), user.getEmail()) != null){
            model.addAttribute("error", "Username hoặc email đã tồn tại");
            return "register";
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        Role role = new Role();
        role.setRoleId(3);
        user.setRole(role);
        userRepository.save(user);
        try {
			mailSender.sendEmailXacNhan(user.getEmail(),request.getServerName());
		}catch(MailException e) {
			System.out.println("Lỗi gửi email: " + e.getMessage());
		}
        return "redirect:/comfirm?email="+user.getEmail();
    }
    
    @GetMapping(value="/comfirm")
    public String showConfirmEmail() {
        return "email-confirmation";
    }

    @GetMapping(value="/resend-confirmation-code")
    public String resendConfirm(@RequestParam("email") String email,HttpServletRequest request) {
        try {
			mailSender.sendEmailXacNhan(email,request.getServerName());
		}catch(MailException e) {
			System.out.println("Lỗi gửi email: " + e.getMessage());
		}
        return "redirect:/confirm?email="+email;
    }
    
    @GetMapping(value="/activate")
    public String activateAccount(Model model,@RequestParam("token") String tokenString) {
        Token token = tokenRepository.findByToken(tokenString);
        if(token == null){
            model.addAttribute("error", "Mã token không hợp lệ!");
            return "activate-by-token";
        }else{
            User user = userRepository.findFirstByUsernameOrEmail("", token.getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            tokenRepository.delete(token);
            return "activate-by-token";
        }
    }
    
}