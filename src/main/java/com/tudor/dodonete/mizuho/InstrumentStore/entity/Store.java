package com.tudor.dodonete.mizuho.InstrumentStore.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "STORE")
@SequenceGenerator(name = "SQ_STORE")
public class Store {
    @Id
    @Column(name = "STORE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_STORE")
    private Long storeId;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "instrument_id", nullable = false)
    private Instrument instrument;

    @Column(name = "PRICE")
    @Digits(integer = 15, fraction = 3)
    private BigDecimal price;

    @Column(name = "ENTRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date entryDate;

}
