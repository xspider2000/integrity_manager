package ru.mmk.scriptmanager.server.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.mmk.scriptmanager.server.domain.Node;
import ru.mmk.scriptmanager.server.domain.Source;
import ru.mmk.scriptmanager.server.service.TreeNodeService;

@Controller
@RequestMapping("/nodes")
public class RestTreeController {
	@Autowired
	private TreeNodeService treeNodeService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	List<Node> listNodes() {
		return treeNodeService.listNode();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	Node addNode(@RequestBody Node request) {
		treeNodeService.addNode(request);
		return request;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody
	Node updateNode(@RequestBody Node request) {
		treeNodeService.updateNode(request);
		return request;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody
	Node deleteNode(@PathVariable Integer id) {
		Node node = treeNodeService.getNodeById(id);
		treeNodeService.removeNode(id);
		return node;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{nodeId}/sources")
	public @ResponseBody
	Set<Source> fetchSources(@PathVariable Integer nodeId) {
		return treeNodeService.getNodeById(nodeId).getSources();
	}
}
