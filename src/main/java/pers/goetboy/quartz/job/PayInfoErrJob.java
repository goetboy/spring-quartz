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


/**
 * 支付后，推送核心没有成功
 *
 * @author:goetboy;
 * @date 2019 /01 /02
 **/
@Transactional(rollbackFor = Exception.class)
public class PayInfoErrJob extends AccessMonitorBaseJob {
    private final static Logger logger = LoggerFactory.getLogger(PayInfoErrJob.class);
    private final static String DATA_SOURCE_NAME = "prlifeDataSource";
    private static String SQL;

    static {
        StringBuffer stringBuffer = new StringBuffer();
        SQL = stringBuffer.append("SELECT * FROM DataInterfaceLog T ")
                .append("WHERE T.`LOGCONTEXT` LIKE '%<actCollectIndi>Y</actCollectIndi>%' ")
                .append("AND remark1 LIKE 'EI%' ")
                .append("AND logdate = Date(SYSDATE())-1 ")
                .append("AND NOT EXISTS ( ")
                .append("SELECT * FROM DataInterfaceLog r ")
                .append("WHERE ( r.`LOGCONTEXT` LIKE '%<uwPass>2</uwPass>%' ")
                .append("OR r.`LOGCONTEXT` LIKE '%<uwPass>1</uwPass>%') ")
                .append("AND remark1 LIKE 'EI_OUT%' ")
                .append("AND logdate = Date(SYSDATE())-1 ")
                .append("AND R.`REFID` = T.`REFID`)").toString();


    }

    @Override
    public Result execute(ScheduleJob scheduleJob) throws SchedulerException {
        DynamicDataHelper.setDataSource(DATA_SOURCE_NAME);
        try {
            Result ret = new Result(Result.SUCCESS, "");
            List<Map<String, Object>> list = baseDao.queryForList(SQL);
            if (CollectionUtils.isEmpty(list)) {
                ret.setStatus(Result.SUCCESS);
                logger.info("没有检查到异常");
            } else {
                ret.setStatus(Result.ERROR);
                StringBuffer msg = new StringBuffer();
                msg.append("支付后，推送核心没有成功的refId :\n");
                for (Map<String, Object> map : list) {
                    msg.append((String) map.get("REFID")).append("|");
                }
                ret.setMsg(msg.toString());
                logger.info("检查到数据异常：[{}]", msg);
            }
            return ret;
        } finally {
            DynamicDataHelper.clearDataSource();
        }
    }
}
