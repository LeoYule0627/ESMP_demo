package com.practice.esmp_demo.model;

import com.practice.esmp_demo.model.entity.Tcnud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TcnudRepository extends JpaRepository<Tcnud, Integer> {
}
