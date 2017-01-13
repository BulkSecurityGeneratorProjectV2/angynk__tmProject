package com.journaldev.spring.service;

import com.journaldev.hibernate.data.dao.*;
import com.journaldev.hibernate.data.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GisCargaService {

    @Autowired
    GisCargaDao gisCargaDao;

    @Autowired
    TrayectoDao trayectoDao;

    @Autowired
    ArcoTiempoDao arcoTiempoDao;


    @Transactional(readOnly = false)
    public void addGisCarga(GisCarga gisCarga) {
        gisCargaDao.addGisCarga(gisCarga);
    }

    public void deleteGisCarga(GisCarga gisCarga) {
        gisCargaDao.deleteGisCarga(gisCarga);
    }

    public void updateGisCarga(GisCarga gisCarga) {
        gisCargaDao.updateGisCarga(gisCarga);
    }

    public List<GisCarga> getGisCargaAll() {
        return gisCargaDao.getGisCargaAll();
    }
    public List<GisCarga> getGisCargaByFecha(String tipoFecha,Date fecha){ return  gisCargaDao.getGisCargaByFecha( tipoFecha, fecha);}
    public List<GisCarga> getGisCargaBetwenFechas(String tipoFecha,Date fechaIni,Date fechaFin){ return  gisCargaDao.getGisCargaBetwenFechas(tipoFecha,fechaIni,fechaFin);}
    @Transactional(readOnly = false)
    public void addTrayecto(Trayecto trayecto) {
        trayectoDao.addTrayecto(trayecto);
    }

    public void deleteTrayecto(Trayecto trayecto) {
        trayectoDao.deleteTrayecto(trayecto);
    }

    public void updateTrayecto(Trayecto trayecto) {
        trayectoDao.updateTrayecto(trayecto);
    }

    public List<Trayecto> getTrayectoAll() { return trayectoDao.getTrayectoAll(); }
    public List<Trayecto> getTrayectoByIdentifier(String trayectoId){ return trayectoDao.getTrayectoByIdentifier(trayectoId);}

    @Transactional(readOnly = false)
    public void addArcoTiempo(ArcoTiempo arcoTiempo) { arcoTiempoDao.addArcoTiempo( arcoTiempo );}

    public void deleteArcoTiempo(ArcoTiempo arcoTiempo) { arcoTiempoDao.deleteArcoTiempo( arcoTiempo );}


    public void updateArcoTiempo(ArcoTiempo arcoTiempo) { arcoTiempoDao.updateArcoTiempo( arcoTiempo );}


    public List<ArcoTiempo> getArcoTiempoAll() { return  arcoTiempoDao.getArcoTiempoAll(); }

    public List<ArcoTiempo> getArcoTiempoByGisCarga(GisCarga gisCarga){ return arcoTiempoDao.getArcoTiempoByGisCarga(gisCarga);}


}
