package com.estacionamiento5.repository;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class Repository<T> {
    
    public static Session session = null;//static para quien la herede tenga la session actual y no null
    private Transaction tr;
    
    private static Repository repo;
    private LotesRepository lotesRepo = null;
    private HisLotesRepository hisLotesRepo = null;
    private CochesRepository cochesRepo = null;
    private PensionesRepository pensionesRepo = null;
    private BaniosRepository baniosRepo = null;
    private UsuariosRepository usuariosRepo = null;
    //singleton
    public Repository(){}
    public static Repository getInstance(){
        if(repo == null) repo = new Repository();
        return repo;
    }
    
    public LotesRepository getLotesRepository(){
	if(this.lotesRepo == null) this.lotesRepo = new LotesRepository();
	return this.lotesRepo;
    }
    
    public HisLotesRepository getHisLotesRepository(){
	if(this.hisLotesRepo == null) this.hisLotesRepo = new HisLotesRepository();
	return this.hisLotesRepo;
    }
    
    public CochesRepository getCochesRepository(){
	if(this.cochesRepo == null) this.cochesRepo = new CochesRepository();
	return this.cochesRepo;
    }
    
    public PensionesRepository getPensionesRepository(){
	if(this.pensionesRepo == null) this.pensionesRepo = new PensionesRepository();
	return this.pensionesRepo;
    }
    
    public BaniosRepository getBaniosRepository(){
	if(this.baniosRepo == null) this.baniosRepo = new BaniosRepository();
	return this.baniosRepo;
    }
    
    public UsuariosRepository getUsuariosRepository(){
	if(this.usuariosRepo == null) this.usuariosRepo = new UsuariosRepository();
	return this.usuariosRepo;
    }
    
    
    
    public void openSession() { session = HibernateUtil.getSessionFactory().openSession(); }
    
    public void openTransaction() { tr = session.beginTransaction(); }
    
    public void commit() { tr.commit(); }
    
    public void rollback() { tr.rollback(); }
        
    public void addObject(Object Obj) { session.save(Obj); }
    
    public void updateObject(Object Obj) { session.update(Obj); }
    
    public void deleteObject(Object Obj) { session.delete(Obj); }
    
    
    
    public Object getObject(Class clase, String attribute, Object obj)
    {
        Object object = session.createCriteria(clase).add(Restrictions.eq(attribute, obj)).uniqueResult();
        return object;
    }
    
    public ArrayList<Object> getObjectAsc(Class Obj, String attribute)
    {
        ArrayList<Object> obj;
        List<Object> ls = session.createCriteria(Obj).addOrder(Order.asc(attribute)).list();
        obj = (ArrayList) ls;
        return obj;
    }
    
    public ArrayList<Object> getObjectDesc(Class Obj, String attribute)
    {
        ArrayList<Object> obj;
        List<Object> ls = session.createCriteria(Obj).addOrder(Order.desc(attribute)).list();
        obj = (ArrayList) ls;        
        return obj;
    }
    
    public ArrayList<Object> searchObjectLike(Class obj, String attribute, String value)
    {
        ArrayList<Object> listObj;
        List<Object> list = session.createCriteria(obj).add(Restrictions.like(attribute,"%"+value+"%")).list();
        listObj = (ArrayList) list;
        return listObj;
    }
    
    public ArrayList<Object> searchObjectByDate(String model, String attribute, String orderAttribute, Object date1, Object date2)
    {
        ArrayList<Object> search;
        Query q = session.createQuery("from "+model+" where SUBSTRING_INDEX(SUBSTRING_INDEX("+attribute+",' ', 1),' ',-1) >= :date1 and SUBSTRING_INDEX(SUBSTRING_INDEX("+attribute+",' ', 1),' ',-1) <= :date2 order by "+orderAttribute+" desc");
        q.setParameter("date1", date1);
        q.setParameter("date2", date2);
        List<Object> ls = q.list();
        search = (ArrayList) ls;
        return search;
    }

}
