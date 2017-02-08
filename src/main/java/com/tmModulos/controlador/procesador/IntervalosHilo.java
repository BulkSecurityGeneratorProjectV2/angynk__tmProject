package com.tmModulos.controlador.procesador;

import com.tmModulos.controlador.servicios.HorariosProvisionalServicio;
import com.tmModulos.controlador.servicios.TablaHorarioService;
import com.tmModulos.modelo.dao.tmData.TiempoIntervalosAuxiliarDao;
import com.tmModulos.modelo.dao.tmData.TiempoIntervalosDao;
import com.tmModulos.modelo.entity.saeBogota.Horario;
import com.tmModulos.modelo.entity.tmData.GisIntervalos;
import com.tmModulos.modelo.entity.tmData.IntervalosProgramacion;
import com.tmModulos.modelo.entity.tmData.ServicioTipoDia;
import com.tmModulos.modelo.entity.tmData.TiempoIntervalos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.List;


public class IntervalosHilo  implements Runnable{

    List<TiempoIntervalos> tiempoIntervalos;



    public IntervalosHilo( List<TiempoIntervalos> tiempoIntervalos) {
        this.tiempoIntervalos =tiempoIntervalos;
    }


    @Override
    public void run() {
        System.out.println( " is running");
        TiempoIntervalosAuxiliarDao auxiliarDao = new TiempoIntervalosAuxiliarDao();
        auxiliarDao.addTiempoIntervalos(tiempoIntervalos);
        return;
    }



}
