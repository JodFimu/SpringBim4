package com.josefigueroa.soccerfieldmanager.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data

@Table(name="soccerfield")
public class SoccerField implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSoccerfield;

    @Column(unique = true)
    private String fieldName;
    private String capacity;
    private String photo;



}
