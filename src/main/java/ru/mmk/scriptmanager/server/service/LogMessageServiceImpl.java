package ru.mmk.scriptmanager.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mmk.scriptmanager.server.dao.LogMessageDao;
import ru.mmk.scriptmanager.server.dao.TreeNodeDao;
import ru.mmk.scriptmanager.server.domain.LogMessage;

@Service
public class LogMessageServiceImpl implements LogMessageService {

	@Autowired
	private LogMessageDao logMessageDao;

	@Autowired
	private TreeNodeDao treeNodeDao;

	@Override
	@Transactional
	public List<LogMessage> listLogMessage() {
		return logMessageDao.list();
	}

	@Override
	@Transactional
	public List<LogMessage> listLogMessageByDate(Integer nodeId, Date dateFrom,
			Date dateTo) {
		return logMessageDao.listByDate(nodeId, dateFrom, dateTo);
	}
}
