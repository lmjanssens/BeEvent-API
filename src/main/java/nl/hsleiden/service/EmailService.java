//package nl.hsleiden.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.stereotype.Component;
//
//import javax.activation.MimeType;
//import javax.mail.Message;
//import javax.mail.Multipart;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
//@Component
//public class EmailService {
//

package nl.hsleiden.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.activation.MimeType;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Component
public class EmailService {

//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void send(String to, String subject, String text) {
//        MimeMessagePreparator preparator = new MimeMessagePreparator() {
//
//            public void prepare(MimeMessage mimeMessage) throws Exception {
//                mimeMessage.setRecipient(Message.RecipientType.TO,
//                        new InternetAddress(to));
//                mimeMessage.setSubject(subject);
//                mimeMessage.setContent(text, "text/html");
//            }
//        };
//
//        try {
//            this.javaMailSender.send(preparator);
//        }
//        catch (MailException ex) {
//            // simply log it and go on...
//            System.err.println(ex.getMessage());
//        }
//    }
//}
}
