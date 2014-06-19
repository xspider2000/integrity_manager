package ru.mmk.scriptmanager.server.service;

import java.util.List;

import ru.mmk.scriptmanager.server.domain.Source;

public interface SourceService {

	public void addSource(Source source);

	public void updateSource(Source source);

	public void removeSource(Integer id);

	public List<Source> listSource();

	public void setSourceAsMaster(Integer sourceId, Integer nodeId);
}
