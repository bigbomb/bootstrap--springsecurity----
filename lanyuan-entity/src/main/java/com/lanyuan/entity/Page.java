package com.lanyuan.entity;

/**
 * 字典
 * @author lanyuan
 * Email：mmm333zzz520@163.com
 * date：2014-4-8
 */
@SuppressWarnings("serial")
public class Page implements java.io.Serializable{
	private Integer start;
	private Integer end;
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
}
