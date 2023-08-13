
package com.estacionamiento5.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Rhoades - pc
 */
public class HisLotesRepository extends Repository {
    
    public ArrayList<Object> getHisServLote(String fecha1, String fecha2)
    {
        ArrayList<Object> objetos;
        Query q = session.createQuery("from HisServLote as h, ServiciosLote as s, Auditoria as au where h.idServicioL = s.id and au.idServicio = h.idServicio and SUBSTRING_INDEX(SUBSTRING_INDEX(au.fecha,' ', 1),' ',-1) >= :fecha1 and SUBSTRING_INDEX(SUBSTRING_INDEX(au.fecha,' ', 1),' ',-1) <= :fecha2 order by au.fecha desc");
        q.setParameter("fecha1", fecha1);
        q.setParameter("fecha2", fecha2);
        List<Object> ls = q.list();
        objetos = (ArrayList) ls;
        return objetos;
    }
    
    public ArrayList<Object> getHisServLoteParaCancelacion(String servicio, Long lote, Date fecha)
    {
        ArrayList<Object> objetos;
        Query q = session.createQuery("from HisServLote as h, ServiciosLote as s, Auditoria as au where h.idServicioL = s.id and au.idServicio = h.idServicio AND h.idServicioL = :servicio AND h.idLote = :lote AND au.fecha = :fecha");
        q.setParameter("servicio", servicio);
        q.setParameter("lote", lote);
        q.setParameter("fecha", fecha);
        List<Object> ls = q.list();
        objetos = (ArrayList) ls;
        return objetos;
    }
    
}
