package com.gaorui.ISpring;

import java.util.List;




import com.gaorui.entity.Carpooling_user;
import com.gaorui.entity.Carpooling;
import com.gaorui.entity.Integral;
import com.gaorui.entity.User;

public interface ISpring {
	public User LoginCl(Long user_tel, String password);

	public Integral ShowPersonalInfo(Long user_id);

	public List<Carpooling> ShowS_carPooling(double user_longitude,
			double user_latitude, int distanceAround);

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
			double Carpooling_latitude, Long Main_user_id);

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
}
