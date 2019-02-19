package pers.goetboy.common.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 邮箱发件配置类.
 * 从配置文件中读取邮件服务器的信息
 *
 * @author:goetboy;
 * @date 2018 /12 /26
 **/
@Component
@PropertySource("classpath:config.properties")
public class Config {
    @Value("${mail.server.ip}")
    private String ip;
    @Value("${mail.server.port}")
    private Integer port;
    @Value("${mail.server.username}")

    private String username;
    @Value("${mail.server.password}")

    private String password;
    @Value("${mail.server.from}")
    private String from;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


}
