package com.gaorui.Service;




import java.util.List;

import javax.annotation.Resource;

import com.gaorui.ISpring.ISpring;
import com.gaorui.dao.UserDao;
import com.gaorui.entity.Carpooling;
import com.gaorui.entity.Carpooling_user;
import com.gaorui.entity.Integral;
import com.gaorui.entity.User;
import com.gaorui.redisdao.redisdao;

public class SpringManager implements ISpring{
	@Resource
	private UserDao userDao;
	@Resource
	private redisdao redisdao1;
	@Override
	public User LoginCl(Long user_tel,String password) {
		
		return userDao.LoginCl(user_tel, password);
	}
	@Override
	public Integral ShowPersonalInfo(Long user_id) {
		
		return userDao.ShowPersonalInfo(user_id);
	}
	
	//需要计算出用户周围的所有的距离范围
	@Override
	public  List<Carpooling> ShowS_carPooling(double user_longitude,
			double user_latitude, int distanceAround) {
		double Big1_user_longitude = 0;
		double Big1_user_latitude = 0;
		double Big2_user_longitude = 0;
		double Big2_user_latitude = 0;
		return userDao.ShowS_carPooling(Big1_user_longitude, Big1_user_latitude, Big2_user_longitude, Big2_user_latitude);
	}
	@Override
	public Carpooling SelectCarpoolingByCarpooling_id(Long Carpooling_id) {
		
		return userDao.SelectCarpoolingByCarpooling_id(Carpooling_id);
	}
	@Override
	public User SelectUserByUser_id(Long user_id) {
		
		return userDao.SelectUserByUser_id(user_id);
	}
	@Override
	public int JoinCarpooling(Long Carpooling_id, Long user_id) {
	
		return userDao.JoinCarpooling(Carpooling_id, user_id) ;
	}
	@Override
	public Carpooling_user SelectCarpooling_idByUser_id(Long user_id) {
		// TODO Auto-generated method stub
		return userDao.SelectCarpooling_idByUser_id(user_id);
	}
	@Override
	public List<Carpooling_user> SelectUser_idByCarpooling_id(
			Long Carpooling_id, Long user_id) {
		// TODO Auto-generated method stub
		return userDao.SelectUser_idByCarpooling_id(Carpooling_id, user_id);
	}
	@Override
	public int updatecu_statusByUser_idCarpooling_id(Long user_id,
			Long Carpooling_id) {
		
		return userDao.updatecu_statusByUser_idCarpooling_id(user_id, Carpooling_id);
	}
	@Override
	public int InitiateCarpooling(
			String Carpooling_origin,
			String Carpooling_destination, String Carpooling_Date,
			int Carpooling_distance, String Carpooling_way,
			int Carpooling_count, double Carpooling_longitude,
			double Carpooling_latitude,
			Long Main_user_id) {
		
		return userDao.InitiateCarpooling(Carpooling_origin, Carpooling_destination, Carpooling_Date, Carpooling_distance, Carpooling_way, Carpooling_count, Carpooling_longitude, Carpooling_latitude,Main_user_id);
	}
	@Override
	public int updateCarpooling_status(Long Carpooling_id) {
		
		return userDao.updateCarpooling_status(Carpooling_id);
	}
	@Override
	public Carpooling_user JudgeCarpoolingByCarpooling_User_id(Long Carpooling_id,
			Long user_id) {
	
		return userDao.JudgeCarpoolingByCarpooling_User_id(Carpooling_id, user_id);
	}
	@Override
	public List<Carpooling> SelectCarpoolingByUser_id(Long user_id) {
	
		return userDao.SelectCarpoolingByUser_id(user_id);
	}
	@Override
	public List<Carpooling_user> SelectCarpooling_UserByUser_id(Long user_id) {
	
		return userDao.SelectCarpooling_UserByUser_id(user_id);
	}
	@Override
	public int InsertS_user(Long user_id, Long user_tel, String user_password,
			double user_longitude, double user_latitude, String user_name,
			String user_content) {
		
		return userDao.InsertS_user(user_id, user_tel, user_password, user_longitude, user_latitude, user_name, user_content);
	}
	@Override
	public int selectCarCountByCarpooling_id(Long Carpooling_id) {
		
		return userDao.selectCarCountByCarpooling_id(Carpooling_id);
	}
	@Override
	public int InsertS_userInfo(Long user_id) {
		
		return userDao.InsertS_userInfo(user_id);
	}
	@Override
	public User SearchSameUserName(Long user_id) {
		
		return userDao.SearchSameUserName(user_id);
	}



	



}
