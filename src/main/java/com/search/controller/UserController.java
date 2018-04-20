package com.search.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.search.bean.Admin;
import com.search.service.AdminService;

@Controller
public class UserController {
	// 自动注入业务层的userService类
	@Autowired
	AdminService adminService;

	// login业务的访问位置为login
	@RequestMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			// 把用户名和密码封装为 UsernamePasswordToken 对象
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			// rememberme
			token.setRememberMe(true);
			try {
				System.out.println("1. " + token.hashCode());
				// 执行登录.
				currentUser.login(token);
				System.out.println("登入成功");
			}
			// ... catch more exceptions here (maybe custom ones specific to your
			// application?
			// 所有认证时异常的父类.
			catch (AuthenticationException ae) {
				// unexpected condition? error?
				System.out.println("登录失败: " + ae.getMessage());
			}
		}
		return "redirect:list.jsp";
	}

}
