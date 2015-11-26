package com.gaorui.Controller.Annotation;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.alibaba.fastjson.JSONObject;
import com.gaorui.ISpring.ISpring;

import com.gaorui.entity.Integral;
import com.gaorui.entity.User;
import com.gaorui.util.CommonUtil;

@Controller
public class UserController {
	@Resource(name = "springManager")
	private ISpring springManager;// 注入springManager

	
	//代码是灵魂,注释是心声。
	//
	/**
	 * 用户登录验证
	 * 
	 * @param request
	 * @param user_tel
	 * @param password
	 * @return code,message,object
	 */
	@RequestMapping(value = "/LoginCl", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject LoginCl(HttpServletRequest request, Long user_tel,
			String password, HttpServletResponse response) {
		// double user_longitude, double user_latitude
		response.setHeader("Access-Control-Allow-Origin", "*");

		HttpSession httpSession = request.getSession();

		User user = springManager.LoginCl(user_tel, password);

		System.out.println("user_tel:" + user_tel + "password" + password);

		if (user != null) {
			
			httpSession.setAttribute("user", user);

			return CommonUtil.constructResponse(1, "ok", user);

		} else {

			return CommonUtil.constructResponse(0, "false", null);
		}

	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param user_tel
	 * @param password
	 * @return code,message,object
	 */
	@RequestMapping(value = "/RegisteredCl", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject RegisteredCl(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "user_tel", required = false) Long user_tel,
			@RequestParam(value="user_password", required = false)String user_password, 
			@RequestParam(value="user_name", required = false)String user_name
		) {
		response.setHeader("Access-Control-Allow-Origin", "*");

		System.out.println("user_tel" + user_tel);
		
		User user1 = springManager.SearchSameUserName(user_tel);
		
		if(user1 != null){
			
			return CommonUtil.constructResponse(0, "该账号已经被注册", null);
		}
		
		int num = springManager.InsertS_user(user_tel, user_tel, user_password,
				 user_tel.toString());

		HttpSession httpSession = request.getSession();

		

		if (num > 0) {
			User user = new User();

		//	user.setUser_content(user_content);
			user.setUser_id(user_tel);
		//	user.setUser_latitude(user_latitude);
		//	user.setUser_longitude(user_longitude);
			user.setUser_name(user_name);
			user.setUser_password(user_password);
			httpSession.setAttribute("user", user);
			
			//个人详细信息,例如点赞,拼车次数暂时作废.
			/*int result = springManager.InsertS_userInfo(user.getUser_id());
			
			if(result<1){
				return CommonUtil.constructResponse(0, "服务端插入个人信息报错", null);
			}*/
			return CommonUtil.constructResponse(1, "注册成功", null);
		}
		return CommonUtil.constructResponse(0, "注册失败", null);
	}

	/**
	 * 用户查看个人信息(作废,等老汪)
	 * 
	 * @param request
	 * @return code,message,object
	 */
	@RequestMapping(value = "/ShowPersonalInfo", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject ShowPersonalInfo(HttpServletRequest request,
			HttpServletResponse response) {

		response.setHeader("Access-Control-Allow-Origin", "*");

		HttpSession httpSession = request.getSession();

		JSONObject jsonObject = new JSONObject();

		User user = (User) httpSession.getAttribute("user");

		if (user == null) {

			return CommonUtil.constructResponse(0, "false", null);

		} else {

			Long user_id = user.getUser_id();

			Integral integral = springManager.ShowPersonalInfo(user_id);

			jsonObject.put("integral", integral);

			jsonObject.put("user_name", user.getUser_name());

			return CommonUtil.constructResponse(1, "ok", jsonObject);

		}
	}


	


}
