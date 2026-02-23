package com.example.jt808.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "vehicle_location")
public class LocationRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sim_no", nullable = false, length = 20)
    private String simNo;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "speed_kmh")
    private Integer speedKmh;

    @Column(name = "direction_deg")
    private Integer directionDeg;

    @Column(name = "gps_time")
    private OffsetDateTime gpsTime;

    @Column(name = "alarm_flags", length = 64)
    private String alarmFlags;

    @Column(name = "status_flags", length = 64)
    private String statusFlags;
}
