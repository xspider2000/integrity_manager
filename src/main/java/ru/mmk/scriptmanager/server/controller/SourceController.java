package ru.mmk.scriptmanager.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.mmk.scriptmanager.server.domain.Source;
import ru.mmk.scriptmanager.server.service.SourceService;

@Controller
@RequestMapping("/sources")
public class SourceController {
	@Autowired
	private SourceService sourceService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	List<Source> getAllSources() {
		return sourceService.listSource();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	Source addSource(@RequestBody Source request) {
		sourceService.addSource(request);
		return request;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody
	Source updateSource(@RequestBody Source request) {
		sourceService.updateSource(request);
		return request;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "setasmaster")
	public @ResponseBody
	void setAsMaster(@RequestBody Source source) {
		sourceService.setSourceAsMaster(source.getId(), source.getNodeId());
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody
	void removeSource(@PathVariable Integer id) {
		sourceService.removeSource(id);
	}

}
