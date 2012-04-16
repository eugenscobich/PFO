package ru.pfo.model;

import java.util.ArrayList;
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
@Table(name = "Category")
public class Category {

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Long id;

	@Column(name = "Name")
	private String name;

	@Column(name = "UUID")
	private String uuid;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "Category_VideoNewses", joinColumns = { @JoinColumn(name = "CategoryId") }, inverseJoinColumns = { @JoinColumn(name = "VideoNewsId") })
	private List<VideoNews> videoNewses = new ArrayList<VideoNews>();

	@Transient
	private Long size;

	// ============ Properties Section ============

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VideoNews> getVideoNewses() {
		return videoNewses;
	}

	public void setVideoNewses(List<VideoNews> videoNewses) {
		this.videoNewses = videoNewses;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String toString() {
		String str = "\n" + "Name: " + name + "\n" + "Uuid: " + uuid;
		for (VideoNews videoNews : videoNewses) {
			str = str.concat(videoNews.toString());
		}
		return str;
	}
}
