package pers.goetboy.common.mail;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮件发送处理接口
 *
 * @author:goetboy;
 * @date 2018 /12 /17
 **/
@Component
public class MailHelper {
    private JavaMailSender javaMailSender;
    private MimeMessage mimeMessage;

    private String encoding = "UTF-8";
    public MailHelper() {
        this.javaMailSender = new JavaMailSenderImpl();
        this.mimeMessage = javaMailSender.createMimeMessage();

    }

    /**
     * 传入编码
     * @param encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public MailHelper setConfig(Config config) throws MessagingException {
        if (StringUtils.isEmpty(config.getFrom())) {
            setFrom(config.getUsername());
        } else {
            setFrom(config.getFrom());
        }
        return setServer(config.getIp(), config.getPort(), config.getUsername(), config.getPassword());

    }

    /**
     * 消息发送
     *
     * @throws MessagingException 邮件助手异常
     * @throws UnsentMessageException 消息发送异常
     */
    public void send() throws UnsentMessageException, MessagingException {
        if (javaMailSender == null) {
            throw new UnsentMessageException("邮件服务没有实例化");
        }
        JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) javaMailSender;
        javaMailSenderImpl.testConnection();
        javaMailSender.send(mimeMessage);

    }

    /**
     * 传入接收人地址，可以用逗号分隔多个地址
     * 也可以作多参数传入
     *
     * @param mailAddress 邮件地址
     * @return this
     * @throws MessagingException 邮件配置异常
     */
    public MailHelper setSender(String... mailAddress) throws MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, encoding);
        for (String address : mailAddress) {
            mimeMessageHelper.setTo(InternetAddress.parse(address));
        }
        return this;
    }

    /**
     * 传入发送人信息
     *
     * @param mailAddress 邮件地址
     * @return this
     * @throws MessagingException 邮件助手配置异常
     */
    public MailHelper setFrom(String mailAddress) throws MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom(mailAddress);

        return this;
    }

    /**
     * 传入标题
     *
     * @param str 标题
     * @return this
     * @throws MessagingException 邮件助手配置异常
     */
    public MailHelper setSubject(String str) throws MessagingException {
        new MimeMessageHelper(mimeMessage).setSubject(str);
        return this;
    }

    /**
     * 传入邮件内容
     *
     * @param str 邮件内容
     * @return this
     * @throws MessagingException 邮件助手配置异常
     */
    public MailHelper setText(String str) throws MessagingException {
        new MimeMessageHelper(mimeMessage).setText(str);
        return this;
    }

    private MailHelper setServer(String ip, int port, String username, String password) {

        return setServer(ip, port, username, password, null);
    }

    /**
     * 传入接收人地址，可以用逗号分隔多个地址
     * 也可以作多参数传入
     *
     * @param ip       ip地址
     * @param port     端口号
     * @param username 用户名
     * @param password 密码
     * @param protocol 协议
     */
    private MailHelper setServer(String ip, int port, String username, String password, String protocol) {

        JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) javaMailSender;
        javaMailSenderImpl.setDefaultEncoding(encoding);
        javaMailSenderImpl.setHost(ip);
        javaMailSenderImpl.setPort(port);
        javaMailSenderImpl.setUsername(username);
        javaMailSenderImpl.setPassword(password);
        javaMailSenderImpl.setProtocol(protocol);
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "false");
        javaMailSenderImpl.setJavaMailProperties(p);

        return this;
    }
}
