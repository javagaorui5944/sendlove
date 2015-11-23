package com.gaorui.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.gaorui.entity.Carpooling;
import com.gaorui.entity.Carpooling_user;
import com.gaorui.entity.Integral;
import com.gaorui.entity.User;

@Component
public interface UserDao {
	final String user = "user_id,user_tel,user_password,user_longitude,user_latitude,user_name,user_content";

	final String integral = "integral_id,user_id,integral_level,integral_carpoolingcount,integral_initiatecount,integral_goodpraisecount";

	final String carpooling = "Carpooling_id,user_id,Carpooling_origin,Carpooling_destination,Carpooling_Date,Carpooling_distance,Carpooling_way,Carpooling_count,Carpooling_nearme,Carpooling_content,Carpooling_status,Carpooling_longitude,Carpooling_latitude,End_Carpooling_longitude,End_Carpooling_latitude";

	final String carpooling_user = "Carpooling_id,user_id";

	@Select("SELECT "
			+ user
			+ " FROM s_user  WHERE user_tel=#{user_tel} AND user_password='${password}'")
	public User LoginCl(@Param("user_tel") Long user_tel,
			@Param("password") String password);

	@Select("SELECT " + integral + " FROM s_integral WHERE user_id=#{0}")
	public Integral ShowPersonalInfo(Long user_id);

	// @Select("SELECT "+carpooling+" FROM s_carpooling WHERE Carpooling_longitude<${user_longitude} AND Carpooling_latitude<${user_latitude}")
	@Select("SELECT " + carpooling
			+ " FROM s_carpooling ORDER BY Carpooling_Date DESC LIMIT #{0},#{1}")
	public List<Carpooling> ShowS_carPooling(int c_id,int showPageCount);

	@Select("SELECT " + carpooling
			+ " FROM s_carpooling WHERE  Carpooling_id=#{0}")
	public Carpooling SelectCarpoolingByCarpooling_id(Long Carpooling_id);

	@Select("SELECT " + user + " FROM s_user WHERE user_id=#{0}")
	public User SelectUserByUser_id(Long user_id);

	@Insert("INSERT INTO carpooling_user(Carpooling_id,user_id)VALUES(${Carpooling_id},${user_id})")
	public int JoinCarpooling(@Param("Carpooling_id") Long Carpooling_id,
			@Param("user_id") Long user_id);

	@Select("SELECT " + carpooling_user
			+ " FROM carpooling_user WHERE user_id=#{0} AND cu_status=1")
	public Carpooling_user SelectCarpooling_idByUser_id(Long user_id);

	@Select("SELECT "
			+ carpooling_user
			+ " FROM carpooling_user WHERE Carpooling_id=#{0} AND user_id !=#{1} AND cu_status=1")
	public List<Carpooling_user> SelectUser_idByCarpooling_id(
			Long Carpooling_id, Long user_id);

	@Update("UPDATE carpooling_user SET cu_status =-1 WHERE user_id=#{0} AND Carpooling_id=#{1}")
	public int updatecu_statusByUser_idCarpooling_id(Long user_id,
			Long Carpooling_id);

	@Insert("insert into s_carpooling(Carpooling_origin,Carpooling_destination,Carpooling_Date,Carpooling_distance,Carpooling_way,Carpooling_count,Carpooling_longitude,Carpooling_latitude,End_Carpooling_longitude,End_Carpooling_latitude,user_id) values('${Carpooling_origin}','${Carpooling_destination}','${Carpooling_Date}','${Carpooling_distance}','${Carpooling_way}',${Carpooling_count},${Carpooling_longitude},${Carpooling_latitude}, ${End_Carpooling_longitude},${End_Carpooling_latitude},${user_id})")
	public int InitiateCarpooling(
			@Param("Carpooling_origin") String Carpooling_origin,
			@Param("Carpooling_destination") String Carpooling_destination,
			@Param("Carpooling_Date") String Carpooling_Date,
			@Param("Carpooling_distance") int Carpooling_distance,
			@Param("Carpooling_way") String Carpooling_way,
			@Param("Carpooling_count") int Carpooling_count,
			@Param("Carpooling_longitude") double Carpooling_longitude,
			@Param("Carpooling_latitude") double Carpooling_latitude,
			@Param("End_Carpooling_longitude") double End_Carpooling_longitude,
			@Param("End_Carpooling_latitude") double End_Carpooling_latitude,
			@Param("user_id") Long user_id);

