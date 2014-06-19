package ru.mmk.scriptmanager.server.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.mmk.scriptmanager.server.service.SchedulerService;

@Controller
@RequestMapping("/scheduler/nodes")
public class SchedulerController {
	@Autowired
	private SchedulerService schedulerService;

	@RequestMapping(method = RequestMethod.POST, value = "/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	void startNodeScheduling(@PathVariable Integer id) throws SchedulerException {
		schedulerService.startNodeSchedulling(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody
	void stopNodeScheduling(@PathVariable Integer id) throws SchedulerException {
		schedulerService.stopNodeSchedulling(id);
	}

}