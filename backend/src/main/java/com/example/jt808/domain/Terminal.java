package com.example.jt808.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vehicle_terminal")
public class Terminal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sim_no", unique = true, nullable = false, length = 20)
    private String simNo;

    @Column(name = "plate_no", length = 20)
    private String plateNo;

    @Column(name = "device_model", length = 64)
    private String deviceModel;

    @Column(name = "manufacturer_id", length = 16)
    private String manufacturerId;

    @Column(name = "protocol_type", length = 16)
    private String protocolType;
}
