
package com.estacionamiento5.repository;

import static com.estacionamiento5.repository.Repository.session;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Rhoades - pc
 */
public class BaniosRepository extends Repository {
    
    public ArrayList<Object> getHisBanios(String fecha1, String fecha2)
    {
        ArrayList<Object> objetos;
        Query q = session.createQuery("from HisServBanios as hsb, Banios as b where hsb.tipoServicio=b.id and SUBSTRING_INDEX(SUBSTRING_INDEX(fecha,' ', 1),' ',-1) >= :fecha1 and SUBSTRING_INDEX(SUBSTRING_INDEX(fecha,' ', 1),' ',-1) <= :fecha2 order by hsb.fecha desc");
        q.setParameter("fecha1", fecha1);
        q.setParameter("fecha2", fecha2);
        List<Object> ls = q.list();
        objetos = (ArrayList) ls;
        return objetos;
    }
    
}
