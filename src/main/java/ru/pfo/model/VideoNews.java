package ru.pfo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "VideoNews")
public class VideoNews {

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Long id;

	@Column(name = "VideoKey")
	private String videoKey;

	@Column(name = "Title")
	private String title;

	@Column(name = "Title_ru")
	private String title_ru;

	@Column(name = "Title_tr")
	private String title_tr;

	@Column(name = "ImgUrl")
	private String imgUrl;

	@Column(name = "Rating")
	private Integer rating;

	@Column(name = "Duration")
	private Integer duration; // Seconds

	@Column(name = "Views")
	private Long views;

	@Column(name = "AddedDate")
	private Date addedDate;

	@Column(name = "VideoUrl")
	private String videoUrl;

	@Column(name = "LastUrlUpdate")
	private Date lastUrlUpdate;

	@Column(name = "Hiden")
	private Boolean hiden;

	@Transient
	private String playVideoUrl;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "Category_VideoNewses", joinColumns = { @JoinColumn(name = "VideoNewsId") }, inverseJoinColumns = { @JoinColumn(name = "CategoryId") })
	private List<Category> categories = new ArrayList<Category>();

	// ============ Properties Section ============

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_ru() {
		return title_ru;
	}

	public void setTitle_ru(String title_ru) {
		this.title_ru = title_ru;
	}

	public String getTitle_tr() {
		return title_tr;
	}

	public void setTitle_tr(String title_tr) {
		this.title_tr = title_tr;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoKey() {
		return videoKey;
	}

	public void setVideoKey(String videoKey) {
		this.videoKey = videoKey;
	}

	public Date getLastUrlUpdate() {
		return lastUrlUpdate;
	}

	public void setLastUrlUpdate(Date lastUrlUpdate) {
		this.lastUrlUpdate = lastUrlUpdate;
	}

	public Boolean getHiden() {
		return hiden;
	}

	public void setHiden(Boolean hiden) {
		this.hiden = hiden;
	}

	public String toString() {
		String str = "\n================================\n" + "VideoKey: " + videoKey + "\n" + "Title: " + title + "\n"
				+ "Title-ru: " + title_ru + "\n" + "Title_tr: " + title_tr + "\n" + "ImgUrl: " + imgUrl + "\n"
				+ "Rating: " + rating + "\n" + "Duration: " + duration + "\n" + "Views: " + views + "\n"
				+ "AddedDate: " + addedDate + "Hiden: " + getHiden() + "\n================================";
		return str;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Transient
	public String getFormatedDuration() {
		int min = getDuration() / 60;
		int sec = getDuration() - min * 60;
		return String.format("%02d", min) + ":" + String.format("%02d", sec);
	}

	public String getPlayVideoUrl() {
		return playVideoUrl;
	}

	public void setPlayVideoUrl(String playVideoUrl) {
		this.playVideoUrl = playVideoUrl;
	}

}
