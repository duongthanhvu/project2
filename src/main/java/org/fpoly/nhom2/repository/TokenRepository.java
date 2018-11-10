package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Token;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TokenRepository
 */
public interface TokenRepository extends JpaRepository<Token,Integer>{

    Token findByToken(String email);
    
}