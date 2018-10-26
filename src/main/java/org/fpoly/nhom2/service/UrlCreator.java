package org.fpoly.nhom2.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class UrlCreator {
	public String createUrl(String string) {
		string = string.toLowerCase();
		string = string.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
		string = string.replaceAll("[èéẹẻẽêềếệểễ]", "e");
		string = string.replaceAll("[ìíịỉĩ]", "i");
		string = string.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
		string = string.replaceAll("[ùúụủũưừứựửữ]", "u");
		string = string.replaceAll("[ỳýỵỷỹ]", "y");
		string = string.replace("đ", "d");
		string = string.replaceAll("[^a-zA-Z0-9]","-");
		string = string + "-" + randomString();
		return string;
	}
		
	private String randomString() {
		int length = 7;
		boolean useLetters = true;
		boolean useNumber = false;
		String randomStr = RandomStringUtils.random(length, useLetters, useNumber);
		return randomStr;
	}
}
