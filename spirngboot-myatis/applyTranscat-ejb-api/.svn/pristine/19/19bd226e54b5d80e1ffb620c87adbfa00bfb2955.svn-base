package com.ule.uhj.util.repayPlans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Period {


	private Calendar start ;
	private Calendar end ;
	private BigDecimal currBasePrin;
	private BigDecimal currAmount;
	private BigDecimal currPrinc;
	private BigDecimal currInter;
	private Integer index;  //从0开始
	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		Calendar c = Calendar.getInstance();
		c.setTime(start.getTime());		
		this.start = c;
	}
	public Calendar getEnd() {
		return end;
	}
	public void setEnd(Calendar end) {
		Calendar c = Calendar.getInstance();
		c.setTime(end.getTime());
		this.end = c;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	@Override
	public String toString() {
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyyMMdd");
		return String
				.format("start:%s;end:%s;currBasePrin:%s;currAmount:%s;currPrinc:%s;currInter%s;index:%s",
						sdfymd.format(start.getTime()),
						sdfymd.format(end.getTime()),
						currBasePrin, currAmount, currPrinc, currInter, index);
	}
	public BigDecimal getCurrBasePrin() {
		return currBasePrin;
	}
	public void setCurrBasePrin(BigDecimal currBasePrin) {
		this.currBasePrin = currBasePrin;
	}
	public BigDecimal getCurrPrinc() {
		return currPrinc;
	}
	public void setCurrPrinc(BigDecimal currPrinc) {
		this.currPrinc = currPrinc;
	}
	public BigDecimal getCurrInter() {
		return currInter;
	}
	public void setCurrInter(BigDecimal currInter) {
		this.currInter = currInter;
	}
	public BigDecimal getCurrAmount() {
		return currAmount;
	}
	public void setCurrAmount(BigDecimal currAmount) {
		this.currAmount = currAmount;
	}
	
}

