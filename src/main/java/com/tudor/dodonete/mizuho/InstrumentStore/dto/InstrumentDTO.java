package com.tudor.dodonete.mizuho.InstrumentStore.dto;

import lombok.Data;

@Data
public class InstrumentDTO {
    private long instrumentId;
    private String instrumentName;

    public InstrumentDTO() {
    }

    public InstrumentDTO(long instrumentId, String instrumentName) {
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
    }
}
