package pers.goetboy.quartz.services;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.goetboy.quartz.mapper.ScheduleJobLogMapper;
import pers.goetboy.quartz.model.entity.ScheduleJobLog;
import pers.goetboy.quartz.model.vo.ScheduleJobLogVo;


/**
 * 调度日志业务类.
 * 调度日志相关业务均通过此类进行
 *
 * @author:goetboy;
 * @date 2018 /12 /29
 **/
@Transactional(rollbackFor = Throwable.class)
@Service
public class ScheduleJobLogService {
    private final ScheduleJobLogMapper scheduleJobLogMapper;

    @Autowired
    public ScheduleJobLogService(ScheduleJobLogMapper scheduleJobLogMapper) {
        this.scheduleJobLogMapper = scheduleJobLogMapper;
    }

    /**
     * 存储调度日志
     *
     * @param scheduleJobLog 日志对象
     */
    public void save(ScheduleJobLog scheduleJobLog) {
        scheduleJobLogMapper.insert(scheduleJobLog);
    }

    /**
     * 查询日志信息
     *
     * @param scheduleJobLog 日志参数
     * @return 日志分页列表
     */
    public IPage<ScheduleJobLog> page(ScheduleJobLogVo scheduleJobLog) {
        IPage page = new Page(scheduleJobLog.getCurPage(), scheduleJobLog.getItemsPerPage());
        page = scheduleJobLogMapper.selectPage(page, null);


        return page;

    }

}
