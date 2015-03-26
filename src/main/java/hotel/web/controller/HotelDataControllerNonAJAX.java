/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web.controller;
import hotel.web.entities.Hotel;
import hotel.web.facade.HotelFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Mike
 */
@WebServlet(name = "HotelDataController", urlPatterns = {"/hdcnonajax"})
public class HotelDataControllerNonAJAX extends HttpServlet {

    private static final String RESULT_PAGE = "index.jsp";
    @EJB
    private HotelFacade hs;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            RequestDispatcher view
                    = request.getRequestDispatcher(RESULT_PAGE);
            HttpSession session = request.getSession();
            
            String op = request.getParameter("op");
            Hotel h = null;
            int id = 0;
            boolean getAll = true;
            List result = null;
            if (op != null && op.equals("retrieve")) {
                id = Integer.parseInt(request.getParameter("id"));
                h = hs.find(id);
                request.setAttribute("selectedHotel", h);
                session.setAttribute("selectedHotel", h);
            } else if (op != null && op.equals("update")) {
                id = Integer.parseInt(request.getParameter("id"));
                h = new Hotel(id, (String) request.getParameter("name"), (String) request.getParameter("address"), (String) request.getParameter("city"), (String) request.getParameter("state"), (String) request.getParameter("zip"), (String) request.getParameter("note"));
                    hs.edit(h);
                    session.setAttribute("selectedHotel", h);
            } else if (op != null && op.equals("create")) {
                h = new Hotel(0, (String) request.getParameter("name"), (String) request.getParameter("address"), (String) request.getParameter("city"), (String) request.getParameter("state"), (String) request.getParameter("zip"), (String) request.getParameter("note"));
                    hs.create(h);
                    session.setAttribute("selectedHotel", h);
                
            } else if (op != null && op.equals("search")) {
                String colName = request.getParameter("colName");
                String val = request.getParameter("val");
                if(!colName.equals("all")){
                    result = hs.getHotelsByValue(colName, val);
                    getAll = false;
                }
                
            } else if (op != null && op.equals("delete")) {
                id = Integer.parseInt(request.getParameter("id"));
                    hs.remove(hs.find(id));
                    session.setAttribute("selectedHotel", null);
            }
            if (getAll) {
                result = hs.findAll();
            }

            // Parameters are read only Request object properties, but attributes
            // are read/write. We can use attributes to store data for use on
            // another page.
            Field[] fields = result.get(0).getClass().getDeclaredFields();
            List<String> fieldList = new ArrayList<>();
            for(Field f: fields){
                if(f.getName().charAt(0) != '_' && !f.getName().equals("serialVersionUID")){
                    fieldList.add(f.getName());
                }
            }
            request.setAttribute("hotels", result);
            request.setAttribute("fields", fieldList);
            // This object lets you forward both the request and response
            // objects to a destination page
            
            view.forward(request, response);

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
