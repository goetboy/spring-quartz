package pers.goetboy.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.goetboy.quartz.model.entity.ScheduleJob;
import org.springframework.stereotype.Repository;


/**
 *  * job mapper.
 *  *ScheduleJobMapper继承的 {@link BaseMapper} 接口已经提供了需要基本的sql执行方法，如果里面方法不满足业务使用，在此接口下增加相应方法
 *  *
 * @author:goetb
 * @date 2019 /01 /18
 **/
@Repository
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {
}
