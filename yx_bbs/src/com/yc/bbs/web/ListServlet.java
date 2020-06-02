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

/**
 * Servlet 作用 
 * 1 响应浏览器的请求
 * 2 调用业务对象的方法( dao, biz 类的方法 ),获取数据或执行修改
 * @author Administrator
 *
 */
@WebServlet("/ListServlet")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private TopicDao tDao = new TopicDao();

	/**
	 * request 请求对象
	 * response 响应对象
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 解决乱码问题
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		// 获取从浏览器发送过来的请求参数 
		String boardid = request.getParameter("boardid");
		List<Map<String,Object>> list = tDao.queryByBoard(boardid);
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
