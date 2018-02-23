package com.eversec.graduation.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eversec.graduation.common.ResponseData;
import com.eversec.graduation.service.IUserService;
import com.eversec.graduation.utils.DesUtils;

@Controller
@RequestMapping("user/")
public class UserController {
	@Autowired
	private IUserService userService;

	/**
	 * 登陆
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public ResponseData login(HttpServletRequest req) {
		String phone = req.getParameter("phone");
		String pwd = req.getParameter("pwd");
		return userService.login(DesUtils.decrypt(phone), DesUtils.decrypt(pwd));

	}

	/**
	 * 注册
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("register")
	@ResponseBody
	public ResponseData register(HttpServletRequest req) {
		String phone = req.getParameter("phone");
		String pwd = req.getParameter("pwd");
		String nickname = req.getParameter("nickname");
		String alipayAccount = req.getParameter("alipayAccount");
		return userService.register(DesUtils.decrypt(phone), DesUtils.decrypt(pwd), nickname, alipayAccount);

	}

	/**
	 * 修改用户信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value="update",method = RequestMethod.POST)
	@ResponseBody
	public ResponseData update(@RequestParam("file") MultipartFile file,HttpServletRequest req) {
		String phone = req.getParameter("phone");
		String nickname = req.getParameter("nickname");
		String selfIntroduction = req.getParameter("selfIntroduction");
		
	     return userService.editUser(phone,  nickname, selfIntroduction,file);
		
		
	}
}
