package com.anniu.ordercall.service.user;

import com.anniu.ordercall.bean.dto.common.MailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class SendMail {
    protected Logger log = LoggerFactory.getLogger(getClass());

    public String toChinese(String text) {
        try {
            text = MimeUtility.encodeText(new String(text.getBytes(), "UTF-8"), "UTF-8", "B");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }


    public boolean sendMail(MailDto mb) {
        String host = mb.getHost();
        final String username = mb.getUsername();
        final String password = mb.getPassword();
        String from = mb.getFrom();
        String to = mb.getTo();
        String subject = mb.getSubject();
        String content = mb.getContent();
        String fileName = mb.getFilename();
        Vector<String> file = mb.getFile();


        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);                // 设置SMTP的主机
        props.put("mail.smtp.auth", "true");            // 需要经过验证
        //props.put("mail.transport.protocol", "smtp");
        //props.put("mail.smtp.starttls.enable","true");
        Session session = Session.getInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            List<InternetAddress> toList = new ArrayList<InternetAddress>();
            String[] tos = to.split(";");
            for (String string : tos) {
                toList.add(new InternetAddress(string));
            }
            InternetAddress[] address = toList.toArray(new InternetAddress[toList.size()]);
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(toChinese(subject));

            Multipart mp = new MimeMultipart();
            MimeBodyPart mbpContent = new MimeBodyPart();
            mbpContent.setText(content);
            mp.addBodyPart(mbpContent);

            /*    往邮件中添加附件    */
            // Enumeration<String> efile = file.elements();
            // while (efile.hasMoreElements()) {
            //     MimeBodyPart mbpFile = new MimeBodyPart();
            //     fileName = efile.nextElement().toString();
            //     FileDataSource fds = new FileDataSource(fileName);
            //     mbpFile.setDataHandler(new DataHandler(fds));
            //     mbpFile.setFileName(toChinese(fds.getName()));
            //     mp.addBodyPart(mbpFile);
            // }

            msg.setContent(mp);
            msg.setSentDate(new Date());
            Transport.send(msg);

        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

}

