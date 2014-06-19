package ru.mmk.scriptmanager.server.controller.datasource;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.mmk.scriptmanager.server.domain.Node;
import ru.mmk.scriptmanager.server.domain.Source;
import ru.mmk.scriptmanager.server.jaxb.datasource.AddNodeRequest;
import ru.mmk.scriptmanager.server.jaxb.datasource.AddNodeResponse;
import ru.mmk.scriptmanager.server.jaxb.datasource.FetchNodeRequest;
import ru.mmk.scriptmanager.server.jaxb.datasource.FetchNodeResponse;
import ru.mmk.scriptmanager.server.jaxb.datasource.RemoveNodeRequest;
import ru.mmk.scriptmanager.server.jaxb.datasource.RemoveNodeResponse;
import ru.mmk.scriptmanager.server.jaxb.datasource.UpdateNodeRequest;
import ru.mmk.scriptmanager.server.jaxb.datasource.UpdateNodeResponse;
import ru.mmk.scriptmanager.server.service.TreeNodeService;

@Controller
@RequestMapping("/nodes/")
public class TreeController {
	@Autowired
	private TreeNodeService treeNodeService;

	@RequestMapping("/fetch")
	public @ResponseBody
	FetchNodeResponse listNodes(@RequestBody FetchNodeRequest request) {
		FetchNodeResponse response = new FetchNodeResponse(treeNodeService.listNode());
		return response;
	}

	@RequestMapping("/add")
	public @ResponseBody
	AddNodeResponse addNode(@RequestBody AddNodeRequest request) {
		Node node = request.getNode();
		treeNodeService.addNode(node);
		AddNodeResponse response = new AddNodeResponse(node);
		return response;
	}

	@RequestMapping("/update")
	public @ResponseBody
	UpdateNodeResponse updateNode(@RequestBody UpdateNodeRequest request) {
		Node node = request.getNode();
		treeNodeService.updateNode(node);
		UpdateNodeResponse response = new UpdateNodeResponse(node);
		return response;
	}

	@RequestMapping("/remove")
	public @ResponseBody
	RemoveNodeResponse deleteNode(@RequestBody RemoveNodeRequest request) {
		Node node = request.getNode();
		treeNodeService.removeNode(node.getId());
		RemoveNodeResponse response = new RemoveNodeResponse(node);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{nodeId}/sources")
	public @ResponseBody
	Set<Source> fetchSources(@PathVariable Integer nodeId) {
		return treeNodeService.getNodeById(nodeId).getSources();
	}
}
