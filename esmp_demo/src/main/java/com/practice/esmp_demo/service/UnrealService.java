package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.AddHcmioAndTcnud;
import com.practice.esmp_demo.controller.dto.Search;
import com.practice.esmp_demo.controller.dto.response.UnrealResponse;
import com.practice.esmp_demo.model.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnrealService {
    @Autowired
    TestRepository testRepository;
    @Autowired
    HcmioService hcmioService;
    @Autowired
    TcnudService tcnudService;

    public LinkedHashMap unrealDetail(Search request) {
        try {
            List<Map> detailList;

            if (request.getStock() != null) {
                // 情況一 : 有股票
                detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq(), request.getStock());
            } else {
                // 情況二 : 沒股票
                detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq());
            }

            if (detailList != null) {
                // 情況一 : 有資料
                // 將資料(DetailList)送去整理成(ResultList)
                List resultList = setDetailList(detailList);
                return UnrealResponse.setUnrealResponse(resultList, "000", "");
            } else {
                // 情況二 : 沒資料
                return UnrealResponse.setUnrealResponse(null, "001", "查無結果");
            }
        } catch (Exception e) {
            return UnrealResponse.setUnrealResponse(null, "005", "伺服器忙碌中，請稍後嘗試");
        }

    }

    public LinkedHashMap unrealSum(Search request) {
        try {
            List<Map> sumList;
            List<Map> detailList;
            List resultList = new ArrayList<>();

            if (request.getStock() != null) {
                // 情況一 : 有股票
                sumList = testRepository.getUnrealSum(request.getBranchNo(), request.getCustSeq(), request.getStock());
                detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq(), request.getStock());
                // 判斷有沒有資料
                if (sumList != null) {
                    resultList = setSumList(sumList, detailList);
                } else {
                    return UnrealResponse.setUnrealResponse(null, "001", "查無結果");
                }
            } else {
                // 情況二 : 沒股票
                // 先找出客戶所持有的哪些股票
                // 依序取得單一股票的總滙，合成一個List
                List<String> stockList = testRepository.getCustAllStock(request.getBranchNo(), request.getCustSeq());
                // 判斷有沒有資料
                if (stockList != null) {
                    for (String stock : stockList) {
                        sumList = testRepository.getUnrealSum(request.getBranchNo(), request.getCustSeq(), stock);
                        detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq(), stock);
                        resultList.add(setSumList(sumList, detailList));
                    }
                } else {
                    return UnrealResponse.setUnrealResponse(null, "001", "查無結果");
                }
            }
            return UnrealResponse.setUnrealResponse(resultList, "000", "");

        } catch (Exception e) {
            return UnrealResponse.setUnrealResponse(null, "005", "伺服器忙碌中，請稍後嘗試");
        }
    }

    @Transactional
    public LinkedHashMap unrealAdd(AddHcmioAndTcnud request) throws Exception {
        try {
            List<Map> check = testRepository.getUnrealAddDetail(request.getBranchNo(), request.getCustSeq(), request.getTradeDate(), request.getDocSeq());
            if (check.size()==0) {
                boolean addHcmio = hcmioService.createHcmio(request);
                boolean addTcnud = tcnudService.createTcnud(request);
                ArrayList<LinkedHashMap> resultList;
                // 判斷是否成功新增，失敗丟出 Exception 來 rollback
                if (addHcmio && addTcnud) {
                    List<Map> detailList = testRepository.getUnrealAddDetail(request.getBranchNo(), request.getCustSeq(), request.getTradeDate(), request.getDocSeq());
                    resultList = setDetailList(detailList);
                } else {
                    throw new Exception();
                }
                return UnrealResponse.setUnrealResponse(resultList, "000", "");
            } else {
                return UnrealResponse.setUnrealResponse(null, "002", "已有資料，新增失敗");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception();
        }
    }


    public ArrayList<LinkedHashMap> setSumList(List<Map> sumList, List<Map> detailList) {
        ArrayList<LinkedHashMap> resultList = new ArrayList<>();
        for (Map res : sumList) {
            LinkedHashMap sum = new LinkedHashMap();
            sum.put("stock", res.get("Stock"));
            sum.put("stockName", res.get("stockName"));
            sum.put("nowPrice", res.get("nowPrice"));
            sum.put("sumRemainQty", res.get("sumRemainQty"));
            sum.put("sumFee", res.get("sumFee"));
            sum.put("sumCost", res.get("sumCost"));
            sum.put("sumMarketValue", res.get("sumMarketValue"));
            sum.put("sumUnrealProfit", res.get("sumUnrealProfit"));
            sum.put("detailList", setDetailList(detailList));
            resultList.add(sum);
        }
        return resultList;
    }

    public ArrayList<LinkedHashMap> setDetailList(List<Map> detailList) {
        ArrayList<LinkedHashMap> resultList = new ArrayList();
        for (Map res : detailList) {
            LinkedHashMap detail = new LinkedHashMap();
            detail.put("tradeDate", res.get("TradeDate"));
            detail.put("docSeq", res.get("DocSeq"));
            detail.put("stock", res.get("Stock"));
            detail.put("stockName", res.get("StockName"));
            detail.put("buyPrice", res.get("BuyPrice"));
            detail.put("nowPrice", res.get("NowPrice"));
            detail.put("qty", res.get("Qty"));
            detail.put("remainQty", res.get("RemainQty"));
            detail.put("fee", res.get("Fee"));
            detail.put("cost", res.get("Cost"));
            detail.put("marketValue", res.get("MarketValue"));
            detail.put("unrealProfit", res.get("UnrealProfit"));
            resultList.add(detail);
        }
        return resultList;
    }

}
