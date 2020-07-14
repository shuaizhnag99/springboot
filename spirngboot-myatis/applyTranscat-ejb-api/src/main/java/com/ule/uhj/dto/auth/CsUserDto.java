package com.ule.uhj.dto.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CsUserDto implements Serializable{
	private static final long serialVersionUID = -7328478309889711908L;
	static int expire = 30;
	static final String[] ticketSecure = new String[]{"MkZBOgIyMuBCMjg0","2"};
	private String userId;
	private String userName;
	private List<String> links = new ArrayList<String>();
	private Map<String,String> linksName = new HashMap<String, String>();
	private Map<String, Map<String, String>> menus;
	private Date loginTime;
	private Date lastOperateTime;
	private int random;
	private String ip;
	private String ticket;
	
	public boolean isExpire(){
		if(lastOperateTime == null || ((new Date().getTime() - lastOperateTime.getTime()) / (1000 * 60) > expire)){
			return true;
		}
		return false;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
		setLastOperateTime(loginTime);  //第一次登陆的时候 最后操作时间等同登陆时间
	}
	public Date getLastOperateTime() {
		return lastOperateTime;
	}
	public void setLastOperateTime(Date lastOperateTime) {
		this.lastOperateTime = lastOperateTime;
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	public Map<String, Map<String, String>> getMenus() {
		return menus;
	}
	public void setMenus(Map<String, Map<String, String>> menus) {
		this.menus = menus;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	public Map<String, String> getLinksName() {
		return linksName;
	}
	public void setLinksName(Map<String, String> linksName) {
		this.linksName = linksName;
	}
}

