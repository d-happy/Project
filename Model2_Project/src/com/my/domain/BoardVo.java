package com.my.domain;

import java.sql.Timestamp;

public class BoardVo {
	private int b_no;
    private String b_title;
    private String b_content;
    private String m_id;
    private Timestamp b_date;
    private int b_readcount;
    private String b_file_path;
    
	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_content() {
		return b_content;
	}
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	public Timestamp getB_date() {
		return b_date;
	}
	public void setB_date(Timestamp b_date) {
		this.b_date = b_date;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public int getB_readcount() {
		return b_readcount;
	}
	public void setB_readcount(int b_readcount) {
		this.b_readcount = b_readcount;
	}
	public String getB_file_path() {
		return b_file_path;
	}
	public void setB_file_path(String b_file_path) {
		this.b_file_path = b_file_path;
	}
	
	@Override
	public String toString() {
		return "BoardVo [b_no=" + b_no + ", b_title=" + b_title + ", b_content=" + b_content + ", m_id=" + m_id
				+ ", b_date=" + b_date + ", b_readcount=" + b_readcount + ", b_file_path=" + b_file_path + "]";
	}

} //BoardVo