	@Update("UPDATE s_carpooling SET Carpooling_status =100 WHERE Carpooling_id=#{0}")
	public int updateCarpooling_status(Long Carpooling_id);

	@Select("SELECT " + carpooling + " FROM s_carpooling where user_id=#{0}")
	public Carpooling SelectCarpooling_id_idByMainUser_id(Long user_id);

	// 根据Carpooling_id计算出在队伍中的人数
	@Select("SELECT COUNT(*)+1 FROM carpooling_user WHERE Carpooling_id=#{0}")
	public int selectCarCountByCarpooling_id(Long Carpooling_id);

	// 根据Carpooling_id和user_id判断用户是否在拼车队伍
	@Select("SELECT " + carpooling_user
			+ " FROM carpooling_user WHERE Carpooling_id=#{0} AND user_id=#{1}")
	public Carpooling_user JudgeCarpoolingByCarpooling_User_id(
			Long Carpooling_id, Long user_id);

	// 根据user_id查找是否在s_carpooling表中
	@Select("SELECT " + carpooling
			+ " FROM s_carpooling WHERE user_id=#{0} ")
	public List<Carpooling> SelectCarpoolingByUser_id(Long user_id);

	// SELECT * FROM carpooling_user WHERE user_id=13996767513
	// 根据user_id查找是否在carpooling_user表中
	@Select("SELECT " + carpooling_user
			+ " FROM carpooling_user WHERE user_id=#{0} and cu_status=1")
	public List<Carpooling_user> SelectCarpooling_UserByUser_id(Long user_id);

	// 用户注册
	@Insert("INSERT INTO s_user(user_id,user_tel,user_password,user_longitude,user_latitude,user_name,user_content) VALUES(${user_id},${user_tel},'${user_password}','${user_longitude}',${user_latitude},'${user_name}','${user_content}')")
	public int InsertS_user(@Param("user_id") Long user_id,
			@Param("user_tel") Long user_tel,
			@Param("user_password") String user_password,
			@Param("user_longitude") double user_longitude,
			@Param("user_latitude") double user_latitude,
			@Param("user_name") String user_name,
			@Param("user_content") String user_content);
	
	//用户注册后个人信息插入
	@Insert("INSERT INTO s_integral(user_id) VALUES(#{0})")
	public int InsertS_userInfo(Long user_id);
	
	//查找用户是否在拼车队伍中
//	@Select("SELECT * FROM carpooling_user WHERE  Carpooling_id=68 AND user_id=11")
	
	//查看用户名是否重复
	@Select("SELECT "+user+" FROM s_user WHERE user_id=#{0}")
	public User SearchSameUserName(Long user_id);
	
	//地图
	
	@Select("SELECT " + carpooling
			+ " FROM s_carpooling ")
	public List<Carpooling> ShowMapS_carPooling();
	
	//查出拼车id 根据时间
	@Select("SELECT  Carpooling_id FROM s_carpooling WHERE Carpooling_Date='${Carpooling_Date}'")
	public int GetCarpooling_idByCarpooling_Date(@Param("Carpooling_Date")  String Carpooling_Date);
	
	
	//下拉分页返回历史数据
	@Select("SELECT "+carpooling+" FROM s_carpooling WHERE Carpooling_id<#{0} ORDER BY Carpooling_Date   DESC LIMIT 0,10")
	public List<Carpooling> GetHistoryCarpooling(Long c_id);
	
	//刷新操作
	@Select("SELECT "+carpooling+" FROM s_carpooling  WHERE Carpooling_id>#{0} ORDER BY Carpooling_Date   DESC ")
	public List<Carpooling> FlushCarpooling(Long c_id);
	
	//查找s_carpooling最后一条数据
	@Select("SELECT Carpooling_id FROM s_carpooling ORDER BY Carpooling_Date  LIMIT 0,1")
	public Long SelectLastCarpooling_id();
	
	//返回最后一条操作自增的id
	@Select("SELECT LAST_INSERT_ID()")
	public Long Return_LAST_INSERT_ID();
}
