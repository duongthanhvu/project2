package org.fpoly.nhom2.service;

import java.util.UUID;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.Token;
import org.fpoly.nhom2.entiry.User;
import org.fpoly.nhom2.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;;

@Service("mailer")
public class EmailService {

	@Autowired
	JavaMailSender mailSender;
	@Autowired
	TokenRepository tokenRepository;
	
	@Async
	public void sendEmailXacNhan(String email, String hostname) throws MailException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Xác nhận tài khoản");
		String tokenString = UUID.randomUUID().toString();
		message.setText("Nhấn vào link này để xác nhận http://"+hostname+"/activate?token="+tokenString);
		try {
			mailSender.send(message);
			System.out.println("Gửi email đến " + email +" thành công!");
		}catch(MailException e){
			throw e;
        }
		Token token = new Token(email,tokenString);
		tokenRepository.save(token);
	}

	@Async
	public void sendEmailCapQuyen(User user, Company company) throws MailException{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("vudtpk00714@fpt.edu.vn");
		message.setSubject("Yêu cầu cấp quyền quản trị trang doanh nghiệp");
		message.setText("Thành viên " + user.getUsername() + " (email: "+user.getEmail()+") đã gửi yêu cầu cấp quyền quản trị cho trang của công ty " +company.getName());
		try {
			mailSender.send(message);
			System.out.println("Gửi email đến admin thành công!");
		}catch(MailException e){
			throw e;
        }
	}

	@Async
	public void iWantToSubmitNewCompany(User user, Company company) throws MailException{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("vudtpk00714@fpt.edu.vn");
		message.setSubject("Yêu cầu thêm doanh nghiệp");
		String thongTinCongTy = "Tên công ty: "+company.getName()+"\nĐịa chỉ: "+company.getAddress().getStreet()+"\nMo tả: "+company.getDescription();
		message.setText("Thành viên " + user.getUsername() + " (email: "+user.getEmail()+") đã gửi yêu cầu tạo trang doanh nghiệp mới.\nChi tiết:\n"+thongTinCongTy);
		try {
			mailSender.send(message);
			System.out.println("Gửi email đến admin thành công!");
		}catch(MailException e){
			throw e;
        }
	}

	@Async
	public void sendEmailResetPassword(String email, String hostname) throws MailException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Yêu cầu đặt lại mật khẩu");
		String tokenString = UUID.randomUUID().toString();
		message.setText("Nhấn vào link này để thực hiện việc đặt lại mật khẩu http://"+hostname+"/reset-password?token="+tokenString+"\nNếu không phải bạn muốn đặt lại mật khẩu vui lòng bỏ qua email này.");
		try {
			mailSender.send(message);
			System.out.println("Gửi email đến " + email +" thành công!");
		}catch(MailException e){
			throw e;
        }
		Token token = new Token(email,tokenString);
		tokenRepository.save(token);
	}
}