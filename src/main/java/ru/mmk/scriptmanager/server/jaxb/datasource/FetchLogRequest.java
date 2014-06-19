package ru.mmk.scriptmanager.server.jaxb.datasource;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class FetchLogRequest {

	@XmlElement(name = "data")
	private CriteriaParams params = new CriteriaParams();
	private String dataSource;
	private String operationType;
	private Integer startRow;
	private Integer endRow;
	private String sortBy;
	private String textMatchStyle;
	private String componentId;
	private String oldValues;

	public CriteriaParams getParams() {
		return params;
	}

	public void setParams(CriteriaParams params) {
		this.params = params;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getTextMatchStyle() {
		return textMatchStyle;
	}

	public void setTextMatchStyle(String textMatchStyle) {
		this.textMatchStyle = textMatchStyle;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getOldValues() {
		return oldValues;
	}

	public void setOldValues(String oldValues) {
		this.oldValues = oldValues;
	}

	public static class CriteriaParams {
		private Integer nodeId;
		private Date dateFrom;
		private Date dateTo;

		public Integer getNodeId() {
			return nodeId;
		}

		public void setNodeId(Integer nodeId) {
			this.nodeId = nodeId;
		}

		public Date getDateFrom() {
			return dateFrom;
		}

		public void setDateFrom(Date dateFrom) {
			this.dateFrom = dateFrom;
		}

		public Date getDateTo() {
			return dateTo;
		}

		public void setDateTo(Date dateTo) {
			this.dateTo = dateTo;
		}

	}
}
