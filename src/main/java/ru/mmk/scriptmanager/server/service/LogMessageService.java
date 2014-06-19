package ru.mmk.scriptmanager.server.service;

import java.util.Date;
import java.util.List;

import ru.mmk.scriptmanager.server.domain.LogMessage;

public interface LogMessageService {
	public List<LogMessage> listLogMessage();

	public List<LogMessage> listLogMessageByDate(Integer nodeId, Date dateFrom,
			Date dateTo);
}
