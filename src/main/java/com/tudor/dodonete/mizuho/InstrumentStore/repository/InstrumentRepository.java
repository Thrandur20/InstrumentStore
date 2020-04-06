package com.tudor.dodonete.mizuho.InstrumentStore.repository;

import com.tudor.dodonete.mizuho.InstrumentStore.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    @Transactional
    Optional<Instrument> findOneByInstrumentName(String instrumentName);
}
