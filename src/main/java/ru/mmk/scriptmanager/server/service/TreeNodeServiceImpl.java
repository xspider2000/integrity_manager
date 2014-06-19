package ru.mmk.scriptmanager.server.service;

import java.util.Date;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mmk.scriptmanager.server.dao.TreeNodeDao;
import ru.mmk.scriptmanager.server.domain.Node;

@Service
public class TreeNodeServiceImpl implements TreeNodeService {

	@Autowired
	private TreeNodeDao treeNodeDao;

	@Autowired
	private SchedulerService schedulerService;

	@Override
	@Transactional
	public void addNode(Node node) {
		treeNodeDao.add(node);
	}

	@Override
	@Transactional
	public void removeNode(Integer id) {
		treeNodeDao.remove(id);
	}

	@Override
	@Transactional
	public List<Node> listNode() {
		final List<Node> listNode = treeNodeDao.list();
		for (Node node : listNode) {
			setAttributeScheduled(node);
			setSourceCount(node);
		}
		return listNode;
	}

	@Override
	@Transactional
	public Node getNodeById(Integer nodeId) {
		final Node node = treeNodeDao.getById(nodeId);
		setAttributeScheduled(node);
		setSourceCount(node);
		return node;
	}

	@Override
	@Transactional
	public void updateNode(Node node) {
		treeNodeDao.update(node);
	}

	private void setAttributeScheduled(Node node) {
		Integer nodeId = node.getId();
		try {
			node.setScheduled(schedulerService.isScheduled(nodeId));
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private void setSourceCount(Node node) {
		node.setSourceCount(node.getSources().size());
	}

	@Override
	@Transactional
	public void incrementErrorCount(Integer nodeId) {
		Node node = treeNodeDao.getById(nodeId);
		int errorCount = node.getErrorCount();
		errorCount++;
		node.setErrorCount(errorCount);
		updateNode(node);
	}

	@Override
	@Transactional
	public void incrementRunCount(Integer nodeId) {
		Node node = treeNodeDao.getById(nodeId);
		int runCount = node.getRunCount();
		runCount++;
		node.setRunCount(runCount);
		updateNode(node);
	}

	@Override
	@Transactional
	public void setLastRunDate(Integer nodeId) {
		Node node = treeNodeDao.getById(nodeId);
		Date curentDate = new Date();
		node.setLastRunDate(curentDate);
		updateNode(node);
	}
}
