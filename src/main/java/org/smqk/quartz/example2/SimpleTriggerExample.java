package org.smqk.quartz.example2;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This Example will demonstrate all of the basics of scheduling capabilities of Quartz using Simple Triggers.
 * 
 * @author smqk
 */
public class SimpleTriggerExample {
	
	public void run() throws Exception {
		Logger log = LoggerFactory.getLogger(SimpleTriggerExample.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");

		log.info("------- Initializing -------------------");

		// First we must get a reference to a scheduler
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		log.info("------- Initialization Complete --------");

		log.info("------- Scheduling Jobs ----------------");

		// get a "nice round" time a few seconds in the future...
		log.debug("currentDate:"+sdf.format(new Date()));
		Date startTime = DateBuilder.nextGivenSecondDate(null, 15);
		log.debug("startDate:"+sdf.format(startTime));

		// job1 任务仅会执行一次
		JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();
		SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity("trigger1", "group1").startAt(startTime).build();

		// schedule it to run!
		Date ft = sched.scheduleJob(job, trigger);
		log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，重复执行  "
				+ trigger.getRepeatCount() + " 次, 每次间隔 "
				+ trigger.getRepeatInterval() / 1000 + " 秒");

		
		// job2 任务仅会执行一次
		job = newJob(SimpleJob.class).withIdentity("job2", "group1").build();
		trigger = (SimpleTrigger) newTrigger().withIdentity("trigger2", "group1").startAt(startTime).build();

		ft = sched.scheduleJob(job, trigger);
		log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，重复执行  "
				+ trigger.getRepeatCount() + " 次, 每次间隔 "
				+ trigger.getRepeatInterval() / 1000 + " 秒");

		
		// job3 将会执行11次 (执行一次，重复10次 )
		job = newJob(SimpleJob.class).withIdentity("job3", "group1").build();
		trigger = newTrigger().withIdentity("trigger3", "group1").startAt(startTime)
				.withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10)).build();

		ft = sched.scheduleJob(job, trigger);
		log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，重复执行  "
				+ trigger.getRepeatCount() + " 次, 每次间隔 "
				+ trigger.getRepeatInterval() / 1000 + " 秒");

		// 使用匿名的触发器调度相同的任务 (job3)
		// 这次任务仅仅会重复执行2次（重复执行时间间隔为10秒）
		trigger = newTrigger().withIdentity("trigger3", "group2").startAt(startTime)
				.withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(2)).forJob(job).build();

		ft = sched.scheduleJob(trigger);
		log.info(job.getKey() + " 任务(同样的)将在: " + sdf.format(ft) + " 时执行 ，重复执行  "
				+ trigger.getRepeatCount() + " 次, 每次间隔 "
				+ trigger.getRepeatInterval() / 1000 + " 秒");
		
		
		// job4 任务将会执行6次 (执行一次，重复5次 )
		// job4 任务重复执行时间间隔为10秒
		job = newJob(SimpleJob.class).withIdentity("job4", "group1").build();
		trigger = newTrigger().withIdentity("trigger4", "group1").startAt(startTime)
				.withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(5)).build();

		ft = sched.scheduleJob(job, trigger);
		log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，重复执行  "
				+ trigger.getRepeatCount() + " 次, 每次间隔 "
				+ trigger.getRepeatInterval() / 1000 + " 秒");

		
		// 在未来的5分钟内，job5 任务将会执行一次
		job = newJob(SimpleJob.class).withIdentity("job5", "group1").build();
		trigger = (SimpleTrigger) newTrigger()
				.withIdentity("trigger5", "group1")
				.startAt(futureDate(5, IntervalUnit.MINUTE)).build();

		ft = sched.scheduleJob(job, trigger);
		log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，重复执行  "
				+ trigger.getRepeatCount() + " 次, 每次间隔 "
				+ trigger.getRepeatInterval() / 1000 + " 秒");

		
		// job6 将不间断的重复执行，重复执行时间间隔为5秒
		// 不间断执行时执行次数为 -1
		job = newJob(SimpleJob.class).withIdentity("job6", "group1").build();
		trigger = newTrigger().withIdentity("trigger6", "group1").startAt(startTime)
				.withSchedule(simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();

		ft = sched.scheduleJob(job, trigger);
		log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，重复执行  "
				+ trigger.getRepeatCount() + " 次, 每次间隔 "
				+ trigger.getRepeatInterval() / 1000 + " 秒");

		
		log.info("------- Starting Scheduler ----------------");

		/**
		 * 所有的任务都别添加到任务调度器(scheduler)中,但时此时所有的任务都不会被执行直到调度器被启动
		 */
		sched.start();

		log.info("------- Started Scheduler -----------------");

		// 任务也可以在调度器启动后被调度执行
		// job7 任务将会重复执行20次，重复执行时间间隔为5分钟
		job = newJob(SimpleJob.class).withIdentity("job7", "group1").build();
		trigger = newTrigger().withIdentity("trigger7", "group1").startAt(startTime)
				.withSchedule(simpleSchedule().withIntervalInMinutes(5).withRepeatCount(20)).build();

		ft = sched.scheduleJob(job, trigger);
		log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，重复执行  "
				+ trigger.getRepeatCount() + " 次, 每次间隔 "
				+ trigger.getRepeatInterval() / 1000 + " 秒");

		
		// 不通过触发器执行任务
		job = newJob(SimpleJob.class).withIdentity("job8", "group1").storeDurably().build();
		sched.addJob(job, true);

		log.info("'Manually' triggering job8...");
		sched.triggerJob(jobKey("job8", "group1"));

		log.info("------- Waiting 30 seconds... --------------");

		try {
			// wait 33 seconds to show jobs
			Thread.sleep(30L * 1000L);
			// executing...
		} catch (Exception e) {
			//
		}

		// 任务重新调度（更改任务的调度：重复执行10次重复执行时间间隔为1秒钟）
		log.info("------- Rescheduling... --------------------");
		trigger = newTrigger().withIdentity("trigger7", "group1").startAt(startTime)	
				.withSchedule(simpleSchedule().withIntervalInSeconds(1).withRepeatCount(10)).build();

		ft = sched.rescheduleJob(trigger.getKey(), trigger);
		log.info("job7 rescheduled to run at: " + ft);

		log.info("------- Waiting five minutes... ------------");
		try {
			// wait five minutes to show jobs
			Thread.sleep(300L * 1000L);
			// executing...
		} catch (Exception e) {
			//
		}

		log.info("------- Shutting Down ---------------------");
		//一旦停止的调度器，不能再重写启动
		sched.shutdown(true);
		//sched.start(); //重启动调度器将抛出异常 org.quartz.SchedulerException: The Scheduler cannot be restarted after shutdown() has been called.

		log.info("------- Shutdown Complete -----------------");

		SchedulerMetaData metaData = sched.getMetaData();
		log.info("总共执行了 " + metaData.getNumberOfJobsExecuted() + " 个任务.");

	}

	public static void main(String[] args) throws Exception {
		SimpleTriggerExample example = new SimpleTriggerExample();
		example.run();
	}

}
