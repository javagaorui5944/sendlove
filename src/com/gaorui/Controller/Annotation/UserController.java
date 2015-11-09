package com.gaorui.Controller.Annotation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaorui.ISpring.ISpring;
import com.gaorui.entity.Carpooling_user;
import com.gaorui.entity.Carpooling;
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
	@RequestMapping(value = "/LoginCl", method = RequestMethod.POST)
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
			@RequestParam(value="user_longitude", required = false)double user_longitude, 
			@RequestParam(value="user_latitude", required = false)double user_latitude,
			@RequestParam(value="user_name", required = false)String user_name, 
			@RequestParam(value="user_content", required = false)String user_content) {
		response.setHeader("Access-Control-Allow-Origin", "*");

		System.out.println("user_tel" + user_tel);
		
		User user1 = springManager.SearchSameUserName(user_tel);
		
		if(user1 != null){
			
			return CommonUtil.constructResponse(0, "该账号已经被注册", null);
		}
		
		int num = springManager.InsertS_user(user_tel, user_tel, user_password,
				user_longitude, user_latitude, user_name, user_content);

		HttpSession httpSession = request.getSession();

		

		if (num > 0) {
			User user = new User();

			user.setUser_content(user_content);
			user.setUser_id(user_tel);
			user.setUser_latitude(user_latitude);
			user.setUser_longitude(user_longitude);
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

	/**
	 * 用户查看周围的拼车帖子,用户自己确定周围多少km
	 * 
	 * @param request
	 * @param user_longitude
	 * @param user_latitude
	 * @param distanceAround
	 * @return code,message,object
	 */
	@RequestMapping(value = "/ShowS_carPooling", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject ShowS_carPooling(HttpServletRequest request,
			HttpServletResponse response) {

		response.setHeader("Access-Control-Allow-Origin", "*");

		/*
		 * @RequestParam(value = "user_longitude", required = false) double
		 * user_longitude,
		 * 
		 * @RequestParam(value = "user_latitude", required = false) double
		 * user_latitude,
		 * 
		 * @RequestParam(value = "distanceAround", required = false) int
		 * distanceAround
		 */
	/*	JSONObject jsonObject = new JSONObject();

		JSONArray jsonArray = new JSONArray();
*/
		/*
		 * HttpSession httpSession = request.getSession();
		 * 
		 * User user = (User) httpSession.getAttribute("user");
		 * 
		 * Long user_id = user.getUser_id();
		 */
		double user_longitude = 100;

		double user_latitude = 100;

		int distanceAround = 100;

		List<Carpooling> Carpooling = springManager.ShowS_carPooling(
				user_longitude, user_latitude, distanceAround);

		if (Carpooling == null) {

			return CommonUtil.constructResponse(0, "false", null);

		} else {
			/*
			 * for (int i = 0; i < Carpooling.size(); i++) { Long Carpooling_id
			 * = Carpooling.get(i).getCarpooling_id(); Carpooling_user cu =
			 * springManager.JudgeCarpoolingByCarpooling_User_id(Carpooling_id,
			 * user_id); if(cu != null){
			 * 
			 * jsonObject.put("ok",Carpooling.get(i)); } else{
			 * jsonObject.put("Carpooling"+i,Carpooling.get(i)); }
			 * 
			 * 
			 * } jsonArray.add(jsonObject);
			 */
			return CommonUtil.constructResponse(1, "ok", Carpooling);

		}
	}

	/**
	 * 用户点击某一个拼车贴进去查看详细信息()
	 * 
	 * @param request
	 * @param Carpooling_id
	 * @return code,message,object
	 */
	@RequestMapping(value = "/ShowPersonalS_carPooling", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject ShowPersonalS_carPooling(HttpServletRequest request,
			HttpServletResponse response, Long Carpooling_id) {

		System.out.println("Carpooling_id:" + Carpooling_id);

		response.setHeader("Access-Control-Allow-Origin", "*");

		JSONObject jsonObject = new JSONObject();

		HttpSession httpSession = request.getSession();

		User user = (User) httpSession.getAttribute("user");

		if (user == null) {
			System.out.println("user" + user);
			return CommonUtil.constructResponse(0, "false", null);

		}

		double Me_user_longitude = user.getUser_longitude();

		double Me_user_latitude = user.getUser_latitude();

		Carpooling carpooling = springManager
				.SelectCarpoolingByCarpooling_id(Carpooling_id);

		if (carpooling == null) {
			System.out.println("carpooling:" + carpooling);
			return CommonUtil.constructResponse(0, "false", null);

		}

		Long user_id = carpooling.getUser_id();

		User main_user = springManager.SelectUserByUser_id(user_id);

		if (main_user == null) {

			return CommonUtil.constructResponse(0, "nook", null);

		}

		double main_user_longitude = main_user.getUser_longitude();

		double main_user_latitude = main_user.getUser_latitude();

		double distance = CommonUtil.GetDistance(Me_user_latitude,
				Me_user_longitude, main_user_latitude, main_user_longitude);

		jsonObject.put("distance", distance);

		jsonObject.put("carpooling", carpooling);

		return CommonUtil.constructResponse(1, "ok", jsonObject);
	}

	/**
	 * 用户拼车加入拼车队伍功能,加入动作前判断队伍不能大于4人
	 * 
	 * @param request
	 * @param Carpooling_id
	 * @return code,message,object
	 */
	@RequestMapping(value = "/JoinCarpooling", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject JoinCarpooling(HttpServletRequest request,
			HttpServletResponse response, Long Carpooling_id) {
		response.setHeader("Access-Control-Allow-Origin", "*");

		JSONObject jsonObject = new JSONObject();

		HttpSession httpSession = request.getSession();

		User user = (User) httpSession.getAttribute("user");

		if (user == null) {

			return CommonUtil.constructResponse(0, "false", null);

		}
		Long user_id = user.getUser_id();
		
		//判断用户是否已经加入到队伍当中了
		
		Carpooling_user cu = springManager.JudgeCarpoolingByCarpooling_User_id(Carpooling_id, user_id);
		
		if(cu != null){
			
			return CommonUtil.constructResponse(0, "用户已经在拼车队伍里面了", null);
			
		}
		
	
		//加入动作前判断队伍人数,暂定轿车,不能大于4人
		int people_count = springManager.selectCarCountByCarpooling_id(Carpooling_id);
		
		//people_count1为其余加入队伍的人数加上拼车发起人本来已有的人数
		int people_count1 = people_count + springManager.SelectCarpoolingByCarpooling_id(Carpooling_id).getCarpooling_count();
		
		if(people_count1 == 4){
			
			return CommonUtil.constructResponse(0, "拼车队伍人数已经有4人,不能加入", null);
			
		}
		
		
		int num = springManager.JoinCarpooling(Carpooling_id, user_id);

		if (num > 0) {
			
			jsonObject.put("Carpooling_id", Carpooling_id);
			
			return CommonUtil.constructResponse(1, "ok", jsonObject);

		} else {

			return CommonUtil.constructResponse(0, "false", null);

		}

	}

	/**
	 * 用户加入队伍后查看队伍所有的信息
	 * 
	 * @param request
	 * @param Carpooling_id
	 * @return code,message,object
	 */
	@RequestMapping(value = "/UserJoinedShowCarlingInfo", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject UserJoinedShowCarlingInfo(HttpServletRequest request,
			HttpServletResponse response, Long Carpooling_id) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject jsonObject = new JSONObject();

		HttpSession htppSession = request.getSession();

		User user = (User) htppSession.getAttribute("user");

		Long user_id = user.getUser_id();

		/*
		 * 
		 * Carpooling_user carpooling_user =
		 * springManager.SelectCarpooling_idByUser_id(user_id);
		 * 
		 * Long carpooling_id = carpooling_user.getCarpooling_id();
		 */

		List<Carpooling_user> Carpooling_users = springManager
				.SelectUser_idByCarpooling_id(Carpooling_id, user_id);
		List<Long> user_ids = new ArrayList<Long>();
		for (int i = 0; i < Carpooling_users.size(); i++) {
			user_ids.add(Carpooling_users.get(i).getUser_id());
		}
		Carpooling carpooling = springManager
				.SelectCarpoolingByCarpooling_id(Carpooling_id);

	//	Long Main_user_id = carpooling.getUser_id();
		System.out.println("user_ids"+user_ids);

		jsonObject.put("Carpooling_users", user_ids);
		
		jsonObject.put("carpooling", carpooling);
		
		
		
		return CommonUtil.constructResponse(1, "ok", jsonObject);

	}

	/**
	 * 拼车发起人踢人,将加入拼车队伍用户的状态改为-1
	 * 
	 * @param request
	 * @param user_id
	 * @return code,message,object
	 */
	@RequestMapping(value = "/DeleteJoinedUser", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject DeleteJoinedUser(HttpServletRequest request,
			HttpServletResponse response, Long user_id, Long Carpooling_id) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		// JSONObject jsonObject = new JSONObject();
		/*
		 * HttpSession htppSession = request.getSession();
		 * 
		 * User user = (User) htppSession.getAttribute("user");
		 * 
		 * Long Main_user_id = user.getUser_id();
		 */

		// Long carpooling_id =
		// springManager.SelectCarpooling_id_idByMainUser_id();

		/*
		 * Carpooling_user carpooling_user =
		 * springManager.SelectCarpooling_idByUser_id(Main_user_id);
		 * 
		 * Long carpooling_id = carpooling_user.getCarpooling_id();
		 */

		int num = springManager.updatecu_statusByUser_idCarpooling_id(user_id,
				Carpooling_id);

		if (num > 0) {
			return CommonUtil.constructResponse(1, "已经将此人踢出队伍", null);
		}

		return CommonUtil.constructResponse(0, "false", null);

	}

	/**
	 * 拼车人完成拼车动作功能,将拼车贴状态改为100
	 * 
	 * @param request
	 * @param Carpooling_id
	 * @return code,message,object
	 */
	@RequestMapping(value = "/MainUserConfirm", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject MainUserConfirm(HttpServletRequest request,
			HttpServletResponse response, Long Carpooling_id) {

		response.setHeader("Access-Control-Allow-Origin", "*");
		int num = springManager.updateCarpooling_status(Carpooling_id);

		if (num > 0) {
			return CommonUtil.constructResponse(1, "ok", null);
		}

		return CommonUtil.constructResponse(0, "false", null);

	}

	/**
	 * 用户发起拼车
	 * 
	 * @param request
	 * @param Carpooling_origin
	 * @param Carpooling_destination
	 * @param Carpooling_Date
	 * @param Carpooling_distance
	 * @param Carpooling_way
	 * @param Carpooling_count
	 * @param Carpooling_longitude
	 * @param Carpooling_latitude
	 * @return code,message,object
	 */
	@RequestMapping(value = "/InitiateCarpooling", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject InitiateCarpooling(HttpServletRequest request,
			HttpServletResponse response, String Carpooling_origin,
			String Carpooling_destination, String Carpooling_Date,
			int Carpooling_distance, String Carpooling_way,
			int Carpooling_count, double Carpooling_longitude,
			double Carpooling_latitude) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		HttpSession htppSession = request.getSession();

		User user = (User) htppSession.getAttribute("user");

		Long Main_user_id = user.getUser_id();

		int num = springManager.InitiateCarpooling(Carpooling_origin,
				Carpooling_destination, Carpooling_Date, Carpooling_distance,
				Carpooling_way, Carpooling_count, Carpooling_longitude,
				Carpooling_latitude, Main_user_id);

		if (num > 0) {
			return CommonUtil.constructResponse(1, "ok", null);
		}

		return CommonUtil.constructResponse(0, "false", null);

	}

	/**
	 * 判断用户是否发起过拼车,或者是在某个拼车队伍中(设计ing)
	 * 
	 * @param request
	 * @param response
	 * @return code,message,object
	 * @author Gr
	 */

	@RequestMapping(value = "/JudgeCarpoolingMan", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject JudgeCarpoolingMan(HttpServletRequest request,
			HttpServletResponse response) {
		
		response.setHeader("Access-Control-Allow-Origin", "*");

		JSONObject jsonObject1 = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		JSONArray jsonArray2 = new JSONArray();

		HttpSession htppSession = request.getSession();

		User user = (User) htppSession.getAttribute("user");

		Long user_id = user.getUser_id();

		List<Carpooling> carpoolings = springManager
				.SelectCarpoolingByUser_id(user_id);
		System.out.println("user_id" + user_id + "carpoolings"
				+ carpoolings.toString());
		// Object object = "[]";

		// 如果为空则证明登录用户没有发起过正在拼车的队伍
		if (carpoolings.toString().equals("[]")) {
			System.out.println("[]");
			List<Carpooling_user> carpooling_user = springManager
					.SelectCarpooling_UserByUser_id(user_id);
			// 该用户既没有发起拼车,也没加入拼车队伍
			if (carpooling_user.toString().equals("[]")) {
				//jsonArray.add(jsonObject);

				jsonObject1.put("s_carpooling", jsonArray);

				jsonObject1.put("carpooling_user", jsonArray2);
				return CommonUtil.constructResponse(0, "该用户既没有发起拼车,也没加入拼车队伍",
						jsonObject1);
			}
			// 该用户没有发起拼车,但加入了拼车队伍
			else {
				for (int i = 0; i < carpooling_user.size(); i++) {
					JSONObject jsonObject2 = new JSONObject();
					
					Long Carpooling_id = carpooling_user.get(i)
							.getCarpooling_id();

					jsonObject2
							.put("Carpooling_id", Carpooling_id.toString());

					System.out.println("Carpooling_id:" + Carpooling_id);
					jsonArray.add(jsonObject2);
				}
			

				jsonObject1.put("s_carpooling", jsonArray2);

				jsonObject1.put("carpooling_user", jsonArray);

				//jsonArray.add(jsonObject);
				return CommonUtil.constructResponse(1, "该用户没有发起拼车,但加入了拼车队伍",
						jsonObject1);
			}

		}

		else {

			List<Carpooling_user> carpooling_user = springManager
					.SelectCarpooling_UserByUser_id(user_id);
			//该用户发起了拼车,也加入了拼车队伍
			if (!carpooling_user.toString().equals("[]")) {

				for (int i = 0; i < carpooling_user.size(); i++) {
					JSONObject jsonObject4 = new JSONObject();
					Long Carpooling_id = carpooling_user.get(i)
							.getCarpooling_id();

					jsonObject4.put("Carpooling_id",
							Carpooling_id.toString());

					System.out.println("carpooling_user_Carpooling_id:"
							+ Carpooling_id);
					jsonArray2.add(jsonObject4);
				}

				
			//	JSONArray JSONArray4 = new JSONArray();
				for (int i = 0; i < carpoolings.size(); i++) {
					
					JSONObject jsonObject3 = new JSONObject();
					
					Long Mian_Carpooling_id = carpoolings.get(i)
							.getCarpooling_id();

					jsonObject3.put("Mian_Carpooling_id",
							Mian_Carpooling_id.toString());

					System.out.println("carpoolings_Carpooling_id:"
							+ Mian_Carpooling_id);
					
					jsonArray.add(jsonObject3);
				}

			

				jsonObject1.put("s_carpooling", jsonArray);

				jsonObject1.put("carpooling_user", jsonArray2);

				return CommonUtil.constructResponse(1, "该用户发起了拼车,也加入了拼车队伍",
						jsonObject1);

			}

			// 该用户发起了拼车,但没加入拼车队伍
			else {

				for (int i = 0; i < carpoolings.size(); i++) {
					
					JSONObject jsonObject4 = new JSONObject();

					Long Carpooling_id = carpoolings.get(i).getCarpooling_id();

					jsonObject4
							.put("Carpooling_id", Carpooling_id.toString());

					System.out.println("Carpooling_id:" + Carpooling_id);

					jsonArray.add(jsonObject4);
				}

				jsonObject1.put("s_carpooling", jsonArray);

				jsonObject1.put("carpooling_user", jsonArray2);

				return CommonUtil.constructResponse(1, "该用户发起了拼车,但没加入拼车队伍",
						jsonObject1);
			}
		}

	}
	
	/**
	 * 用户加入队伍后选择退出该拼车队伍
	 * @param request
	 * @param response
	 * @param Carpooling_id
	 * @return code,message,object
	 */
	@RequestMapping(value = "/UserexitCarpooling", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject UserexitCarpooling(HttpServletRequest request,
			HttpServletResponse response, Long Carpooling_id) {

		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HttpSession htppSession = request.getSession();

		User user = (User) htppSession.getAttribute("user");
		if (user == null) {
			return CommonUtil.constructResponse(0, "false", null);
		}
		
		Long user_id = user.getUser_id();
			
		int num = springManager.updatecu_statusByUser_idCarpooling_id(user_id,Carpooling_id);

		if (num > 0) {
			return CommonUtil.constructResponse(1, "成功退出拼车队伍", null);
		}

		return CommonUtil.constructResponse(0, "false", null);

	}
	
	
	/**
	 * 用户查看自己的拼车或加入拼车队伍的信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/My_Carpooling", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject My_Carpooling(HttpServletRequest request,
			HttpServletResponse response) {
		
		response.setHeader("Access-Control-Allow-Origin", "*");

	//	JSONObject jsonObject1 = new JSONObject();

		JSONArray jsonArray = new JSONArray();

	//	JSONArray jsonArray2 = new JSONArray();

		HttpSession htppSession = request.getSession();

		User user = (User) htppSession.getAttribute("user");

		Long user_id = user.getUser_id();

		List<Carpooling> carpoolings = springManager
				.SelectCarpoolingByUser_id(user_id);
		System.out.println("user_id" + user_id + "carpoolings"
				+ carpoolings.toString());
		// Object object = "[]";

		// 如果为空则证明登录用户没有发起过正在拼车的队伍
		if (carpoolings.toString().equals("[]")) {
			System.out.println("[]");
			List<Carpooling_user> carpooling_user = springManager
					.SelectCarpooling_UserByUser_id(user_id);
			// 该用户既没有发起拼车,也没加入拼车队伍
			if (carpooling_user.toString().equals("[]")) {
				//jsonArray.add(jsonObject);

				//jsonObject1.put("s_carpooling", jsonArray);

				//jsonObject1.put("carpooling_user", jsonArray2);
				return CommonUtil.constructResponse(0, "该用户既没有发起拼车,也没加入拼车队伍",
						jsonArray);
			}
			// 该用户没有发起拼车,但加入了拼车队伍
			else {
				for (int i = 0; i < carpooling_user.size(); i++) {
					
					JSONObject jsonObject2 = new JSONObject();
					
					Long Carpooling_id = carpooling_user.get(i)
							.getCarpooling_id();
					
					Carpooling carpooling = springManager.SelectCarpoolingByCarpooling_id(Carpooling_id);
					
					jsonObject2
							.put("Carpooling_id", Carpooling_id.toString());
					
					jsonObject2
					.put("Carpooling_origin", carpooling.getCarpooling_origin());
					

					jsonObject2
					.put("Carpooling_destination", carpooling.getCarpooling_destination());
					
					jsonObject2
					.put("Carpooling_Date", carpooling.getCarpooling_Date());
					
					jsonObject2
					.put("type", 2);


					System.out.println("Carpooling_id:" + Carpooling_id);
					jsonArray.add(jsonObject2);
				}
			

				//jsonObject1.put("s_carpooling", jsonArray2);

				//jsonObject1.put("carpooling_user", jsonArray);

				//jsonArray.add(jsonObject);
				return CommonUtil.constructResponse(1, "该用户没有发起拼车,但加入了拼车队伍",
						jsonArray);
			}

		}

		else {

			List<Carpooling_user> carpooling_user = springManager
					.SelectCarpooling_UserByUser_id(user_id);
			//该用户发起了拼车,也加入了拼车队伍
			if (!carpooling_user.toString().equals("[]")) {

				for (int i = 0; i < carpooling_user.size(); i++) {
					JSONObject jsonObject4 = new JSONObject();
					Long Carpooling_id = carpooling_user.get(i)
							.getCarpooling_id();

					Carpooling carpooling = springManager.SelectCarpoolingByCarpooling_id(Carpooling_id);
					
					jsonObject4.put("Carpooling_id",
							Carpooling_id.toString());
					
					jsonObject4
					.put("Carpooling_origin", carpooling.getCarpooling_origin());
					

					jsonObject4
					.put("Carpooling_destination", carpooling.getCarpooling_destination());
					
					jsonObject4
					.put("Carpooling_Date", carpooling.getCarpooling_Date());
					
					jsonObject4
					.put("type", 2);

					System.out.println("carpooling_user_Carpooling_id:"
							+ Carpooling_id);
					
					jsonArray.add(jsonObject4);
				}

				
			//	JSONArray JSONArray4 = new JSONArray();
				for (int i = 0; i < carpoolings.size(); i++) {
					
					JSONObject jsonObject3 = new JSONObject();
					
					Long Mian_Carpooling_id = carpoolings.get(i)
							.getCarpooling_id();
					
					//Carpooling carpooling = springManager.SelectCarpoolingByCarpooling_id(Mian_Carpooling_id);

					jsonObject3.put("Carpooling_id",
							Mian_Carpooling_id.toString());

					jsonObject3
					.put("Carpooling_origin", carpoolings.get(i).getCarpooling_origin());
					

					jsonObject3
					.put("Carpooling_destination", carpoolings.get(i).getCarpooling_destination());
					
					jsonObject3
					.put("Carpooling_Date", carpoolings.get(i).getCarpooling_Date());
					
					jsonObject3
					.put("type", 1);

					
					System.out.println("carpoolings_Carpooling_id:"
							+ Mian_Carpooling_id);
					
					jsonArray.add(jsonObject3);
				}

			
				System.out.println("jsonArray"+jsonArray.toString());
				//jsonObject1.put("s_carpooling", jsonArray);

				//jsonObject1.put("carpooling_user", jsonArray2);

				return CommonUtil.constructResponse(1, "该用户发起了拼车,也加入了拼车队伍",
						jsonArray);

			}

			// 该用户发起了拼车,但没加入拼车队伍
			else {

				for (int i = 0; i < carpoolings.size(); i++) {
					
					JSONObject jsonObject4 = new JSONObject();

					Long Carpooling_id = carpoolings.get(i).getCarpooling_id();

					jsonObject4
							.put("Carpooling_id", Carpooling_id.toString());
					
					jsonObject4
					.put("Carpooling_origin", carpoolings.get(i).getCarpooling_origin());
					

					jsonObject4
					.put("Carpooling_destination", carpoolings.get(i).getCarpooling_destination());
					
					jsonObject4
					.put("Carpooling_Date", carpoolings.get(i).getCarpooling_Date());
					
					jsonObject4
					.put("type", 1);

					System.out.println("Carpooling_id:" + Carpooling_id);

					jsonArray.add(jsonObject4);
				}

				//jsonObject1.put("s_carpooling", jsonArray);

				//jsonObject1.put("carpooling_user", jsonArray2);
				
				return CommonUtil.constructResponse(1, "该用户发起了拼车,但没加入拼车队伍",
						jsonArray);
			}
		}

		
	}

}
