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
	private static int staticVar = 0;
	private int instanceVar = 0;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String curDate = new SimpleDateFormat("yyyy-MM-dd hh:ss:SSS").format(new Date());
		log.info("【"+curDate+"】 HelloJob ,staticVar: "+(++staticVar)+"  ,instanceVar:"+(++instanceVar));
	}

}
