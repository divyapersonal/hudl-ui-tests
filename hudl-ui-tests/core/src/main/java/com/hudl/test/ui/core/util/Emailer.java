package com.hudl.test.ui.core.util;

import org.apache.commons.lang3.StringUtils;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Emailer {
    private String mailHost;

    private String mailPort;

    private String mailFrom;

    private List<String> toAddresses = new ArrayList<>();

    private String subject;

    private String text;

    public Emailer(String mailHost, String mailPort, String fromAddress) {
        this.mailHost = mailHost;
        this.mailPort = mailPort;
        this.mailFrom = fromAddress;
    }

    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public void addToAddress(String emailAddress) {
        toAddresses.add(emailAddress);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void send() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", mailHost);
        properties.setProperty("mail.smtp.port", StringUtils.isNotEmpty(mailPort) ? mailPort : "25");
        properties.setProperty("mail.smtp.auth", "false");

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(mailFrom));

            for (String emailAddress : toAddresses) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            }

            message.setSubject(subject);
            message.setText(text);

            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

