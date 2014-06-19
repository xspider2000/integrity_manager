package ru.mmk.scriptmanager.server.jaxb.datasource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ru.mmk.scriptmanager.server.domain.Node;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class RemoveNodeRequest extends NodeRequest {
	
	@XmlElement(name = "data")
	private Node node = new Node();

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
}
