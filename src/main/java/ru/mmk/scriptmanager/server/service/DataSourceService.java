package ru.mmk.scriptmanager.server.service;

import java.util.List;

import ru.mmk.scriptmanager.server.domain.DataSource;

public interface DataSourceService {

	public List<DataSource> list();

	public DataSource getByName(String name);
}
