package com.jxtzw.app.bean;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;
@Table(name="comment")
public class Comment implements Serializable {
	/**
	 * ID
	 */
	@Transient
	private static final long serialVersionUID = 4197506386349095929L;
	@Id(column="Cid")
	private String Cid;
	private String Uid;
	private String Username;
	private int Aid;
	private String Postip;
	private String Dateline;
	private String Status;
	private String Message;
	private String Idtype;
	public String getCid() {
		return Cid;
	}
	public void setCid(String cid) {
		Cid = cid;
	}
	public String getUid() {
		return Uid;
	}
	public void setUid(String uid) {
		Uid = uid;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public int getAid() {
		return Aid;
	}
	public void setAid(int aid) {
		Aid = aid;
	}
	public String getPostip() {
		return Postip;
	}
	public void setPostip(String postip) {
		Postip = postip;
	}
	public String getDateline() {
		return Dateline;
	}
	public void setDateline(String dateline) {
		Dateline = dateline;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getIdtype() {
		return Idtype;
	}
	public void setIdtype(String idtype) {
		Idtype = idtype;
	}
}
