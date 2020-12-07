package com.my.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface InterService {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
} //InterService
