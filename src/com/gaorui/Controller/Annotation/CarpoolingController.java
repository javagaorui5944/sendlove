package com.gaorui.Controller.Annotation;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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









import com.alibaba.fastjson.JSONObject;
import com.gaorui.ISpring.ISpring;
import com.gaorui.entity.Carpooling_user;
import com.gaorui.entity.Carpooling;
import com.gaorui.entity.User;
import com.gaorui.util.CommonUtil;
import com.gaorui.util.WebSocketPushUtil;


@Controller
public class CarpoolingController {
	@Resource(name = "springManager")
	private ISpring springManager;// 注入springManager

	// 代码是灵魂,注释是心声。
	//

	/**
	 * 用户查看周围的拼车帖子,用户自己确定周围多少km
	 * 
	 * @param request
	 * @param
	 * @param
	 * @param distanceAround
	 * @return code,message,object
	 * @author gr
	 */
	@RequestMapping(value = "/ShowS_carPooling", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject ShowS_carPooling(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "c_id", required = false) Long c_id,
			@RequestParam(value = "Flush_Rus", required = false) String Flush_Rus) {

		response.setHeader("Access-Control-Allow-Origin", "*");

		System.out.println("Flush:" + Flush_Rus + "--c_id:"
				+ c_id);

		int pageCount = 0;

		
		//第一次加载操作
		if (c_id == null && Flush_Rus == null) {
			System.out.println("Carpooling_Date == null && Flush_Rus == null");
			
			List<Carpooling> Carpooling = springManager.ShowS_carPooling(pageCount,
					pageCount + 10);
			
			if (Carpooling.toString().equals("[]")) {

				return CommonUtil.constructResponse(0, "暂时没有用户发起拼车", null);

			} else {

				return CommonUtil.constructResponse(1, "第一次加载操作", Carpooling);

			}
		} 
		
		//刷新操作
		else if (c_id != null && Flush_Rus != null) {
			
		/*	int c_id =
			springManager.GetCarpooling_idByCarpooling_Date(Carpooling_Date);*/
			 
			List<Carpooling> Carpooling = springManager.FlushCarpooling(c_id);
			
			
			System.out.println("Carpooling_Date != null && Flush_Rus.equals('flush')");
			
			return CommonUtil.constructResponse(1, "刷新操作", Carpooling);
			
		} 
		
		//下拉分页操作
		else if(c_id != null && Flush_Rus == null){
			
			/*int  c_id =
			  springManager.GetCarpooling_idByCarpooling_Date(Carpooling_Date);*/
			 
			Long Last_c_id = springManager.SelectLastCarpooling_id();
			
			if(c_id == Last_c_id ){
				
				return CommonUtil.constructResponse(0, "下拉已无更多历史数据", null);
				
			}
			List<Carpooling> Carpooling = springManager.GetHistoryCarpooling(c_id);
			
			System.out.println("else");
			
			return CommonUtil.constructResponse(1, "下拉分页操作", Carpooling);
			
		}
		else{
			
			return CommonUtil.constructResponse(0, "服务端逻辑出错",null);
			
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
		
		Carpooling_user cu = springManager.JudgeCarpoolingByCarpooling_User_id(Carpooling_id, user.getUser_id());
		Carpooling 	carpooling2 = springManager.JudgeCarpoolingByCarpooling_id(Carpooling_id, user.getUser_id());
		
		System.out.println(cu+"--"+carpooling2+"----");
		int isresult = 0; 
		if(cu != null | carpooling2!=null){
			isresult =1 ;
		}
		
		jsonObject.put("isresult", isresult);

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

		// 判断用户是否已经加入到队伍当中了

		Carpooling_user cu = springManager.JudgeCarpoolingByCarpooling_User_id(
				Carpooling_id, user_id);

		if (cu != null) {

			return CommonUtil.constructResponse(0, "用户已经在拼车队伍里面了", null);

		}

		// 加入动作前判断队伍人数,暂定轿车,不能大于4人
		int people_count = springManager
				.selectCarCountByCarpooling_id(Carpooling_id);

		// people_count1为其余加入队伍的人数加上拼车发起人本来已有的人数
		int people_count1 = people_count
				+ springManager.SelectCarpoolingByCarpooling_id(Carpooling_id)
						.getCarpooling_count();

		if (people_count1 == 4) {

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

		// Long Main_user_id = carpooling.getUser_id();
		System.out.println("user_ids" + user_ids);

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
	@RequestMapping(value = "/InitiateCarpooling", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject InitiateCarpooling(HttpServletRequest request,
			HttpServletResponse response, String Carpooling_origin,
			String Carpooling_destination,  String Carpooling_way,
			int Carpooling_count) {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		HttpSession htppSession = request.getSession();
		String Carpooling_origin1 = null;
		String Carpooling_destination1 =null;
		String Carpooling_way1 =null;
		try {
			Carpooling_origin1 = new String(Carpooling_origin.getBytes("iso-8859-1"), "utf-8");
			Carpooling_destination1 = new String(Carpooling_destination.getBytes("iso-8859-1"), "utf-8");
			Carpooling_way1 = new String(Carpooling_way.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
		
			e.printStackTrace();
		}
		
		Date now = new Date();
		
		SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	
		
		String Carpooling_Date  =	myFmt2.format(now);
		
		User user = (User) htppSession.getAttribute("user");
		if(user == null ){
			
			return CommonUtil.constructResponse(0, "false", null);
			
		}
		Long Main_user_id = user.getUser_id();
		double Carpooling_longitude = user.getUser_longitude();
		double Carpooling_latitude = user.getUser_latitude();
		
		int num = springManager.InitiateCarpooling(Carpooling_origin1,
				Carpooling_destination1, Carpooling_Date,
				Carpooling_way1, Carpooling_count, Carpooling_longitude,
				Carpooling_latitude,
				 Main_user_id);

		if (num > 0) {
			
			Long Carpooling_id = springManager.Return_LAST_INSERT_ID();
			
			JSONObject jsonObject = new JSONObject();
			
			Carpooling carpooling = new Carpooling();
			
			carpooling.setCarpooling_id(Carpooling_id);
			carpooling.setCarpooling_origin(Carpooling_origin);
			carpooling.setCarpooling_destination(Carpooling_destination);	
			carpooling.setCarpooling_Date(Carpooling_Date);
		//	carpooling.setCarpooling_distance(Carpooling_distance);
			carpooling.setCarpooling_way(Carpooling_way);
			carpooling.setCarpooling_count(Carpooling_count);
			carpooling.setCarpooling_longitude(Carpooling_longitude);
			carpooling.setCarpooling_latitude(Carpooling_latitude);
			
			jsonObject.put("pushMapCarpooling", carpooling);
			
			//推送给所有连接的客户端(WebSocket)
			WebSocketPushUtil wspu = new WebSocketPushUtil();
			wspu.hello(null, null,jsonObject);
			
			return CommonUtil.constructResponse(1, "ok", null);
		}

		return CommonUtil.constructResponse(0, "false", null);

	}

	/**
	 * 判断用户是否发起过拼车,或者是在某个拼车队伍中(设计完成)
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

		HttpSession htppSession = request.getSession();

		User user = (User) htppSession.getAttribute("user");

		if (user == null) {
			return CommonUtil.constructResponse(0, "false", null);
		}

		Long user_id = user.getUser_id();

		List<Carpooling> carpoolings = springManager
				.SelectCarpoolingByUser_id(user_id);
		System.out.println("user_id" + user_id + "carpoolings"
				+ carpoolings.toString());
		// Object object = "[]";

		JSONObject jsonObject = springManager.JudgeCarpoolingMan(carpoolings,
				user_id);

		return jsonObject;
	}

	/**
	 * 用户加入队伍后选择退出该拼车队伍
	 * 
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

		int num = springManager.updatecu_statusByUser_idCarpooling_id(user_id,
				Carpooling_id);

		if (num > 0) {
			return CommonUtil.constructResponse(1, "成功退出拼车队伍", null);
		}

		return CommonUtil.constructResponse(0, "false", null);

	}

	/**
	 * 用户查看自己的拼车或加入拼车队伍的信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/My_Carpooling", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject My_Carpooling(HttpServletRequest request,
			HttpServletResponse response) {

		response.setHeader("Access-Control-Allow-Origin", "*");

		// JSONArray jsonArray = new JSONArray();

		HttpSession htppSession = request.getSession();

		User user = (User) htppSession.getAttribute("user");

		if (user == null) {

			return CommonUtil.constructResponse(0, "false", null);

		}

		Long user_id = user.getUser_id();

		List<Carpooling> carpoolings = springManager
				.SelectCarpoolingByUser_id(user_id);
		System.out.println("user_id" + user_id + "carpoolings"
				+ carpoolings.toString());
		// Object object = "[]";

		JSONObject jsonObject = springManager.My_Carpooling(carpoolings,
				user_id);

		return jsonObject;

	}

}
