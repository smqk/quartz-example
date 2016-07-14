package org.smqk.quartz.example3;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class SimpleJob implements Job {

    private static Logger log = LoggerFactory.getLogger(SimpleJob.class);
    
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
    
    private static int staticVar = 0;
	
    private int instanceVar = 0;
    
    /**
     * Empty constructor for job initialization
     */
    public SimpleJob() {}

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a
     * <code>{@link org.quartz.Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     * 
     * @throws JobExecutionException
     *             if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)  throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        String curDate = simpleDateFormat.format(new Date());
        ++staticVar;
        ++instanceVar;
        log.info("【"+curDate+"】SimpleJob says: " + jobKey + " ,staticVar: " + staticVar + "  ,instanceVar:" + instanceVar);
    }
    

}
