package com.gaorui.Controller.Annotation;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gaorui.ISpring.ISpring;
import com.gaorui.entity.Carpooling;
import com.gaorui.entity.User;
import com.gaorui.util.CommonUtil;

@Controller
public class ShowMapController {
	@Resource(name = "springManager")
	private ISpring springManager;// 注入springManager

	// 代码是灵魂,注释是心声。
	//

	/**
	 * 显示首页地图正在拼车的队伍
	 * 
	 * @param request
	 * @return code,message,object
	 */
	@RequestMapping(value = "/ShowMapCarpooling", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject ShowMapCarpooling(HttpServletRequest request,
			HttpServletResponse response, int radius) {

		response.setHeader("Access-Control-Allow-Origin", "*");

		HttpSession httpSession = request.getSession();

		User user = (User) httpSession.getAttribute("user");

		if (user == null) {

			return CommonUtil.constructResponse(0, "false", null);

		} else {

			double user_latitude = user.getUser_latitude();
			
			double user_longitude = user.getUser_longitude();
			
			List<Carpooling> carpoolings = springManager.ShowMapS_carPooling(user_latitude, user_longitude,radius);

			return CommonUtil.constructResponse(1, "ok", carpoolings);

		}
	}

}
