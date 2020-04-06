package com.tudor.dodonete.mizuho.InstrumentStore.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "INSTRUMENT")
@SequenceGenerator(name = "SQ_INSTRUMENT")
public class Instrument {
    @Id
    @Column(name = "INSTRUMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_INSTRUMENT")
    long instrumentId;

    @Column(name = "INSTRUMENT_NAME")
    String instrumentName;

    @OneToMany(mappedBy = "instrument")
    List<Store> storeList;
}
