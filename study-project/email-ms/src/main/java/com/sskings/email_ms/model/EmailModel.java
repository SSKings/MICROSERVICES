package com.sskings.email_ms.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.sskings.email_ms.enums.StatusEmail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;
import lombok.Data;


@Data
@Entity
public class EmailModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;

    @PrePersist         
    public void prePersist() {
        setSendDateEmail(LocalDateTime.now());
    }
    
}
