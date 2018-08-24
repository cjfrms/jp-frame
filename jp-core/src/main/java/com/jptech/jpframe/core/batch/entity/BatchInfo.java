package com.jptech.jpframe.core.batch.entity;

import lombok.Data;

@Data
public class BatchInfo {
    private String jobId;
    private String jobName;
    private String jobGroup;
    private String jobClassName;
    private String cronExpression;
    private String desc;
}
