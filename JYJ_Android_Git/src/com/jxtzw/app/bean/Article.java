/**
 * 
 */
package com.jxtzw.app.bean;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

@Table(name="article")
public class Article implements Serializable {
	/**
	 * 序列化ID
	 */
	@Transient
	private static final long serialVersionUID = -36355259443173107L;
	@Id(column="Aid")
	private String Aid;
	private int Catid;
	private String Bid;
	private String Uid;
	private String Username;
	private String Title;
	private String Shorttitle;
	private String Author;
	private String Url;
	private String Summary;
	private String Pic;
	private String Thumb;
	private String Remote;
	private String Prename;
	private String Preurl;
	private String Idtype;
	private String Contents;
	private String Allowcomment;
	private String Owncomment;
	private String Click1;
	private String Click2;
	private String Click3;
	private String Click4;
	private String Click5;
	private String Click6;
	private String Click7;
	private String Click8;
	private String Tag;
	private String DateLine;
	private String Status;
	private String Highlight;
	private String Showinnernav;
	
	
	public String getAid() {
		return Aid;
	}
	public void setAid(String aid) {
		Aid = aid;
	}
	public int getCatid() {
		return Catid;
	}
	public void setCatid(int catid) {
		Catid = catid;
	}
	public String getBid() {
		return Bid;
	}
	public void setBid(String bid) {
		Bid = bid;
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
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getShorttitle() {
		return Shorttitle;
	}
	public void setShorttitle(String shorttitle) {
		Shorttitle = shorttitle;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getSummary() {
		return Summary;
	}
	public void setSummary(String summary) {
		Summary = summary;
	}
	public String getPic() {
		return Pic;
	}
	public void setPic(String pic) {
		Pic = pic;
	}
	public String getThumb() {
		return Thumb;
	}
	public void setThumb(String thumb) {
		Thumb = thumb;
	}
	public String getRemote() {
		return Remote;
	}
	public void setRemote(String remote) {
		Remote = remote;
	}
	public String getPrename() {
		return Prename;
	}
	public void setPrename(String prename) {
		Prename = prename;
	}
	public String getPreurl() {
		return Preurl;
	}
	public void setPreurl(String preurl) {
		Preurl = preurl;
	}
	public String getIdtype() {
		return Idtype;
	}
	public void setIdtype(String idtype) {
		Idtype = idtype;
	}
	public String getContents() {
		return Contents;
	}
	public void setContents(String contents) {
		Contents = contents;
	}
	public String getAllowcomment() {
		return Allowcomment;
	}
	public void setAllowcomment(String allowcomment) {
		Allowcomment = allowcomment;
	}
	public String getOwncomment() {
		return Owncomment;
	}
	public void setOwncomment(String owncomment) {
		Owncomment = owncomment;
	}
	public String getClick1() {
		return Click1;
	}
	public void setClick1(String click1) {
		Click1 = click1;
	}
	public String getClick2() {
		return Click2;
	}
	public void setClick2(String click2) {
		Click2 = click2;
	}
	public String getClick3() {
		return Click3;
	}
	public void setClick3(String click3) {
		Click3 = click3;
	}
	public String getClick4() {
		return Click4;
	}
	public void setClick4(String click4) {
		Click4 = click4;
	}
	public String getClick5() {
		return Click5;
	}
	public void setClick5(String click5) {
		Click5 = click5;
	}
	public String getClick6() {
		return Click6;
	}
	public void setClick6(String click6) {
		Click6 = click6;
	}
	public String getClick7() {
		return Click7;
	}
	public void setClick7(String click7) {
		Click7 = click7;
	}
	public String getClick8() {
		return Click8;
	}
	public void setClick8(String click8) {
		Click8 = click8;
	}
	public String getTag() {
		return Tag;
	}
	public void setTag(String tag) {
		Tag = tag;
	}
	public String getDateLine() {
		return DateLine;
	}
	public void setDateLine(String dateLine) {
		DateLine = dateLine;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getHighlight() {
		return Highlight;
	}
	public void setHighlight(String highlight) {
		Highlight = highlight;
	}
	public String getShowinnernav() {
		return Showinnernav;
	}
	public void setShowinnernav(String showinnernav) {
		Showinnernav = showinnernav;
	}
	
	
	
}
