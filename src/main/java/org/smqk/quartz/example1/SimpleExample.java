package org.smqk.quartz.example1;

import java.util.Date;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smqk.quartz.example1.HelloJob;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.DateBuilder.*;

/**
 * Example 1 - Your First Quartz Program 
 * 
 * This example is designed to demonstrate how to get up and running with Quartz. 
 * This example will fire off a simple job that says “Hello World”.
 * 
 * The program will perform the following actions:
 * 
 * Start up the Quartz Scheduler Schedule a job to run at the next even minute
 * Wait for 90 seconds to give Quartz a chance to run the job Shut down the
 * Scheduler
 * 
 * @author smqk
 * @date 2016年7月13日 上午10:54:38
 */
public class SimpleExample {
	
	private static Logger log = LoggerFactory.getLogger(SimpleExample.class);
	
	public void run() throws Exception {
	    log.info("------- Initializing ----------------------");

	    /*
	     * The program starts by getting an instance of the Scheduler. 
	     * This is done by creating a StdSchedulerFactory and then using it to create a scheduler. 
	     * This will create a simple, RAM-based scheduler.
	     * */
	    // First we must get a reference to a scheduler
	    SchedulerFactory sf = new StdSchedulerFactory();
	    Scheduler sched = sf.getScheduler();

	    log.info("------- Initialization Complete -----------");

		/*
		 * We create a SimpleTrigger that will fire off at the next round minute:
		 */
	    // computer a time that is on the next round minute
	    Date runTime = evenMinuteDate(new Date());

	    log.info("------- Scheduling Job  -------------------");

	    /*
		 * The HelloJob is defined as a Job to Quartz using the JobDetail class:
		 */
	    // define the job and tie it to our HelloJob class
	    JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();

	    // Trigger the job to run on the next round minute
	    Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

	    /**
		 * We now will associate the Job to the Trigger in the scheduler:
		 */
	    // Tell quartz to schedule the job using our trigger
	    sched.scheduleJob(job, trigger);
	    log.info(job.getKey() + " will run at: " + runTime);

	    /**
		 * At this point, the job has been schedule to run when its trigger fires. 
		 * However, the scheduler is not yet running. So, we must tell the scheduler to start up!
		 */
	    // Start up the scheduler (nothing can actually run until the scheduler has been started)
	    sched.start();

	    log.info("------- Started Scheduler -----------------");

	    // wait long enough so that the scheduler as an opportunity to run the job!
	    log.info("------- Waiting 90 seconds... -------------");
	    try {
	    	/**
			 * To let the program have an opportunity to run the job, we then sleep for 90 seconds. 
			 * The scheduler is running in the background and should fire off the job during those 90 seconds.
			 */
		      // wait 65 seconds to show job
		      Thread.sleep(90L * 1000L);
		      // executing...
	    } catch (Exception e) {
	      //
	    }

	    log.info("------- Shutting Down ---------------------");
	    /**
		 * Finally, we will gracefully shutdown the scheduler:
		 */
	    // shut down the scheduler
	    sched.shutdown(true);
	    log.info("------- Shutdown Complete -----------------");
	}

	public static void main(String[] args) throws Exception{
		// 运行依赖 puartz.properties 配置
		SimpleExample example = new SimpleExample();
	    example.run();
	}

}
/**
 * Note: passing true into the shutdown message tells the Quartz Scheduler to wait until all jobs have completed running before returning from the method call.
 * */
