package com.example.jt808.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "raw_message")
public class RawMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "protocol", nullable = false, length = 16)
    private String protocol;

    @Column(name = "message_id", length = 16)
    private String messageId;

    @Column(name = "sim_no", length = 20)
    private String simNo;

    @Column(name = "hex_payload", nullable = false, columnDefinition = "TEXT")
    private String hexPayload;

    @Column(name = "json_payload", nullable = false, columnDefinition = "TEXT")
    private String jsonPayload;
}
