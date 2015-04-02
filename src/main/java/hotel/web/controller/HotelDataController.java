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
import java.util.List;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mike
 */
//@WebServlet(name = "HotelDataController", urlPatterns = {"/hdc"})
public class HotelDataController extends HttpServlet {

    private static final String RESULT_PAGE = "index.jsp";

    @Inject
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

            String op = request.getParameter("op");
            Hotel h = null;
            int id = 0;
            boolean getAll = true;
            List<Hotel> result = null;

            if (op != null) {
                if (op.equals("create")) {
                    
                    hs.create(h);

                } else if (op.equals("retrieve")) {
                    System.out.println("retrieving...");
                    id = Integer.parseInt(request.getParameter("id"));
                    h = hs.find(id);
                    JsonObjectBuilder builder = Json.createObjectBuilder()
                            .add("hotelId", h.getHotelId())
                            .add("name", h.getHotelName())
                            .add("address", h.getStreetAddress())
                            .add("city", h.getCity())
                            .add("state", h.getState())
                            .add("zip", h.getPostalCode())
                            .add("notes", h.getNotes());

                    JsonObject hotelJson = builder.build();
                    response.setContentType("application/json");
                    out.write(hotelJson.toString());
                    out.flush();
                    getAll = false;
                } else if (op.equals("update")) {
                    id = Integer.parseInt(request.getParameter("id"));
                    h = new Hotel(id, (String) request.getParameter("name"), (String) request.getParameter("address"), (String) request.getParameter("city"), (String) request.getParameter("state"), (String) request.getParameter("zip"), (String) request.getParameter("note"));
                    hs.edit(h);
                    id = Integer.parseInt(request.getParameter("id"));
                    h = hs.find(id);
                    JsonObjectBuilder builder = Json.createObjectBuilder()
                            .add("hotelId", h.getHotelId())
                            .add("name", h.getHotelName())
                            .add("address", h.getStreetAddress())
                            .add("city", h.getCity())
                            .add("state", h.getState())
                            .add("zip", h.getPostalCode())
                            .add("notes", h.getNotes());

                    JsonObject hotelJson = builder.build();
                    response.setContentType("application/json");
                    out.write(hotelJson.toString());
                    out.flush();
                    getAll = false;
                } else if (op.equals("delete")) {
                    id = Integer.parseInt(request.getParameter("id"));
                    hs.remove(hs.find(id));
                    
                }

            }
            if (getAll) {
                System.out.println("getting all...");
                result = hs.findAll();
                JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                result.forEach((hotel) -> {
                    jsonArrayBuilder.add(
                            Json.createObjectBuilder()
                            .add("hotelId", hotel.getHotelId())
                            .add("name", hotel.getHotelName())
                            .add("address", hotel.getStreetAddress())
                            .add("city", hotel.getCity())
                            .add("state", hotel.getState())
                            .add("zip", hotel.getPostalCode())
                            .add("notes", hotel.getNotes())
                    );
                });

                JsonArray hotelsJson = jsonArrayBuilder.build();
                response.setContentType("application/json");
                out.write(hotelsJson.toString());
                out.flush();
            }

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
