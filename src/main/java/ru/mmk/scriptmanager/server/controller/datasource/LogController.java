package ru.mmk.scriptmanager.server.controller.datasource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.mmk.scriptmanager.server.jaxb.datasource.FetchLogRequest;
import ru.mmk.scriptmanager.server.jaxb.datasource.FetchLogResponse;
import ru.mmk.scriptmanager.server.service.LogMessageService;

@Controller
@RequestMapping("/log")
public class LogController {
	@Autowired
	private LogMessageService logMessageService;

	@RequestMapping("/fetch")
	public @ResponseBody
	FetchLogResponse getAllLogMessages(@RequestBody FetchLogRequest request) {
		Integer nodeId = request.getParams().getNodeId();
		Date dateFrom = request.getParams().getDateFrom();
		Date dateTo = request.getParams().getDateTo();
		return new FetchLogResponse(logMessageService.listLogMessageByDate(nodeId, dateFrom, dateTo));
	}
}