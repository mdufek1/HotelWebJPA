/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web.util;

import hotel.web.entities.Users;
import hotel.web.facade.UserFacade;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Mike
 */
public class RegistrationVerifier extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Note that we are using @Inject vs. @EJB. Inject is better. But
    // you must have bean.xml installed in your web app under the
    // "WEB-INF" directory.
    @Inject
    UserFacade userService;
    
    @Inject 
    EmailVerificationSender emailService;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String errMsg = "";
        String destination = "/registrationVerified.jsp";
        
        try {
            String id = request.getParameter("id");
            byte[] decoded = Base64.decode(id.getBytes());
            String username = new String(decoded);
            Users user = userService.find(username);
            if(user == null) {
                throw new RuntimeException("Sorry, that user is not in our system");
            }
            user.setEnabled(true);
            userService.edit(user);
                        
        } catch(Exception dae) {
            errMsg = "VERIFICATION ERROR: " + dae.getLocalizedMessage();
            request.setAttribute("errMsg", errMsg);
            destination = "/registrationError.jsp";
        }
        
                    
        RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher(destination);
                dispatcher.forward(request, response);     
		
    } // end of processRequest method ... NOT end of the servlet!!
    
        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
