package com.jptech.jpframe.jpbatch.contoller;

import com.jptech.jpframe.core.batch.entity.BatchInfo;
import com.jptech.jpframe.jpbatch.service.BatchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BatchController {

    @Autowired
    BatchService batchService;

    @ApiOperation("添加任务")
    @PostMapping("add")
    public ResponseEntity<BatchInfo> add(@RequestBody BatchInfo batch) throws Exception {
        batchService.addJob(batch);
        return ResponseEntity.status(HttpStatus.CREATED).body(batch);
    }

    @ApiOperation("删除任务")
    @PostMapping("delete")
    public ResponseEntity<BatchInfo> delete(@RequestBody BatchInfo batch) throws Exception {
        batchService.addJob(batch);
        return ResponseEntity.status(HttpStatus.OK).body(batch);
    }
}
