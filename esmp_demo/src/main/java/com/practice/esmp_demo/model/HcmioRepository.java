package com.practice.esmp_demo.model;

import com.practice.esmp_demo.model.entity.Hcmio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HcmioRepository extends JpaRepository<Hcmio, Integer> {
}