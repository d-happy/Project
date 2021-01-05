package com.my.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IService {
	
	public String excute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
} //InterService
