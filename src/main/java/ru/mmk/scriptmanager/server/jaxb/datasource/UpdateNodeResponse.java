package ru.mmk.scriptmanager.server.jaxb.datasource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ru.mmk.scriptmanager.server.domain.Node;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class UpdateNodeResponse {

	protected int status;

	@XmlElementWrapper(name = "data")
	@XmlElement(name = "record")
	private List<Node> nodes = new ArrayList<Node>();

	public UpdateNodeResponse() {
	}

	public UpdateNodeResponse(Node node) {
		nodes.add(node);
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
