package ru.mmk.scriptmanager.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mmk.scriptmanager.server.dao.SourceDao;
import ru.mmk.scriptmanager.server.domain.Source;

@Service
public class SourceServiceImpl implements SourceService {
	@Autowired
	private SourceDao sourceDao;

	@Override
	@Transactional
	public void addSource(Source source) {
		sourceDao.add(source);
	}

	@Override
	@Transactional
	public void updateSource(Source source) {
		sourceDao.update(source);
	}

	@Override
	@Transactional
	public void removeSource(Integer id) {
		sourceDao.remove(id);
	}

	@Override
	@Transactional
	public List<Source> listSource() {
		return sourceDao.list();
	}

	@Override
	@Transactional
	public void setSourceAsMaster(Integer sourceId, Integer nodeId) {
		sourceDao.setSourceAsMaster(sourceId, nodeId);
	}
}
