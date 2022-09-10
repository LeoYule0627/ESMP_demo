package com.practice.esmp_demo.model;

import com.practice.esmp_demo.model.entity.Hcmio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestRepository extends JpaRepository<Hcmio, Integer> {
    @Query(value = "SELECT t.TradeDate, t.DocSeq, t.Stock, m.StockName, CAST(t.Price AS DECIMAL(10,2)) AS BuyPrice, CAST(m.CurPrice AS DECIMAL(10,2)) AS NowPrice, t.Qty, t.RemainQty, t.Fee\n" +
            "\t, CAST(t.Cost AS DECIMAL(16,0)) AS Cost\n" +
            "\t,CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0)) AS MarketValue\n "+
            "\t,CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0)) AS UnrealProfit\n" +
            "FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock\n" +
            "WHERE t.BranchNo=?1 AND t.CustSeq=?2 AND t.Stock=?3", nativeQuery = true)
    List<Map> getUnrealDetail(String branchNo, String custSeq, String stock);

    @Query(value = "SELECT t.TradeDate, t.DocSeq, t.Stock, m.StockName, CAST(t.Price AS DECIMAL(10,2)) AS BuyPrice, CAST(m.CurPrice AS DECIMAL(10,2)) AS NowPrice, t.Qty, t.RemainQty, t.Fee\n" +
            "\t, CAST(t.Cost AS DECIMAL(16,0)) AS Cost\n" +
            "FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock\n" +
            "WHERE t.BranchNo=?1 AND t.CustSeq=?2", nativeQuery = true)
    List<Map> getUnrealDetail(String branchNo, String custSeq);

    @Query(value = "SELECT t.Stock, m.StockName, CAST(m.CurPrice AS DECIMAL(10,2)) AS NowPrice, (SELECT remainQty FROM tcnud WHERE stock=?3 ORDER BY ModDate DESC,ModTime DESC LIMIT 1) AS SumRemainQty, SUM(t.Fee) AS SumFee\n" +
            "\t, SUM(CAST(t.Cost AS DECIMAL(16,0))) AS SumCost\n" +
            "\t, SUM(CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0))) AS SumMarketValue\n" +
            "\t, SUM(CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0))) AS SumUnrealProfit\n" +
            "FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock\n" +
            "WHERE t.BranchNo=?1 AND t.CustSeq=?2 AND t.Stock=?3",nativeQuery = true)
    List<Map> getUnrealSum(String branchNo, String custSeq, String stock);

    @Query(value = "SELECT stock FROM tcnud WHERE BranchNo='F62W' AND CustSeq=02 GROUP BY stock",nativeQuery = true)
    List<String> getCustAllStock(String branchNo, String custSeq);


    @Query(value = "SELECT t.TradeDate, t.DocSeq, t.Stock, m.StockName, CAST(t.Price AS DECIMAL(10,2)) AS BuyPrice, CAST(m.CurPrice AS DECIMAL(10,2)) AS NowPrice, t.Qty, t.RemainQty, t.Fee\n" +
            "\t, CAST(t.Cost AS DECIMAL(16,0)) AS Cost\n" +
            "   FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock\n" +
            "   WHERE t.BranchNo=?1 AND t.CustSeq=?2 AND t.TradeDate=?3 AND t.DocSeq=?4", nativeQuery = true)
    List<Map> getUnrealAddDetail(String branchNo, String custSeq, String tradeDate, String DocSeq);

}






//    SELECT t.TradeDate,t.DocSeq,t.Stock,m.StockName,CAST(t.Price AS DECIMAL(10,2))AS BuyPrice,CAST(m.CurPrice AS DECIMAL(10,2))AS NowPrice,t.Qty,t.RemainQty,t.Fee
//        ,CAST(t.Cost AS DECIMAL(16,0))AS Cost
//        ,CAST((m.CurPrice*t.Qty)AS DECIMAL(16,0))AS MarketValue
//        ,CAST(((m.CurPrice*t.Qty)-ROUND((m.CurPrice*t.Qty)*0.003)-ROUND((m.CurPrice*t.Qty)*0.001425)-t.Cost)AS DECIMAL(16,0))AS UnrealProfit
//        FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock
//        WHERE t.BranchNo='F62W'AND t.CustSeq=02AND t.Stock=2357