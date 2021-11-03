package com.sonic.simple.quartz;

import com.sonic.simple.models.Jobs;
import com.sonic.simple.models.http.RespModel;
import com.sonic.simple.models.interfaces.JobType;
import com.sonic.simple.services.JobsService;
import com.sonic.simple.services.ResultsService;
import com.sonic.simple.services.TestSuitesService;
import com.sonic.simple.tools.FileTool;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZhouYiXun
 * @des 任务实现类
 * @date 2021/8/21 17:44
 */
@Component
public class QuartzJob extends QuartzJobBean implements Job {
    private final Logger logger = LoggerFactory.getLogger(QuartzJob.class);
    @Autowired
    private JobsService jobsService;
    @Autowired
    private TestSuitesService testSuitesService;
    @Autowired
    private ResultsService resultsService;
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    @Autowired
    private FileTool fileTool;
    @Value("${sonic.jobs.filesKeepDay}")
    private int filesKeepDay;
    @Value("${sonic.jobs.resultsKeepDay}")
    private int resultsKeepDay;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        int type = dataMap.getInt("type");
        switch (type) {
            case JobType.TEST_JOB: {
                Jobs jobs = jobsService.findById(dataMap.getInt("id"));
                if (jobs != null) {
                    RespModel r = testSuitesService.runSuite(jobs.getSuiteId(), "SYSTEM");
                    if (r.getCode() == 3001) {
                        logger.info("测试套件" + jobs.getSuiteId() + " 已删除" + r);
                        jobsService.delete(dataMap.getInt("id"));
                    } else {
                        logger.info("定时任务开始执行：测试套件" + jobs.getSuiteId() + " " + r);
                    }
                } else {
                    logger.info("定时任务id:" + dataMap.getInt("id") + "不存在！");
                }
                break;
            }
            case JobType.CLEAN_FILE_JOB: {
                long timeMillis = Calendar.getInstance().getTimeInMillis();
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                List<String> fileList = Arrays.asList("imageFiles", "recordFiles", "logFiles", "packageFiles");
                cachedThreadPool.execute(() -> {
                    for (String fileType : fileList) {
                        File[] t = new File(fileType).listFiles();
                        for (File dateFile : t) {
                            try {
                                if (timeMillis - sf.parse(dateFile.getName()).getTime()
                                        > filesKeepDay * 86400000L) {
                                    logger.info("开始清理：" + dateFile.getPath());
                                    fileTool.deleteDir(dateFile);
                                }
                            } catch (ParseException e) {
                                logger.info("文件名出错：" + dateFile.getPath());
                                logger.error(e.getMessage());
                            }
                        }
                    }
                });
                break;
            }
            case JobType.CLEAN_RESULT_JOB: {
                resultsService.clean(resultsKeepDay);
                break;
            }
            case JobType.SEND_DAY_REPORT: {
                resultsService.sendDayReport();
                break;
            }
            case JobType.SEND_WEEK_REPORT: {
                resultsService.sendWeekReport();
                break;
            }
        }
    }
}
