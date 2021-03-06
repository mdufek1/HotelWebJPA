/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import hotel.web.entities.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mdufek1
 */
@Stateless
public class UserFacade extends AbstractFacade<Users> {
    
    private static final Logger LOG  = LoggerFactory.getLogger(UserFacade.class);
    @PersistenceContext(unitName = "HotelPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(Users.class);
    }
    
}
