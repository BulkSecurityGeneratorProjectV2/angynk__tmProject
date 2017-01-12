package com.journaldev.hibernate.data.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="nodo")
public class Nodo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="NodoGenerator")
    @SequenceGenerator(name="NodoGenerator", sequenceName = "nodo_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigo")
    private Integer codigo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nodoInicial")
    private Set<ArcoTiempo> arcoTiempoNodoInicialRecords = new HashSet<ArcoTiempo>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nodoFinal")
    private Set<ArcoTiempo> arcoTiempoNodoFinalRecords = new HashSet<ArcoTiempo>(0);

    public Nodo() {
    }

    public Nodo(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Set<ArcoTiempo> getArcoTiempoNodoInicialRecords() {
        return arcoTiempoNodoInicialRecords;
    }

    public void setArcoTiempoNodoInicialRecords(Set<ArcoTiempo> arcoTiempoNodoInicialRecords) {
        this.arcoTiempoNodoInicialRecords = arcoTiempoNodoInicialRecords;
    }

    public Set<ArcoTiempo> getArcoTiempoNodoFinalRecords() {
        return arcoTiempoNodoFinalRecords;
    }

    public void setArcoTiempoNodoFinalRecords(Set<ArcoTiempo> arcoTiempoNodoFinalRecords) {
        this.arcoTiempoNodoFinalRecords = arcoTiempoNodoFinalRecords;
    }
}
