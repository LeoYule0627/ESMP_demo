package com.practice.esmp_demo.model;

import com.practice.esmp_demo.model.entity.Hcmio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestRepository extends JpaRepository<Hcmio, Integer> {
    @Query(value = "CALL getDetailList(?1, ?2, ?3)", nativeQuery = true)
    List<Map> getUnrealDetail(String branchNo, String custSeq, String stock);

    @Query(value = "CALL getDetailNoStock(?1, ?2)", nativeQuery = true)
    List<Map> getUnrealDetail(String branchNo, String custSeq);

    @Query(value = "CALL getSumDetailList(?1, ?2, ?3)",nativeQuery = true)
    List<Map> getUnrealSum(String branchNo, String custSeq, String stock);

    @Query(value = "SELECT stock FROM tcnud WHERE BranchNo=?1 AND CustSeq=?2 GROUP BY stock",nativeQuery = true)
    List<String> getCustAllStock(String branchNo, String custSeq);


    @Query(value = "CALL getAddDetailList(?1, ?2, ?3, ?4)", nativeQuery = true)
    List<Map> getUnrealAddDetail(String branchNo, String custSeq, String tradeDate, String DocSeq);

}
