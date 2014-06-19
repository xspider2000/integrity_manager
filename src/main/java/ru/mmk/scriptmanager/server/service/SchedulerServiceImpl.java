package ru.mmk.scriptmanager.server.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.mmk.scriptmanager.server.domain.Node;

@Service
public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private TreeNodeService treeNodeService;

	@Override
	public void startNodeSchedulling(Integer nodeId) throws SchedulerException {
		Node node = treeNodeService.getNodeById(nodeId);
		String cronSchedule = node.getSchedule();

		JobDetail job = JobBuilder.newJob(ComparingJob.class).withIdentity(nodeId.toString(), nodeId.toString())
				.build();
		job.getJobDataMap().put("nodeId", nodeId);

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(nodeId.toString(), nodeId.toString())
				.withSchedule(
						CronScheduleBuilder.cronSchedule(cronSchedule).withMisfireHandlingInstructionFireAndProceed())
				.forJob(nodeId.toString(), nodeId.toString()).build();

		scheduler.scheduleJob(job, trigger);
	}

	@Override
	public void stopNodeSchedulling(Integer nodeId) throws SchedulerException {
		for (String groupName : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				String jobName = jobKey.getName();
				String jobGroup = jobKey.getGroup();

				if (jobName.equals(nodeId.toString()) && jobGroup.equals(nodeId.toString())) {
					// found it!
					scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
					return;
				}
			}
		}

		throw new SchedulerException("Node with id = " + nodeId + " is not found in scheduller");
	}

	@Override
	public boolean isScheduled(Integer nodeId) throws SchedulerException {
		for (String groupName : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				String jobName = jobKey.getName();
				String jobGroup = jobKey.getGroup();

				if (jobName.equals(nodeId.toString()) && jobGroup.equals(nodeId.toString())) {
					// found it!
					return true;
				}
			}
		}
		return false;
	}

}
