package com.my.util;

import com.my.domain.PagingDto;

public class QueryStringMaker {

	public static String queryMake(PagingDto pagingDto, Boolean isBno) {
		
		String query = "";
		if (isBno) {
			query += "&";
		}
		query += "page=" + pagingDto.getPage();
		query += "&perPage=" + pagingDto.getPerPage();
		query += "&searchType=" + pagingDto.getSearchType();
		query += "&keyword=" + pagingDto.getKeyword();
		
		return query;
	}
	
} //QueryStringMaker
