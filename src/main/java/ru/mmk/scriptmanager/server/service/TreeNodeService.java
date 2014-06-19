package ru.mmk.scriptmanager.server.service;

import java.util.List;

import ru.mmk.scriptmanager.server.domain.Node;

public interface TreeNodeService {

	public void addNode(Node node);

	public void updateNode(Node node);

	public void removeNode(Integer nodeId);

	public List<Node> listNode();

	public Node getNodeById(Integer nodeId);

	public void incrementErrorCount(Integer nodeId);

	public void incrementRunCount(Integer nodeId);

	public void setLastRunDate(Integer nodeId);
}
