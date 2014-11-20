package com.jxtzw.app.bean;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

@Table(name="collectionclassify")
public class CollectionClassify implements Serializable {

	/**
	 * 序列化ID
	 */
	@Transient
	private static final long serialVersionUID = -3004568669958120911L;
	@Id(column="ccf_classify_id")
	private int ccf_classify_id;
	private String ccf_classify_name;
	private String ccf_uid;
	private String ccf_username;
	public int getCcf_classify_id() {
		return ccf_classify_id;
	}
	public void setCcf_classify_id(int ccf_classify_id) {
		this.ccf_classify_id = ccf_classify_id;
	}
	public String getCcf_classify_name() {
		return ccf_classify_name;
	}
	public void setCcf_classify_name(String ccf_classify_name) {
		this.ccf_classify_name = ccf_classify_name;
	}
	public String getCcf_uid() {
		return ccf_uid;
	}
	public void setCcf_uid(String ccf_uid) {
		this.ccf_uid = ccf_uid;
	}
	public String getCcf_username() {
		return ccf_username;
	}
	public void setCcf_username(String ccf_username) {
		this.ccf_username = ccf_username;
	}
}
