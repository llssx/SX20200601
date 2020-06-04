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
	
	public List<Map<String,Object>> queryByDetail(String topicid){
		// 预编译sql语句
		String sql = "SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	tbl_topic a\n" +
				"JOIN tbl_user b ON a.uid = b.uid\n" +
				"WHERE\n" +
				"	topicid = ?\n" +
				"UNION ALL\n" +
				"	SELECT\n" +
				"		*\n" +
				"	FROM\n" +
				"		tbl_reply a\n" +
				"	JOIN tbl_user b ON a.uid = b.uid\n" +
				"	WHERE\n" +
				"		topicid = ?" ;
		return new DBHelper().query(sql, topicid, topicid);
	}
	
}
