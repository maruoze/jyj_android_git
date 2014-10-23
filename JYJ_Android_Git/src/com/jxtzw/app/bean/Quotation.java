package com.jxtzw.app.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name="quotation")
public class Quotation {
	private String quo_en_code;
	private String quo_chs_code;
	private String quo_price;
	private String quo_price_change;
	private String quo_price_change_rate;
	private String quo_open_price;
	private String quo_close_price;
	private String quo_high_price;
	private String quo_low_price;
	private String quo_date;
	private String quo_date_time;
	private String quo_time;
	@Id(column="quo_type_id")
	private String quo_type_id;
	public String getQuo_en_code() {
		return quo_en_code;
	}
	public void setQuo_en_code(String quo_en_code) {
		this.quo_en_code = quo_en_code;
	}
	public String getQuo_chs_code() {
		return quo_chs_code;
	}
	public void setQuo_chs_code(String quo_chs_code) {
		this.quo_chs_code = quo_chs_code;
	}
	public String getQuo_price() {
		return quo_price;
	}
	public void setQuo_price(String quo_price) {
		this.quo_price = quo_price;
	}
	public String getQuo_price_change() {
		return quo_price_change;
	}
	public void setQuo_price_change(String quo_price_change) {
		this.quo_price_change = quo_price_change;
	}
	public String getQuo_price_change_rate() {
		return quo_price_change_rate;
	}
	public void setQuo_price_change_rate(String quo_price_change_rate) {
		this.quo_price_change_rate = quo_price_change_rate;
	}
	public String getQuo_open_price() {
		return quo_open_price;
	}
	public void setQuo_open_price(String quo_open_price) {
		this.quo_open_price = quo_open_price;
	}
	public String getQuo_close_price() {
		return quo_close_price;
	}
	public void setQuo_close_price(String quo_close_price) {
		this.quo_close_price = quo_close_price;
	}
	public String getQuo_high_price() {
		return quo_high_price;
	}
	public void setQuo_high_price(String quo_high_price) {
		this.quo_high_price = quo_high_price;
	}
	public String getQuo_low_price() {
		return quo_low_price;
	}
	public void setQuo_low_price(String quo_low_price) {
		this.quo_low_price = quo_low_price;
	}
	public String getQuo_date() {
		return quo_date;
	}
	public void setQuo_date(String quo_date) {
		this.quo_date = quo_date;
	}
	public String getQuo_date_time() {
		return quo_date_time;
	}
	public void setQuo_date_time(String quo_date_time) {
		this.quo_date_time = quo_date_time;
	}
	public String getQuo_time() {
		return quo_time;
	}
	public void setQuo_time(String quo_time) {
		this.quo_time = quo_time;
	}
	public String getQuo_type_id() {
		return quo_type_id;
	}
	public void setQuo_type_id(String quo_type_id) {
		this.quo_type_id = quo_type_id;
	}
}