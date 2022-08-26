package com.practice.esmp_demo.model;

import com.practice.esmp_demo.model.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    @Query(value = "SELECT T.BranchNo,T.CustSeq,T.Stock,T.RemainQty,T.Cost,M.StockName,M.CurPrice,M.Currency,(T.RemainQty*M.CurPrice) AS Cap, ROUND((T.RemainQty*M.CurPrice)-((T.RemainQty*M.CurPrice)*0.001425)-((T.RemainQty*M.CurPrice)*0.003)-T.Cost,4) AS Ugl " +
            "FROM tcnud AS T inner join mstmb AS M ON T.Stock=M.Stock"
            , nativeQuery = true)
    List<Map<String, String>> getAll();
}
