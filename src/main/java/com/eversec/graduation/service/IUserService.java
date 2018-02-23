package com.eversec.graduation.service;

import org.springframework.web.multipart.MultipartFile;

import com.eversec.graduation.common.ResponseData;
import com.eversec.graduation.entity.User;

public interface IUserService {
	 ResponseData login(String phone,String pwd);

	ResponseData register(String phone, String pwd, String nickname, String alipayAccount);

	ResponseData editUser(String phone, String nickname, String selfIntroduction, MultipartFile file);
	
}
