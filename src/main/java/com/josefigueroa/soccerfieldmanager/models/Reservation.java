package com.josefigueroa.soccerfieldmanager.models;

import java.io.Serializable;
import java.sql.Date;

import com.josefigueroa.soccerfieldmanager.utils.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="reservation")
public class Reservation implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    private Date starTime;
    private Date endTime;
    private String payment;
    
    // objeto de Status que es un enum
    private Status status;

    @ManyToOne// por defecto tiene el fetch eager que toma todos los datos rapido
    private User user;
 
    @ManyToOne
    private SoccerField soccerField;

    // fetch eager toma todos los atributos
    // fetch lazy toma solo el id

}
