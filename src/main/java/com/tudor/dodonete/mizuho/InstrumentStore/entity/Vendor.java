package com.tudor.dodonete.mizuho.InstrumentStore.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "VENDOR")
@SequenceGenerator(name = "SQ_VENDOR")
public class Vendor {
    @Id
    @Column(name = "VENDOR_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_VENDOR")
    long vendorId;

    @Column(name = "VENDOR_NAME")
    String vendorName;

    @OneToMany(mappedBy = "vendor")
    private List<Store> storeList;


}
