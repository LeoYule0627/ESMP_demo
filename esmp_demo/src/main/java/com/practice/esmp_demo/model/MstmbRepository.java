package com.practice.esmp_demo.model;

import com.practice.esmp_demo.model.entity.Mstmb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstmbRepository extends JpaRepository<Mstmb, Integer> {

}
