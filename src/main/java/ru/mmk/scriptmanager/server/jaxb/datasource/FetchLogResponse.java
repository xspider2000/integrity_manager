package ru.mmk.scriptmanager.server.jaxb.datasource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ru.mmk.scriptmanager.server.domain.LogMessage;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class FetchLogResponse {

	private int startRow;
	private int endRow;
	private int totalRows;

	@XmlElementWrapper(name = "data")
	@XmlElement(name = "record")
	private List<LogMessage> logMessages = new ArrayList<LogMessage>();
	protected int status;

	public FetchLogResponse() {
	}

	public FetchLogResponse(List<LogMessage> logMessages) {
		this.logMessages = logMessages;
		status = 0;
		startRow = 0;
		endRow = logMessages.size() - 1;
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

	public List<LogMessage> getLogMessages() {
		return logMessages;
	}

	public void setLogMessages(List<LogMessage> logMessages) {
		this.logMessages = logMessages;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
