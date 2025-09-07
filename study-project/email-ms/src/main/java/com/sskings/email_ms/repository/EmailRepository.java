package com.sskings.email_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sskings.email_ms.model.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel,Long>{
    
}
