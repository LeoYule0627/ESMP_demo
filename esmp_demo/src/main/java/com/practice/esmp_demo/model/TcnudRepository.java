package com.practice.esmp_demo.model;

import com.practice.esmp_demo.model.entity.Tcnud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TcnudRepository extends JpaRepository<Tcnud, Integer> {

    @Query(value = "SELECT * FROM tcnud \n" +
            "WHERE BranchNo=?1 AND CustSeq=?2 AND Stock=?3 Order BY ModDate DESC, ModTime DESC LIMIT 1",nativeQuery = true)
    Tcnud findByBranchNoAndCustSeqAndStock(String branchNo, String custSeq, String stock);
}
