package com.yc.bbs.dao;

import java.util.List;
import java.util.Map;

import com.yc.bbs.util.DBHelper;

/**
 * DAO ==> Data Access Object 数据访问类
 * 转用于操作 tbl_topic 表的sql类
 */
public class TopicDao {

	public List<Map<String,Object>> queryByBoard(String boardid){
		// 预编译sql语句
		String sql = "select * from tbl_topic where boardid = ? " ;
		return new DBHelper().query(sql, boardid);
	}
	
}
