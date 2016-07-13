/* 
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */
 
package org.smqk.quartz.example2;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * <p>
 * This is just a simple job that gets fired off many times by example 1
 * </p>
 * 
 * @author Bill Kratzer
 */
public class SimpleJob implements Job {

    private static Logger log = LoggerFactory.getLogger(SimpleJob.class);
    
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:ss:SSS");
    
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
