package ru.pfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Settings")
public class Settings {

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Long id;

	@Column(name = "CronExpresion")
	private String cronExpresion;

	@Column(name = "RefreshInterval")
	private long refreshInterval;

	@Column(name = "NumberOfPages")
	private int numberOfPages;

	@Column(name = "ItemsOnPage")
	private int itemsOnPage;

	@Column(name = "MaxLivingLinks")
	private long maxLivingLinks;

	// ============ Properties Section ============

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCronExpresion() {
		return cronExpresion;
	}

	public void setCronExpresion(String cronExpresion) {
		this.cronExpresion = cronExpresion;
	}

	public Settings clone() {
		Settings settings = new Settings();
		settings.setId(id);
		settings.setCronExpresion(cronExpresion);
		settings.setItemsOnPage(itemsOnPage);
		settings.setMaxLivingLinks(maxLivingLinks);
		settings.setNumberOfPages(numberOfPages);
		settings.setRefreshInterval(refreshInterval);
		return settings;
	}

	public long getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(long refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public int getItemsOnPage() {
		return itemsOnPage;
	}

	public void setItemsOnPage(int itemsOnPage) {
		this.itemsOnPage = itemsOnPage;
	}

	public long getMaxLivingLinks() {
		return maxLivingLinks;
	}

	public void setMaxLivingLinks(long maxLivingLinks) {
		this.maxLivingLinks = maxLivingLinks;
	}

}
