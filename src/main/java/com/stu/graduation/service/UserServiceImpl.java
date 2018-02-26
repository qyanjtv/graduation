package com.stu.graduation.service;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.stu.graduation.common.Constant;
import com.stu.graduation.common.ResponseData;
import com.stu.graduation.dao.UserDao;
import com.stu.graduation.entity.User;
import com.stu.graduation.utils.FileUtil;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserDao userDao;

	@Override
	public ResponseData login(String phone, String pwd) {
		ResponseData response = new ResponseData();
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(pwd)) {
			response.setCode(Constant.LOGIN_ERROR_CODE);
			response.setMsg(Constant.LOGIN_PARAM_MSG);
			return response;
		}
		// 检查用户是否注册
		User LoginUser = userDao.getUserByPhone(phone);
		if (LoginUser == null) {
			response.setCode(Constant.NOT_FOUND_ODE);
			response.setMsg(Constant.USER_NOT_FOUND_MSG);
			return response;
		}

		User user = userDao.login(phone, pwd);
		if (user == null) {
			response.setCode(Constant.LOGIN_ERROR_CODE);
			response.setMsg(Constant.LOGIN_ERROR_MSG);
		} else {
			List<Object> data = Lists.newArrayList();
			response.setCode(Constant.SUCCESS_CODE);
			response.setMsg(Constant.LOGIN_SUCCESS_MSG);
			data.add(user);
			response.setData(data);
		}
		return response;
	}

	@Override
	public ResponseData register(String phone, String pwd, String nickname, String alipayAccount) {

		ResponseData response = new ResponseData();
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(pwd) || StringUtils.isBlank(nickname)
				|| StringUtils.isBlank(alipayAccount)) {
			response.setCode(Constant.LOGIN_ERROR_CODE);
			response.setMsg(Constant.LOGIN_PARAM_MSG);
			return response;
		}
		User loginUser = userDao.getUserByPhone(phone);
		// 用户已经注册不用重复注册
		if (loginUser != null) {
			response.setCode(Constant.USER_ALLERADY_CODE);
			response.setMsg(Constant.USER_ALLERADY_MSG);
			return response;
		}
		User user = new User();
		user.setNickName(nickname);
		user.setTelphone(phone);
		user.setPwd(pwd);
		user.setAlipayId(alipayAccount);
		userDao.saveAndFlush(user);
		return response;
	}

	@Override
	public ResponseData editUser(String phone, String nickname, String selfIntroduction, MultipartFile file) {

		ResponseData response = new ResponseData();
		try {
			if (StringUtils.isBlank(phone) || StringUtils.isBlank(nickname) || StringUtils.isBlank(selfIntroduction)
					|| file == null) {
				response.setCode(Constant.LOGIN_ERROR_CODE);
				response.setMsg(Constant.LOGIN_PARAM_MSG);
				return response;
			}
			User user = userDao.getUserByPhone(phone);
			if (user == null) {
				response.setCode(Constant.NOT_FOUND_ODE);
				response.setMsg(Constant.USER_NOT_FOUND_MSG);
				return response;
			}
			// 调用文件处理类FileUtil，处理文件，将文件写入指定位置
			String contentType = file.getContentType(); // 图片文件类型
			 String fileName = phone+"."+FileUtil.getExtensionName(file.getOriginalFilename());  //图片名字
			// 文件存放路径
			String filePath = Constant.FILE_PATH + "/img";
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			user.setNickName(nickname);
			user.setSignature(selfIntroduction);
			user.setAvatarId(fileName);
			FileUtil.uploadFile(file.getBytes(), filePath, fileName);
			userDao.saveAndFlush(user);
			User users = userDao.getUserByPhone(phone);
			List<Object>data=Lists.newArrayList();
			data.add(users);
			response.setData(data);

		} catch (Exception e) {
			response.setCode(Constant.SYSTEM_ERROR_CODE);
			response.setMsg(Constant.ERROR_MSG);
			e.printStackTrace();

		}
		return response;
	}

}
