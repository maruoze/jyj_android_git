package com.jxtzw.app.bean;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;
@Table(name="collectionentry")
public class CollectionEntry extends Article implements Serializable {

	/**
	 * 序列化ID
	 */
	@Transient
	private static final long serialVersionUID = -5176641501082492709L;
	
	@Id(column="Aid")
	private String Aid;
	private int ccf_classify_id;
	private String ccf_uid;
	
	public String getAid() {
		return Aid;
	}
	public void setAid(String aid) {
		Aid = aid;
	}
	public int getCcf_classify_id() {
		return ccf_classify_id;
	}
	public void setCcf_classify_id(int ccf_classify_id) {
		this.ccf_classify_id = ccf_classify_id;
	}
	public String getCcf_uid() {
		return ccf_uid;
	}
	public void setCcf_uid(String ccf_uid) {
		this.ccf_uid = ccf_uid;
	}
}
