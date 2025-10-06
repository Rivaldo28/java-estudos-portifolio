package service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    public boolean sendEmail(String to, String subject, String message){
        //rest of the code.
        boolean f =false;

        String from="rivaldosouzateste@outlook.com";

        //Variable for gmail
        String host="smtp.office365.com";

        //get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES " + properties);

        //setting important information to properties object

        //host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", 587);//465
//        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.ssl.trust", host);

        //Step 1: to get the session object
        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            public PasswordAuthentication getPasswordAuthentication(){//$2a$10$13nuK6AT/LFYTWeyKSEOGuR.B/lSwjBA1feVmwQrWRZfCXNqsLn/2
                return new PasswordAuthentication("rivaldosouzateste@outlook.com", "Riva@2010");
            }
        });

        session.setDebug(true);

        //Step 2 : compose the message [text, mlti media]
        MimeMessage m = new MimeMessage(session);

        try{
            //from email
            m.setFrom(from);

            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            m.setSubject(subject);

            //adding text to message
            m.setText(message);

            //send

            //step 3: send the message using transport class
            Transport.send(m);
//            javax.mail.Transport transport = session.getTransport("smtp");
//            transport.sendMessage(m, m.getAllRecipients());
//            transport.close();


            System.out.println("Sent success...........");
            f=true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
}
