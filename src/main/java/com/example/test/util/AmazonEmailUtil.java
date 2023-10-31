package com.example.test.util;

import com.sun.mail.util.MailSSLSocketFactory;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Properties;

/**
 * @author lp
 * @date 2021-11-19 10:34
 */
public class AmazonEmailUtil {

    public static final String FROM = "noreply@edfs.io";
    public static final String TO = "etanges@163.com";

    public static final String SMTP_USERNAME = "noreply";
    public static final String SMTP_PASSWORD = "!Qaz@wsx";

//    static final String HOST = "smtp.mail.us-west-2.awsapps.com";
//    static final String HOST = "imap.mail.us-west-2.awsapps.com";
public static final String HOST = "mobile.mail.us-west-2.awsapps.com";

    public static final int PORT = 465;

    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    static final String CONFIGSET = "ConfigSet";



    public static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";

    public static final String BODY = String.join(
            System.getProperty("line.separator"),
            "<h1>Amazon SES SMTP Email Test</h1>",
            "<p>This email was sent with Amazon SES using the ",
            "<a href='https://github.com/javaee/javamail'>Javamail Package</a>",
            " for <a href='https://www.java.com'>Java</a>."
    );

//          邮箱发送jar
//        <!-- https://mvnrepository.com/artifact/javax.mail/mail -->
//        <dependency>
//            <groupId>javax.mail</groupId>
//            <artifactId>mail</artifactId>
//            <version>1.4.7</version>
//        </dependency>
//        <!-- https://mvnrepository.com/artifact/javax.activation/activation -->
//        <dependency>
//            <groupId>javax.activation</groupId>
//            <artifactId>activation</artifactId>
//            <version>1.1.1</version>
//        </dependency>
    public static void main(String[] args) throws Exception {
        //第一版 卡在链接上 结果都一样，正在尝试连接到主机“imap.mail.us-west-2.awsapps.com/smtp.mail.us-west-2.awsapps.com”，端口465，isSSL错误
        /*
        DEBUG: setDebug: JavaMail version 1.4.7
        DEBUG: getProvider() returning javax.mail.Provider[TRANSPORT,smtp,com.sun.mail.smtp.SMTPTransport,Oracle]
        Sending...
        DEBUG SMTP: useEhlo true, useAuth true
        DEBUG SMTP: trying to connect to host "imap.mail.us-west-2.awsapps.com", port 465, isSSL false
         */
//        sendMessage();
        //第二版

        /*
        错误提示：
        DEBUG: setDebug: JavaMail version 1.4.7
        sendEmail.....
        DEBUG: getProvider() returning javax.mail.Provider[TRANSPORT,smtp,com.sun.mail.smtp.SMTPTransport,Oracle]
        DEBUG SMTP: useEhlo true, useAuth true
        DEBUG SMTP: useEhlo true, useAuth true
        DEBUG SMTP: trying to connect to host "smtp.mail.us-west-2.awsapps.com", port 465, isSSL false
         */

//        sendTowMessage();

        //第三版
//        send();

        //第四版
        sendEmail(FROM,TO,SUBJECT,BODY,SMTP_PASSWORD);

    }


    /**
     * 第四版
     */
    public static void sendEmail(String fromEmail, String toEmail, String title, String textContext, String password) throws Exception {
        Properties properties = new Properties();
        //设置qq邮箱的服务器
        properties.setProperty("mail.host", "smtp.mail.us-west-2.awsapps.com");
        //邮件发送协议
        properties.setProperty("mail.transport.protocol", "smtp");
        //验证用户名以及邮箱授权码
        properties.setProperty("mail.smtp.auth", "true");
        //关于qq邮箱，还要设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        //开始发送邮件
        //创建定义整个应用程序所需的环境信息的session
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        //开启session，查看程序的运行状态
        session.setDebug(true);
        //通过session得到transport对象
        Transport ts = session.getTransport();
        //使用邮箱的用户名和授权码连上邮箱服务器
        ts.connect("smtp.mail.us-west-2.awsapps.com", fromEmail, password);
        //创建邮件
        MimeMessage mimeMessage = new MimeMessage(session);
        //指明邮件的发件人
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        //指明邮件的收件人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        //邮件的标题
        mimeMessage.setSubject(title);
        //邮件的文本内容
        mimeMessage.setContent(textContext, "text/html;charset=utf-8");
        //发送邮件
        ts.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        //关闭连接
        ts.close();
    }


    /**
     * 第三版
     */
    public static void send() throws Exception {

        ExchangeService service = sendThreeMessage();
        EmailMessage msg = new EmailMessage(service);
        msg.setSubject(SUBJECT);
        MessageBody body = MessageBody.getMessageBodyFromText(BODY);
        body.setBodyType(BodyType.HTML);
        msg.setBody(body);
        msg.getToRecipients().add(TO);
        msg.send();
    }

    private static ExchangeService sendThreeMessage(){
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
        // 用户认证信息
        ExchangeCredentials credentials;
        credentials = new WebCredentials(SMTP_USERNAME, SMTP_PASSWORD);
        service.setCredentials(credentials);
        try {
            service.setUrl(new URI("https://mobile.mail.us-west-2.awsapps.com/EWS/exchange.asmx"));
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
        return service;
    }



    /**
     * 第二版
     */
    public static void sendTowMessage() throws MessagingException {
        final Properties props = new Properties();
        // 发件人使用发邮件的电子信箱服务器
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", PORT);
        props.put("mail.user", SMTP_USERNAME);
        props.put("mail.password", SMTP_PASSWORD);
        props.put("mail.smtp.quitwait", "false");
        Authenticator atctr = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = props.getProperty("mail.user");
                String passWord = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, passWord);
            }
        };

        Session mailsession = Session.getInstance(props, atctr);
        mailsession.setDebug(true);
        //构造消息
        MimeMessage msg = new MimeMessage(mailsession);
        InternetAddress from = new InternetAddress(FROM);
        msg.setFrom(from);
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSentDate(new Date());
        msg.setSubject(SUBJECT);
        msg.setText(BODY);
        msg.setContent(BODY, "text/html;charset=UTF-8");
        msg.saveChanges();
        // 发送邮件
        System.out.println("sendEmail.....");
        Transport.send(msg);
        System.out.println("sendMessage success!");

    }


    /**
     * 第一版
     * @throws MessagingException
     */
    public static void sendMessage() throws MessagingException {
        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.quitwait", "false");
        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html");

        // Add a configuration set header. Comment or delete the
        // next line if you are not using a configuration set
        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try
        {
            System.out.println("Sending...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }







}
