package com.my.domain;

public class PagingDto {
	
	private int page;
	private int totalPage;
	private int startPage;
	private int endPage;
	private int startRow;
	private int endRow;
	private int perPage = 10;
	private final int PAGE_BLOCK = 10;
	
	public PagingDto() { }
	
	// setPagingData로 PagingDto 관련 데이터 set -> 개별 데이터 setter 필요 없음
	public void setPagingData(int perPage, int totalCount, int page) {
		this.perPage = perPage;
		this.totalPage = (int)Math.ceil((double)totalCount / perPage);
		this.page = page;
		
		this.endRow = page * perPage;
		this.startRow = this.endRow - (perPage - 1);
		
		this.startPage = (page - 1) / PAGE_BLOCK * PAGE_BLOCK + 1;
		this.endPage = this.startPage + (PAGE_BLOCK - 1);
		if (this.endPage > this.totalPage) {
			this.endPage = this.totalPage;
		}
	}
	
	public int getPage() {
		return page;
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
	public int getPerPage() {
		return perPage;
	}
	public int getPAGE_BLOCK() {
		return PAGE_BLOCK;
	}

	@Override
	public String toString() {
		return "PagingDto [page=" + page + ", totalPage=" + totalPage + ", startPage=" + startPage + 
				", endPage=" + endPage + ", startRow=" + startRow + ", endRow=" + endRow + 
				", perPage=" + perPage + ", PAGE_BLOCK=" + PAGE_BLOCK + "]";
	}
	
} //PagingDto
