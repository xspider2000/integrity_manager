package ru.mmk.scriptmanager.server.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "tree")
public class Node {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "parent_id")
	private Integer parentId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "schedule", length = 50)
	private String schedule;

	@Transient
	private boolean isScheduled;

	@Transient
	private int sourceCount;

	@Column(name = "run_count", nullable = false)
	private int runCount;

	@Column(name = "error_count", nullable = false)
	private int errorCount;

	@Column(name = "last_run_date")
	private Date lastRunDate;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "node_id", updatable = false)
	@OrderBy("name ASC")
	private Set<Source> sources = new HashSet<Source>();

	@XmlTransient
	public Set<Source> getSources() {
		return sources;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public boolean isScheduled() {
		return isScheduled;
	}

	public void setScheduled(boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	public int getSourceCount() {
		return sourceCount;
	}

	public void setSourceCount(int sourceCount) {
		this.sourceCount = sourceCount;
	}

	public int getRunCount() {
		return runCount;
	}

	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public Date getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}

}
