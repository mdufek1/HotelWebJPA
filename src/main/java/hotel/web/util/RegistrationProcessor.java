/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web.util;

import hotel.web.entities.Authorities;
import hotel.web.entities.Users;
import hotel.web.facade.UserFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Mike
 */
public class RegistrationProcessor extends HttpServlet {
    
   ServletContext sctx = getServletContext();
   WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sctx);
//   final AbstractApplicationContext ctx = 
//               new ClassPathXmlApplicationContext("applicationContext.xml");
    private EmailVerificationSender EmailVerificationSender = (EmailVerificationSender)ctx.getBean("EmailVerificationSender");
   // private EmailVerificationSender EmailVerificationSender = new EmailVerificationSender();
    @Inject
    private UserFacade uf;
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uName = request.getParameter("user");
        String pWord = request.getParameter("password");
        saveNewRegistration(uName, pWord);
    }
    public void saveNewRegistration(String userName, String password) {
        Users user = new Users();
        user.setUsername(userName);
        user.setPassword(encodeSha512(password,userName));
        user.setEnabled(false); // don't want enabled until email verified!

        List<Authorities> auths = new ArrayList<Authorities>();
        Authorities auth = new Authorities();
        auth.setAuthority("ROLE_MEMBER"); // or, use any role you want
        auths.add(auth);
        user.setAuthoritiesCollection(auths);
        auth.setUsername(user);
        //System.out.println(user);
        uf.create(user); // you need a UserService (UserFacade)

        try {
             EmailVerificationSender.sendEmail(user.getUsername(), null);
             

        } catch (MailException ex) {
             System.out.println(ex);
             throw new RuntimeException("Sorry, the verification email could not be "
                            + "sent. Please notify the webmaster at "
                            + "hyperbytedev@gmail.com and we'll complete the "
                            + "process for you. Thanks for your patience.");
        }
}

    
    public static String encodeSha512(String pwd, String salt) {
            ShaPasswordEncoder pe = new ShaPasswordEncoder(512);
            pe.setIterations(1024);
            String hash = pe.encodePassword(pwd, salt);
            return hash;
     
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
