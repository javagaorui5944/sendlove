package com.gaorui.ISpring;

import java.util.List;











import com.alibaba.fastjson.JSONObject;
import com.gaorui.entity.Carpooling_user;
import com.gaorui.entity.Carpooling;
import com.gaorui.entity.Integral;
import com.gaorui.entity.User;

public interface ISpring {
	public User LoginCl(Long user_tel, String password);

	public Integral ShowPersonalInfo(Long user_id);

	public List<Carpooling> ShowS_carPooling(int c_id,int showPageCount);

	public Carpooling SelectCarpoolingByCarpooling_id(Long Carpooling_id);

	public User SelectUserByUser_id(Long user_id);

	public int JoinCarpooling(Long Carpooling_id, Long user_id);

	public Carpooling_user SelectCarpooling_idByUser_id(Long user_id);

	public List<Carpooling_user> SelectUser_idByCarpooling_id(
			Long Carpooling_id, Long user_id);

	public int updatecu_statusByUser_idCarpooling_id(Long user_id,
			Long Carpooling_id);

	public int InitiateCarpooling(String Carpooling_origin,
			String Carpooling_destination, String Carpooling_Date,
			int Carpooling_distance, String Carpooling_way,
			int Carpooling_count, double Carpooling_longitude,
			double Carpooling_latitude,double End_Carpooling_longitude,
			double End_Carpooling_latitude, Long Main_user_id);

	public int updateCarpooling_status(Long Carpooling_id);

	public Carpooling_user JudgeCarpoolingByCarpooling_User_id(
			Long Carpooling_id, Long user_id);

	public List<Carpooling> SelectCarpoolingByUser_id(Long user_id);

	public List<Carpooling_user> SelectCarpooling_UserByUser_id(Long user_id);

	public int InsertS_user(Long user_id, Long user_tel, String user_password,
			double user_longitude, double user_latitude, String user_name,
			String user_content);
	
	public int selectCarCountByCarpooling_id(Long Carpooling_id);
	
	public int InsertS_userInfo(Long user_id);
	
	public User SearchSameUserName(Long user_id);
	
	//判断用户是否发起过拼车,或者是在某个拼车队伍接口
	public JSONObject JudgeCarpoolingMan(List<Carpooling> carpoolings,Long user_id);
	
	//用户查看自己的拼车或加入拼车队伍的信息接口
	
	public JSONObject My_Carpooling(List<Carpooling> carpoolings,Long user_id);
	
	//地图
	public List<Carpooling> ShowMapS_carPooling(double user_latitude,double user_longitude,int radius);
	
	public int GetCarpooling_idByCarpooling_Date( String Carpooling_Date);
	
	//下拉查找历史拼车贴
	public List<Carpooling> GetHistoryCarpooling(Long c_id);

	//刷新帖子列表操作
	public List<Carpooling> FlushCarpooling(Long c_id);
	
	//查找帖子最后一条数据
	public Long SelectLastCarpooling_id();
	
	public Long Return_LAST_INSERT_ID();
}
