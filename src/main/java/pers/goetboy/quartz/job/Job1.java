package pers.goetboy.quartz.job;

import pers.goetboy.common.datasource.DynamicDataHelper;

import pers.goetboy.quartz.common.exception.SchedulerException;
import pers.goetboy.quartz.model.Result;
import pers.goetboy.quartz.model.entity.ScheduleJob;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 *测试
 * @author:goetboy;
 * @date 2019 /01 /02
 **/
@Transactional(rollbackFor = Exception.class)
public class Job1 extends AccessMonitorBaseJob {
    private final static Logger logger = LoggerFactory.getLogger(Job1.class);
    private final static String DATA_SOURCE_NAME = "";
    private static String SQL;

    static {
        StringBuffer stringBuffer = new StringBuffer();
        SQL = stringBuffer.toString();


    }

    @Override
    public Result execute(ScheduleJob scheduleJob) throws SchedulerException {
        //加载数据源
        //DynamicDataHelper.setDataSource(DATA_SOURCE_NAME);
        try {
            Result ret = new Result(Result.SUCCESS, "");
            Random random = new Random(2);
            if (random.nextInt() == 1) {
                ret.setStatus(Result.SUCCESS);
                logger.info("没有数据");
            } else {
                ret.setStatus(Result.ERROR);
                StringBuffer msg = new StringBuffer();
                msg.append("有数据");

                ret.setMsg(msg.toString());
                logger.info("检查到数据：[{}]", msg);
            }
            return ret;
        } finally {
            //清除数据源
            //  DynamicDataHelper.clearDataSource();
        }
    }
}
