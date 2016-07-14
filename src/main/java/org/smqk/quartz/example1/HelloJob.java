package org.smqk.quartz.example1;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job{

	private static Logger log = LoggerFactory.getLogger(HelloJob.class);
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
	
	private static int staticVar = 0;
	
	private int instanceVar = 0;
	
	/**
	 * HelloJob is a simple job that implements the Job interface and logs a nice message to the log (by default, this will simply go to the screen). 
	 * 
	 * The current date and time is printed in the job so that you can see exactly when the job is run.
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String curDate = simpleDateFormat.format(new Date());
		++staticVar;
        ++instanceVar;
		log.info("【" + curDate + "】 HelloJob ,staticVar: " + staticVar + "  ,instanceVar:" + instanceVar);
	}

}
