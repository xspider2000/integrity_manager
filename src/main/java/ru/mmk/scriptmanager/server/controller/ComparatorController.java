package ru.mmk.scriptmanager.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.mmk.scriptmanager.server.service.ComparatorService;

@Controller
@RequestMapping("/comparesources")
public class ComparatorController {
	@Autowired
	private ComparatorService comparatorService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	void compareAllSources(@RequestParam Integer nodeId) {
		comparatorService.compareSourcesData(nodeId);
	}
}