package com.my.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.service.InterService;

@WebServlet("*.my")
public class MyFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PREFIX = "/WEB-INF/views/";
	private static final String SUFFIX = ".jsp";
	private Map<String, InterService> commandMap = new HashMap<>();
       
    public MyFrontController() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		super.init();
		System.out.println("init 실행");
    	loadProperties("/com.my.properties/command");
    	System.out.println("ok");
	}
	
	// command.properties 바탕으로 각각 다른 InterService 객체 생성 
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
    			commandMap.put(key, (InterService)obj); 
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
	    
	// uri에서 어떤 Interservice를 불러올지 약속된 command를 찾음
    private String getCommand(HttpServletRequest request) {
    	String uri = request.getRequestURI();
    	System.out.println("uri :" + uri);
    	String contextPath = request.getContextPath();
    	int startIndex = contextPath.length() + 1;
    	String command = uri.substring(startIndex);
    	return command;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet 실행");
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost 실행");
		doHandle(request, response);
	}
	
	// doGet, doPost 같이 처리 가능
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doHandle 실행");
		
		request.setCharacterEncoding("utf-8");
		String command = getCommand(request);
		System.out.println("command :" + command);
		
		String path = "";
		InterService service = commandMap.get(command);
		System.out.println("service :" + service);
		
		try {
			path = service.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		boolean isRedirect = path.startsWith("redirect:");
		if (isRedirect) {
			response.sendRedirect(path.substring("redirect:".length()));
		} else {
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(PREFIX + path + SUFFIX);
			dispatcher.forward(request, response);
		}
	}

} //MyFrontController
