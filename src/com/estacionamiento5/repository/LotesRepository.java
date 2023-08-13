
package com.estacionamiento5.repository;

import com.estacionamiento5.pojos.Lotes;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import com.estacionamiento5.pojos.ServiciosLote;

/**
 *
 * @author Rhoades - pc
 */
public class LotesRepository extends Repository {
    
    public ArrayList<Object> getLotesOcupados()
    {
        ArrayList<Object> objetos;
        Query q = session.createQuery("from Lotes as lt, ServiciosLote as sl where sl.id = lt.servicio and lt.estado = 'OCUPADO' order by lt.inicio desc");
        List<Object> ls = q.list();
        objetos = (ArrayList) ls;
        return objetos;
    }
    
    public Lotes getLoteOcupado(long lote)
    {
        Query q = session.createQuery("from Lotes as lt, ServiciosLote as sl where sl.id = lt.servicio and lt.estado = 'OCUPADO' and lt.idLote=:lote");
        q.setParameter("lote", lote);
        Object[] obj = (Object[]) q.uniqueResult();
        Lotes l = (Lotes) obj[0];
        ServiciosLote sl = (ServiciosLote) obj[1];
        Lotes lt = new Lotes(l.getIdLote(), l.getEstado(), sl.getNombre(), l.getInicio());
        return lt;
    }
    
}
