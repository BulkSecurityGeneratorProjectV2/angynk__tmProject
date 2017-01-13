package com.journaldev.hibernate.data.dao;

import com.journaldev.hibernate.data.entity.ArcoTiempo;
import com.journaldev.hibernate.data.entity.GisCarga;
import com.journaldev.hibernate.data.entity.Nodo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ArcoTiempoDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addArcoTiempo(ArcoTiempo arcoTiempo) {
        getSessionFactory().getCurrentSession().save(arcoTiempo);
    }

    public void deleteArcoTiempo(ArcoTiempo arcoTiempo) {
        getSessionFactory().getCurrentSession().delete(arcoTiempo);
    }


    public void updateArcoTiempo(ArcoTiempo arcoTiempo) {
        getSessionFactory().getCurrentSession().update(arcoTiempo);
    }


    public List<ArcoTiempo> getArcoTiempoAll() {
        List list = getSessionFactory().getCurrentSession().createQuery("from  ArcoTiempo ").list();
        return list;
    }

    public List<ArcoTiempo> getArcoTiempoByGisCarga(GisCarga gisCarga){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ArcoTiempo.class);
        criteria.add(Restrictions.eq("gisCargaArco", gisCarga));
        return criteria.list();
    }
}
