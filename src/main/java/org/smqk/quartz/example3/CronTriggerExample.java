package org.smqk.quartz.example3;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This Example will demonstrate all of the basics of scheduling capabilities of Quartz using Cron Triggers.
 * 
 * @author smqk
 */
public class CronTriggerExample {

  public void run() throws Exception {
    Logger log = LoggerFactory.getLogger(CronTriggerExample.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");

    log.info("------- Initializing -------------------");

    // First we must get a reference to a scheduler
    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler sched = sf.getScheduler();

    log.info("------- Initialization Complete --------");

    log.info("------- Scheduling Jobs ----------------");

    // job1 任务重复执行,重复执行时间间隔为20秒
    JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();

    CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(cronSchedule("0/20 * * * * ?")).build();

    Date ft = sched.scheduleJob(job, trigger);
    log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，cron表达式：  " + trigger.getCronExpression());
    

    // job2 重复执行任务，重复规则以时间在0分15秒为基础每2分钟触发一次
    job = newJob(SimpleJob.class).withIdentity("job2", "group1").build();
    trigger = newTrigger().withIdentity("trigger2", "group1").withSchedule(cronSchedule("15 0/2 * * * ?")).build();

    ft = sched.scheduleJob(job, trigger);
    log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，cron表达式：  " + trigger.getCronExpression());
    
    
    // job3重复执行，重复规则在8点~17点之间  && 0分0秒为基础每2分钟触发一次
    job = newJob(SimpleJob.class).withIdentity("job3", "group1").build();
    trigger = newTrigger().withIdentity("trigger3", "group1").withSchedule(cronSchedule("0 0/2 8-17 * * ?")).build();

    ft = sched.scheduleJob(job, trigger);
    log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，cron表达式：  " + trigger.getCronExpression());
    
    
    // job4重复执行，重复规则在17点~23点之间  && 0分0秒为基础每3分钟触发一次
    job = newJob(SimpleJob.class).withIdentity("job4", "group1").build();
    trigger = newTrigger().withIdentity("trigger4", "group1").withSchedule(cronSchedule("0 0/3 17-23 * * ?")).build();

    ft = sched.scheduleJob(job, trigger);
    log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，cron表达式：  " + trigger.getCronExpression());
    
    
    // job5重复执行，重复规则  上午10点0分0秒 && (每月的1日或者15日)
    job = newJob(SimpleJob.class).withIdentity("job5", "group1").build();
    trigger = newTrigger().withIdentity("trigger5", "group1").withSchedule(cronSchedule("0 0 10am 1,15 * ?")).build();

    ft = sched.scheduleJob(job, trigger);
    log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，cron表达式：  " + trigger.getCronExpression());
    
    
    // job6 重复执行，重复规则 周一到周五在每分0秒或30秒触发
    job = newJob(SimpleJob.class).withIdentity("job6", "group1").build();
    trigger = newTrigger().withIdentity("trigger6", "group1").withSchedule(cronSchedule("0,30 * * ? * MON-FRI")).build();

    ft = sched.scheduleJob(job, trigger);
    log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，cron表达式：  " + trigger.getCronExpression());
    
    
    // job 7 重复执行，重复规则 周六和周日在每分0秒或30秒触发
    job = newJob(SimpleJob.class).withIdentity("job7", "group1").build();

    trigger = newTrigger().withIdentity("trigger7", "group1").withSchedule(cronSchedule("0,30 * * ? * SAT,SUN")).build();

    ft = sched.scheduleJob(job, trigger);
    log.info(job.getKey() + " 任务将在: " + sdf.format(ft) + " 时执行 ，cron表达式：  " + trigger.getCronExpression());
    

    log.info("------- Starting Scheduler ----------------");

    sched.start();

    log.info("------- Started Scheduler -----------------");

    log.info("------- Waiting five minutes... ------------");
    try {
      // wait five minutes to show jobs
      Thread.sleep(300L * 1000L);
      // executing...
    } catch (Exception e) {
      //
    }

    log.info("------- Shutting Down ---------------------");

    sched.shutdown(true);

    log.info("------- Shutdown Complete -----------------");

    SchedulerMetaData metaData = sched.getMetaData();
    log.info("总共执行了 " + metaData.getNumberOfJobsExecuted() + " 个任务.");

  }

  public static void main(String[] args) throws Exception {
    CronTriggerExample example = new CronTriggerExample();
    example.run();
  }

}
