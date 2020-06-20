package com.yc.bbs.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.yc.bbs.dao.TopicDao;

import redis.clients.jedis.Jedis;

@WebServlet("/DetailServlet")
public class DetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TopicDao tDao = new TopicDao();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 解决乱码问题
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 通过 topicid 帖子id 查询帖子信息和回帖信息
		String topicid = request.getParameter("topicid");
		List<Map<String,Object>> list = tDao.queryByDetail(topicid);
		
		/**
		 * 	将访问次数存入 redis 数据库, Reids 其实很大工作是设计 Key 值   
		 *  topicid  ==> key topic:4   :  1,2,3
		 *  				 topic:5   :   	数字
		 *  
		 *  zset ==> key topic-zset 
		 *  		value  topicid
		 *  		score  浏览量
		 */
		Jedis jd = new Jedis();
		// incr 返回 增长的值 原值+1  incr 实现自增
		long cnt = jd.incr("topic:" + topicid);
		
		// 将 topic 保存到 zset 中 生成排行榜
		jd.zadd("topic-zset", cnt, topicid);
		
		jd.close();
		
		/**
		 * 	返回的json 数据的格式
		 * [ {原贴},{跟贴},{跟贴},{跟贴}...  ]
		 */
		 // 将浏览量添加到 原贴 对象
		 list.get(0).put("cnt", cnt);
		
		// 使用 Gson 将数据 以 json 格式方式返回给页面
		Gson gson = new Gson();
		// 将集合转成 json 格式的字符串
		String json = gson.toJson(list);
		response.getWriter().print(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
