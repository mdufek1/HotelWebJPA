package hotel.web.util;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class) // required if using @Autowired as below
public class EmailVerificationSender implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Autowired // required by Spring to inject object from Spring config file
    private MailSender mailSender;
    //@Autowired
    private SimpleMailMessage templateMessage;
   

    public MailSender getMailSender() {
        return mailSender;
    }
    
    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public SimpleMailMessage getTemplateMessage() {
        return templateMessage;
    }

    @Autowired
    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    

    /**
     * Uses Spring's SimleMailMessage technique to send a verification email to
     * the candidate member who attempted registration on Bit Bay. This email is
     * necessary to prevent robots or scammers from registering. A base64
     * encoded username is generated to hide details of the key used by the
     * servlet to identify the user.
     *
     * @param userEmail
     * @param data
     * 
     */
    public void sendEmail(String userEmail, Object data) throws MailException {
//        if(templateMessage == null){
//            System.out.println("templateMessage is null...");
//        }else{
//            System.out.println(templateMessage);
//        }if(mailSender == null){
//            System.out.println("mailSender is null...");
//        }else{
//            System.out.println(mailSender);
//        }
        // Create a Base64 encode of the username
        byte[] encoded = Base64.encode(userEmail.getBytes());
        String base64Username = new String(encoded);
        
        // Create a thread safe "copy" of the template message and customize it
        // See Spring config in applicationContext.xml
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(userEmail);

        msg.setText("Thank you for registering.\n"
                + "Here's the link to complete the registraiton process: \n\n"
                + "http://localhost:8080/HotelJPA/confirm?id=" + base64Username); // change the URL to match your app

        try {
            mailSender.send(msg);
        } catch (NullPointerException npe) {
            throw new MailSendException(
                    "Email send error from EmailVerificationSender");
        }
    }
}
