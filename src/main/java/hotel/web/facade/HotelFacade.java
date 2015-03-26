/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import hotel.web.entities.Hotel;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author mdufek1
 */
@Stateless
public class HotelFacade extends AbstractFacade<Hotel> {
    @PersistenceContext(unitName = "HotelPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Hotel> getHotelsByValue(String col, String val){
        TypedQuery<Hotel> query = em.createQuery("SELECT h FROM Hotel h WHERE "+col+" LIKE '%"+val+"%'", Hotel.class);
        List<Hotel> hotels = query.getResultList();
//        String queryStr = "SELECT h FROM Hotel h WHERE "+col+" LIKE '%"+val+"%'";
//        System.out.println(queryStr);
//        List<Hotel> hotels = em.createQuery(queryStr).getResultList();
        return hotels;
    }

    public HotelFacade() {
        super(Hotel.class);
    }
    
}
