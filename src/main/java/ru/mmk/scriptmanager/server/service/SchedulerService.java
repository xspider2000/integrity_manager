package ru.mmk.scriptmanager.server.service;

import org.quartz.SchedulerException;

public interface SchedulerService {

	public void startNodeSchedulling(Integer nodeId) throws SchedulerException;

	public void stopNodeSchedulling(Integer nodeId) throws SchedulerException;

	public boolean isScheduled(Integer nodeId) throws SchedulerException;
}
