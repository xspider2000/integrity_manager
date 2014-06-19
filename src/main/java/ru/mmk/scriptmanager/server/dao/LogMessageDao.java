package ru.mmk.scriptmanager.server.dao;

import java.util.Date;
import java.util.List;

import ru.mmk.scriptmanager.server.domain.LogMessage;

public interface LogMessageDao extends GenericDao<LogMessage> {
	public List<LogMessage> listByDate(Integer nodeId, Date dateFrom, Date dateTo);
}
