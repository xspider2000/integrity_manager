package ru.mmk.scriptmanager.server.jaxb.datasource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ru.mmk.scriptmanager.server.domain.Node;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class FetchNodeRequest extends NodeRequest {

	@XmlElement(name = "data")
	private Node node = new Node();

	private String operationId;

	private String textMatchStyle;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getTextMatchStyle() {
		return textMatchStyle;
	}

	public void setTextMatchStyle(String textMatchStyle) {
		this.textMatchStyle = textMatchStyle;
	}

}
