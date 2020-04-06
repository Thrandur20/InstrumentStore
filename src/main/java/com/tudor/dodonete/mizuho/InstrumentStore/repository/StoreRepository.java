package com.tudor.dodonete.mizuho.InstrumentStore.repository;

import com.tudor.dodonete.mizuho.InstrumentStore.entity.Instrument;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s " +
            "JOIN s.vendor v " +
            "WHERE v.vendorId = ?1 " +
            "AND s.entryDate > ?2")
    List<Store> findAllRecentByVendorId(long vendorId, Date startDate);

    @Query("SELECT s FROM Store s " +
            "JOIN s.instrument v " +
            "WHERE v.instrumentId = ?1 " +
            "AND s.entryDate > ?2")
    List<Store> findAllRecentByInstrumentId(long instrumentId, Date startDate);
}
