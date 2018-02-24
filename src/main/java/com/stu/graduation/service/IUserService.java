package com.stu.graduation.service;

import org.springframework.web.multipart.MultipartFile;

import com.stu.graduation.common.ResponseData;

public interface IUserService {
	 ResponseData login(String phone,String pwd);

	ResponseData register(String phone, String pwd, String nickname, String alipayAccount);

	ResponseData editUser(String phone, String nickname, String selfIntroduction, MultipartFile file);
	
}
