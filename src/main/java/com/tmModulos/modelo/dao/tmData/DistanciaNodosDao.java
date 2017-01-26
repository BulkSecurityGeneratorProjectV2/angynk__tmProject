package com.tmModulos.modelo.dao.tmData;

import com.tmModulos.modelo.entity.tmData.DistanciaNodos;
import com.tmModulos.modelo.entity.tmData.MatrizDistancia;
import com.tmModulos.modelo.entity.tmData.Nodo;
import com.tmModulos.modelo.entity.tmData.ServicioDistancia;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class DistanciaNodosDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addDistanciaNodos(DistanciaNodos distanciaNodos) {
        Serializable save = getSessionFactory().getCurrentSession().save(distanciaNodos);

    }

    public void deleteDistanciaNodos(DistanciaNodos distanciaNodos) {
        getSessionFactory().getCurrentSession().delete(distanciaNodos);
    }


    public void updateDistanciaNodos(DistanciaNodos distanciaNodos) {
        getSessionFactory().getCurrentSession().update(distanciaNodos);
    }


    public List<DistanciaNodos> getDistanciaNodosAll() {
        List list = getSessionFactory().getCurrentSession().createQuery("from  DistanciaNodos ").list();
        return list;
    }

    public List<DistanciaNodos> getDistanciaNodosByMatriz(MatrizDistancia matrizDistancia){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(DistanciaNodos.class);
        criteria.add(Restrictions.eq("matrizDistancia", matrizDistancia));
        return criteria.list();
    }

    public DistanciaNodos getDistanciaNodosByServicioAndPunto(ServicioDistancia servicioDistancia,Nodo nodo, MatrizDistancia matrizDistancia){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(DistanciaNodos.class);
        criteria.add(Restrictions.eq("nodo", nodo));
        criteria.add(Restrictions.eq("servicioDistancia", servicioDistancia));
        criteria.add(Restrictions.eq("matrizDistancia", matrizDistancia));
        return (DistanciaNodos) criteria.uniqueResult();
    }
}
