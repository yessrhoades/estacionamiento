
package com.estacionamiento5.repository;

import static com.estacionamiento5.repository.Repository.session;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import com.estacionamiento5.pojos.Clientes;
import com.estacionamiento5.pojos.Coches;
import com.estacionamiento5.pojos.Pensiones;
import com.estacionamiento5.pojos.ServicioPension;

/**
 *
 * @author Rhoades - pc
 */
public class PensionesRepository extends Repository {
    
    public ArrayList<Object> getPensiones(String fecha1, String fecha2)
    {
        ArrayList<Object> pensiones;
        Query q = session.createQuery("FROM ServicioPension sp, Coches c, Clientes cl, Pensiones p, Auditoria au WHERE sp.idCoche = c.idCoche AND c.idCliente = cl.idCliente AND sp.idPension = p.id AND au.idServicio=sp.idServicio and SUBSTRING_INDEX(SUBSTRING_INDEX(au.fecha,' ', 1),' ',-1) >= :fecha1 and SUBSTRING_INDEX(SUBSTRING_INDEX(au.fecha,' ', 1),' ',-1) <= :fecha2 order by au.fecha desc");
        q.setParameter("fecha1", fecha1);
        q.setParameter("fecha2", fecha2);
        List<Object> ls = q.list();
        pensiones = (ArrayList) ls;
        return pensiones;
    }
    
    public ArrayList<Object> getPensionesActivas()
    {        
        ArrayList<Object> pensiones;
        Query q = session.createQuery("FROM ServicioPension sp, Coches c, Clientes cl, Pensiones p, Auditoria au WHERE sp.idCoche = c.idCoche AND c.idCliente = cl.idCliente AND sp.idPension = p.id AND au.idServicio=sp.idServicio AND sp.activa = true order by au.fecha desc");
        List<Object> ls = q.list();
        pensiones = (ArrayList) ls;
        return pensiones;
    }
    
    public ServicioPension getCochePension(String coche)
    {
        ServicioPension servicio;
        servicio = (ServicioPension) session.createCriteria(ServicioPension.class).add(Restrictions.eq("idCoche", coche)).add(Restrictions.eq("activa", true)).uniqueResult();
        return servicio;
    }
    
    public Object getDatosForTicket(String idCoche)
    { 
        Object[] obj;
        Query q = session.createQuery("FROM Coches as c, Clientes cl WHERE c.idCliente = cl.idCliente and c.idCoche = :idCoche");
        q.setParameter("idCoche", idCoche);
        obj = (Object[]) q.uniqueResult();
        return obj;
    }    
    
    public void FinalizarPensiones(Date fecha)
    {
        try {
            Query q = session.createQuery("FROM ServicioPension sp, Coches c, Clientes cl, Pensiones p, Auditoria au WHERE sp.idCoche = c.idCoche AND c.idCliente = cl.idCliente AND sp.idPension = p.id AND au.idServicio=sp.idServicio AND sp.activa = true AND au.fecha2 < :fecha");
            q.setParameter("fecha", fecha);
            List<Object> ls = q.list();
            if(!ls.isEmpty()) {
                for(Object Obj : ls) {
                    Object[] obj = (Object[]) Obj;
                    ServicioPension sp = (ServicioPension) obj[0];
                    Coches c = (Coches) obj[1];
                    Clientes cl = (Clientes) obj[2];
                    Pensiones p = (Pensiones) obj[3];
                    sp.setActiva(false);
                    updateObject(sp);
                    JOptionPane.showMessageDialog(null, "A finalizado "+p.getNombre()+" el cliente "+cl.getNombre()+" con el coche "+c.getModelo());
                }
            } else {
                System.out.println("No se encontraron pensiones");
            }
        } catch(HibernateException e) {
            JOptionPane.showMessageDialog(null,"error al verificar fechas de pensiones\n"+e.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
        
}
