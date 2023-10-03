package com.alfish.arealmvp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "statue", schema = "public")
@Getter
@Setter
@ToString
public class Statue {

    @Id
    @Column(name = "statue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statueId;

    @Column(name = "statue_name")
    private String statueName;

    @Column(name = "views")
    private Integer views;

    @Column(name = "interactions")
    private Integer interactions;

    @Column(name = "writes")
    private Integer writes;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "altitude")
    private Double altitude;

    @Column(name = "degree")
    private Double degree;

}
