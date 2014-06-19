package ru.mmk.scriptmanager.server.jaxb.datasource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ru.mmk.scriptmanager.server.domain.DataSource;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class FetchDataSourcesResponse {

	private int startRow;
	private int endRow;
	private int totalRows;
	private int status;

	@XmlElementWrapper(name = "data")
	@XmlElement(name = "record")
	private List<DataSource> connectionDataList = new ArrayList<DataSource>();

	public FetchDataSourcesResponse() {
	}

	public FetchDataSourcesResponse(List<DataSource> connectionDataList) {
		this.connectionDataList = connectionDataList;
		status = 0;
		startRow = 0;
		endRow = connectionDataList.size() - 1;
		totalRows = endRow + 1;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<DataSource> getConnectionDataList() {
		return connectionDataList;
	}

	public void setConnectionDataList(List<DataSource> connectionDataList) {
		this.connectionDataList = connectionDataList;
	}

}
