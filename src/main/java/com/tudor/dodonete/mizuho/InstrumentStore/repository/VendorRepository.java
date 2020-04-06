package com.tudor.dodonete.mizuho.InstrumentStore.repository;

import com.tudor.dodonete.mizuho.InstrumentStore.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("vendorRepository")
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Optional<Vendor> findOneByVendorName(String vendorName);
}
