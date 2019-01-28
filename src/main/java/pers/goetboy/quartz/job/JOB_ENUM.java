package pers.goetboy.quartz.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 需在前台展示的job类
 *
 * @author:goetboy;
 * @date 2019 /01 /03
 **/
public enum JOB_ENUM {
    payInfoErrJob(/*"支付后未推送成功异常检测",*/ "pers.goetboy.quartz.job.PayInfoErrJob"),
    payInfoErrJob2(/*"没有支付记录核心已支付异常检测",*/ "pers.goetboy.quartz.job.PayInfoErrJob2");
    //private String charatCn;
    private String classPath;

   /* JOB_ENUM(String charatCn, String classPath) {
        this.charatCn = charatCn;
        this.classPath = classPath;
    }*/

    JOB_ENUM(String classPath) {
        this.classPath = classPath;
    }

    public static List<Map<String, String>> map() {
        List<Map<String, String>> list = new ArrayList(JOB_ENUM.values().length);
        for (JOB_ENUM value : values()) {
            Map map = new HashMap(1);
            map.put("className", value.name());
            map.put("classPath", value.getClassPath());
            list.add(map);
        }

        return list;
    }

    //public String getCharatCn() {return charatCn;  }

    public String getClassPath() {
        return classPath;
    }
}
