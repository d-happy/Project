package com.my.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WriteFormService implements IService {

	@Override
	public String excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "writeForm";
	}

} //WriteFormService
