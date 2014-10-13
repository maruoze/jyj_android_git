/**
 * 
 */
package com.jxtzw.app.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name="article")
public class Article {
	@Id(column="mAid")
	private String mAid;
	private String mCatid;
	private String mBid;
	private String mUid;
	private String mUsername;
	private String mTitle;
	private String mShorttitle;
	private String mAuthor;
	private String mFrom;
	private String mFromurl;
	private String mUrl;
	private String mSummary;
	private String mPic;
	private String mThumb;
	private String mRemote;
	private String mPrename;
	private String mPreurl;
	private String mId;
	private String mIdtype;
	private String mContents;
	private String mAllowcomment;
	private String mOwncomment;
	private String mClick1;
	private String mClick2;
	private String mClick3;
	private String mClick4;
	private String mClick5;
	private String mClick6;
	private String mClick7;
	private String mClick8;
	private String mTag;
	private String mDateLine;
	private String mStatus;
	private String mHighlight;
	private String mShowinnernav;
	public String getmAid() {
		return mAid;
	}
	public void setmAid(String mAid) {
		this.mAid = mAid;
	}
	public String getmCatid() {
		return mCatid;
	}
	public void setmCatid(String mCatid) {
		this.mCatid = mCatid;
	}
	public String getmBid() {
		return mBid;
	}
	public void setmBid(String mBid) {
		this.mBid = mBid;
	}
	public String getmUid() {
		return mUid;
	}
	public void setmUid(String mUid) {
		this.mUid = mUid;
	}
	public String getmUsername() {
		return mUsername;
	}
	public void setmUsername(String mUsername) {
		this.mUsername = mUsername;
	}
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getmShorttitle() {
		return mShorttitle;
	}
	public void setmShorttitle(String mShorttitle) {
		this.mShorttitle = mShorttitle;
	}
	public String getmAuthor() {
		return mAuthor;
	}
	public void setmAuthor(String mAuthor) {
		this.mAuthor = mAuthor;
	}
	public String getmFrom() {
		return mFrom;
	}
	public void setmFrom(String mFrom) {
		this.mFrom = mFrom;
	}
	public String getmFromurl() {
		return mFromurl;
	}
	public void setmFromurl(String mFromurl) {
		this.mFromurl = mFromurl;
	}
	public String getmUrl() {
		return mUrl;
	}
	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}
	public String getmSummary() {
		return mSummary;
	}
	public void setmSummary(String mSummary) {
		this.mSummary = mSummary;
	}
	public String getmPic() {
		return mPic;
	}
	public void setmPic(String mPic) {
		this.mPic = mPic;
	}
	public String getmThumb() {
		return mThumb;
	}
	public void setmThumb(String mThumb) {
		this.mThumb = mThumb;
	}
	public String getmRemote() {
		return mRemote;
	}
	public void setmRemote(String mRemote) {
		this.mRemote = mRemote;
	}
	public String getmPrename() {
		return mPrename;
	}
	public void setmPrename(String mPrename) {
		this.mPrename = mPrename;
	}
	public String getmPreurl() {
		return mPreurl;
	}
	public void setmPreurl(String mPreurl) {
		this.mPreurl = mPreurl;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmIdtype() {
		return mIdtype;
	}
	public void setmIdtype(String mIdtype) {
		this.mIdtype = mIdtype;
	}
	public String getmContents() {
		return mContents;
	}
	public void setmContents(String mContents) {
		this.mContents = mContents;
	}
	public String getmAllowcomment() {
		return mAllowcomment;
	}
	public void setmAllowcomment(String mAllowcomment) {
		this.mAllowcomment = mAllowcomment;
	}
	public String getmOwncomment() {
		return mOwncomment;
	}
	public void setmOwncomment(String mOwncomment) {
		this.mOwncomment = mOwncomment;
	}
	public String getmClick1() {
		return mClick1;
	}
	public void setmClick1(String mClick1) {
		this.mClick1 = mClick1;
	}
	public String getmClick2() {
		return mClick2;
	}
	public void setmClick2(String mClick2) {
		this.mClick2 = mClick2;
	}
	public String getmClick3() {
		return mClick3;
	}
	public void setmClick3(String mClick3) {
		this.mClick3 = mClick3;
	}
	public String getmClick4() {
		return mClick4;
	}
	public void setmClick4(String mClick4) {
		this.mClick4 = mClick4;
	}
	public String getmClick5() {
		return mClick5;
	}
	public void setmClick5(String mClick5) {
		this.mClick5 = mClick5;
	}
	public String getmClick6() {
		return mClick6;
	}
	public void setmClick6(String mClick6) {
		this.mClick6 = mClick6;
	}
	public String getmClick7() {
		return mClick7;
	}
	public void setmClick7(String mClick7) {
		this.mClick7 = mClick7;
	}
	public String getmClick8() {
		return mClick8;
	}
	public void setmClick8(String mClick8) {
		this.mClick8 = mClick8;
	}
	public String getmTag() {
		return mTag;
	}
	public void setmTag(String mTag) {
		this.mTag = mTag;
	}
	public String getmDateLine() {
		return mDateLine;
	}
	public void setmDateLine(String mDateLine) {
		this.mDateLine = mDateLine;
	}
	public String getmStatus() {
		return mStatus;
	}
	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}
	public String getmHighlight() {
		return mHighlight;
	}
	public void setmHighlight(String mHighlight) {
		this.mHighlight = mHighlight;
	}
	public String getmShowinnernav() {
		return mShowinnernav;
	}
	public void setmShowinnernav(String mShowinnernav) {
		this.mShowinnernav = mShowinnernav;
	}
}
