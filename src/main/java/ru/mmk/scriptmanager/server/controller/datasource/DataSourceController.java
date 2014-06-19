package ru.mmk.scriptmanager.server.controller.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.mmk.scriptmanager.server.jaxb.datasource.FetchDataSourcesResponse;
import ru.mmk.scriptmanager.server.service.DataSourceService;

@Controller
@RequestMapping("/connection_data")
public class DataSourceController {
	@Autowired
	private DataSourceService dataSourceService;

	@RequestMapping("/fetch")
	public @ResponseBody
	FetchDataSourcesResponse getAllDataSources() {
		return new FetchDataSourcesResponse(dataSourceService.list());
	}
}