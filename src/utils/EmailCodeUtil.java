package utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class EmailCodeUtil {
    /**
     * QQ邮箱验证
     *
     * @param toMail
     *            收件人邮箱
     * @return 收件人邮箱+验证码
     */
    public static String qqSendMail(String toMail) {
        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = null;
        String code=createCode();
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getInstance(props);

            Message msg = new MimeMessage(session);
            msg.setSubject("GG 邮箱验证");
            StringBuilder builder = new StringBuilder();
            builder.append("验证码:"+code);
            builder.append("\n欢迎使用");
            Date date=new Date();
            builder.append("\n时间: " + date);
            msg.setText(builder.toString());
            msg.setFrom(new InternetAddress("1021661582@qq.com"));
            Transport transport = session.getTransport();
            transport.connect("smtp.qq.com", "1021661582@qq.com", "ktpzzgsrnvivbbbg");
            //填写收件人邮箱
            transport.sendMessage(msg, new Address[] { new InternetAddress(toMail) });
            transport.close();

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return "";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "";
        }
        return code;
    }

    /**
     * 产生四位随机数
     *
     * @return
     */
    public static String createCode() {
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if (randLength < 4) {
            for (int i = 1; i <= 4 - randLength; i++)
                fourRandom = "0" + fourRandom;
        }
        return fourRandom;
    }
}
