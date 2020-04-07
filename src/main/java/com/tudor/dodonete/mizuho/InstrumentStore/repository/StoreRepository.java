package com.tudor.dodonete.mizuho.InstrumentStore.repository;

import com.tudor.dodonete.mizuho.InstrumentStore.entity.Instrument;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Store;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s " +
            "JOIN s.vendor v " +
            "WHERE v.vendorId = ?1 " +
            "AND s.entryDate > ?2")
    List<Store> findAllRecentByVendorId(long vendorId, Date startDate);

    List<Store> findAllByVendor(Vendor vendor);

    @Query("SELECT s FROM Store s " +
            "JOIN s.instrument v " +
            "WHERE v.instrumentId = ?1 " +
            "AND s.entryDate > ?2")
    List<Store> findAllRecentByInstrumentId(long instrumentId, Date startDate);

    List<Store> findAllByInstrument(Instrument instrument);

    Optional<Store> findOneByInstrumentAndVendor(Instrument instrument, Vendor vendor);
}
