package com.my.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.service.IService;

@WebServlet("*.my")
public class MyFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PREFIX = "WEB-INF/views/";
	private static final String SUFFIX = ".jsp";
	Map<String, Object> commandMap = new HashMap<>();
       
    public MyFrontController() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		System.out.println("init");
		loadProperties("/com.my.properties/command");
		System.out.println("ok");
	}
	
	private void loadProperties(String prop) {
		ResourceBundle rb = ResourceBundle.getBundle(prop);
		Enumeration<String> keys = rb.getKeys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			String className = rb.getString(key);
			System.out.println(key + "=" + className);
			try {
				Class<?> commandClass = Class.forName(className);
				Object obj = commandClass.newInstance();
				commandMap.put(key, (IService)obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getCommand(HttpServletRequest request) throws ServletException, IOException {
		String uri = request.getRequestURI(); // /list.my
		String contextPath = request.getContextPath(); // path="/" 라서 없음
		int indexDot = uri.indexOf("."); // 5
//		String command = uri.substring(1, indexDot);
		String command = uri.substring(1); // list.my
		return command;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandler(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandler(request, response);
	}
	
	private void doHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = getCommand(request);
		IService iService = (IService) commandMap.get(command);
		
		String path = "";
		try {
			path = iService.excute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String strRedirect = "redirect:";
		if (path.contains(strRedirect)) {
			String location = path.substring(strRedirect.length());
			response.sendRedirect(location);
		} else {
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(PREFIX + path + SUFFIX);
			dispatcher.forward(request, response);
		}
	}

} //MyFrontController
