package com.my.domain;

public class PagingDto {
	private int page;
	private int perPage = 10;
	private int totalPage;
	private int startPage;
	private int endPage;
	private int startRow;
	private int endRow;
	private String searchType;
	private String keyword;
	private final int PAGE_BLOCK = 10;

	public PagingDto() { }

	public PagingDto(int page, int perPage, String searchType, String keyword) {
		super();
		this.page = page;
		this.perPage = perPage;
		this.searchType = searchType;
		this.keyword = keyword;
	}

	public void setPaging(int page, int perPage, int totalArticle, 
						 String searchType, String keyword) {
		this.page = page;
		this.perPage = perPage;
		this.searchType = searchType;
		this.keyword = keyword;
		
		/* 무조건 올려서 int로 */
		this.totalPage = (int)Math.ceil((double)totalArticle / perPage);
		// (double)(totalArticle/perPage) -> (double)(int/int) 라서 (double)(0) -> 0.0
		// (double)int/int -> double/int -> double 값 나옴
		this.endRow = page * perPage;
		this.startRow = this.endRow - (perPage-1);
		
		/* 중간에 소수점은 int니까 사라짐 -> 1, 11, ... */
		this.startPage = (page - 1) / PAGE_BLOCK * PAGE_BLOCK + 1;
		/* PAGE_BLOCK = 10 단위로 끝남  */
		this.endPage = this.startPage + (PAGE_BLOCK - 1);
		if (this.endPage > this.totalPage) {
			this.endPage = this.totalPage;
		}
	}

	public int getPage() {
		return page;
	}
	public int getPerPage() {
		return perPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public int getStartRow() {
		return startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public String getSearchType() {
		return searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public int getPAGE_BLOCK() {
		return PAGE_BLOCK;
	}
	
	@Override
	public String toString() {
		return "PagingDto [page=" + page + ", perPage=" + perPage + ", totalPage=" + totalPage + ", startPage="
				+ startPage + ", endPage=" + endPage + ", startRow=" + startRow + ", endRow=" + endRow + ", searchType="
				+ searchType + ", keyword=" + keyword + ", PAGE_BLOCK=" + PAGE_BLOCK + "]";
	}
	
} //PagingDto
