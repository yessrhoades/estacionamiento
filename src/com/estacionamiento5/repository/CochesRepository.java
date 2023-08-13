
package com.estacionamiento5.repository;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Rhoades - pc
 */
public class CochesRepository extends Repository {
    
    public ArrayList<Object> getCoches(String modelo)
    {
        ArrayList<Object> objetos;
        Query q = session.createQuery("from Coches as c, Clientes as cl where c.idCliente=cl.idCliente and c.modelo like '%"+modelo+"%' order by cl.nombre");
        List<Object> ls = q.list();
        objetos = (ArrayList) ls;
        return objetos;
    }
    
}
