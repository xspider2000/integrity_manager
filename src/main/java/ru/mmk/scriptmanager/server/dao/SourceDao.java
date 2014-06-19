package ru.mmk.scriptmanager.server.dao;

import ru.mmk.scriptmanager.server.domain.Source;

public interface SourceDao extends GenericDao<Source> {
	public void setSourceAsMaster(Integer sourceId, Integer nodeId);
}
