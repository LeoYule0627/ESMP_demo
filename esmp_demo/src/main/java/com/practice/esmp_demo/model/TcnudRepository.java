package com.practice.esmp_demo.model;

import com.practice.esmp_demo.model.entity.Tcnud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TcnudRepository extends JpaRepository<Tcnud, Integer> {
    @Query(value = "SELECT RemainQty FROM tcnud WHERE TradeDate=?1 AND BranchNo=?2 AND CustSeq=?3 AND Stock=?4 " +
            "Order BY ModDate, ModTime DESC LIMIT 1"
            , nativeQuery = true)
    int findByRemainQty(String tradeDate, String branchNo, String custSeq, String stock);
}
