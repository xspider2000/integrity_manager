package ru.mmk.scriptmanager.server.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class ComparingJob implements Job {
	@Autowired
	ComparatorService comparatorService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		Integer nodeId = context.getJobDetail().getJobDataMap().getInt("nodeId");
		comparatorService.compareSourcesData(nodeId);
	}
}
