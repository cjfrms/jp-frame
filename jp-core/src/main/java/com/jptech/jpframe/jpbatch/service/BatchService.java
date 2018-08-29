package com.jptech.jpframe.jpbatch.service;

import com.jptech.jpframe.core.batch.entity.BatchInfo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    @Autowired
    private Scheduler scheduler;

    public void addJob(BatchInfo batch) throws Exception {
        JobKey jobKey = JobKey.jobKey(batch.getJobName(),batch.getJobGroup());
        if(scheduler.checkExists(jobKey)){
            throw new Exception("job already exists!!!!");
        }
        Class clz = Class.forName(batch.getJobClassName());
        JobDetail job = JobBuilder.newJob(clz)
                .withIdentity(batch.getJobName(),batch.getJobGroup()).build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(batch.getCronExpression());
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(batch.getJobName(), batch.getJobGroup())
                .startNow().withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(job, trigger);
    }

    public void deleteJob(BatchInfo taskInfo) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(taskInfo.getJobName(),taskInfo.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    public void triggerJob(BatchInfo taskInfo) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(taskInfo.getJobName(),taskInfo.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    public void pauseJob(BatchInfo scheduleJob) throws SchedulerException{
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
    }
}
