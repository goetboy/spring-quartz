package pers.goetboy.quartz.controller;

import org.springframework.http.ResponseEntity;

/**
 * 控制器超类，做一些通用配置和方法
 *
 * @author:goetb
 * @date 2019 /01 /22
 **/
public abstract class AbstractController {
    /**
     * 执行成功
     *
     * @param t 返回参数
     * @return 带返回值响应
     */
    protected <T> ResponseEntity<T> success(T t) {
        return ResponseEntity.ok(t);
    }

    /**
     * 执行成功
     *
     * @return 无返回值响应
     */
    protected ResponseEntity success() {
        return ResponseEntity.ok().build();
    }

}
