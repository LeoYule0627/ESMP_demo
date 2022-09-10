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
    Calculate calculate;
    @Autowired
    HcmioService hcmioService;
    @Autowired
    TcnudService tcnudService;

    public LinkedHashMap unrealDetail(Search request) {
        List<Map> detailList;
        if (request.getStock() != null) {
            detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq(), request.getStock());
        } else {
            detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq());
        }
        List resultList = setDetailList(detailList);
        return UnrealResponse.setUnrealResponse(resultList, "000", "成功");
    }

    public LinkedHashMap unrealSum(Search request) {
        List<Map> sumList;
        List<Map> detailList;
        List resultList = new ArrayList<>();
        if (request.getStock() != null) {
            sumList = testRepository.getUnrealSum(request.getBranchNo(), request.getCustSeq(), request.getStock());
            detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq(), request.getStock());
            resultList = setSumList(sumList, detailList);
        } else {
            List<String> stockList = testRepository.getCustAllStock(request.getBranchNo(), request.getCustSeq());
            for (String stock : stockList) {
                sumList = testRepository.getUnrealSum(request.getBranchNo(), request.getCustSeq(), stock);
                detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq(), stock);
                resultList.add(setSumList(sumList, detailList));
            }
        }
        return UnrealResponse.setUnrealResponse(resultList, "000", "成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public LinkedHashMap unrealAdd(AddHcmioAndTcnud request) throws Exception {
        boolean addHcmio = hcmioService.createHcmio(request);
        boolean addTcnud = tcnudService.createTcnud(request);
        ArrayList<LinkedHashMap> resultList = new ArrayList<>();
        if (addHcmio && addTcnud) {
            List<Map> detailList = testRepository.getUnrealAddDetail(request.getBranchNo(), request.getCustSeq(), request.getTradeDate(), request.getDocSeq());
            resultList = setDetailList(detailList);
        } else {
            throw new Exception();
        }
        return UnrealResponse.setUnrealResponse(resultList, "000", "成功");
    }


    public ArrayList<LinkedHashMap> setSumList(List<Map> sumList, List<Map> detailList) {
        ArrayList<LinkedHashMap> resultList = new ArrayList<>();
        if (sumList != null && detailList != null) {
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
        } else {
            return resultList;
        }
        return resultList;
    }

    public ArrayList<LinkedHashMap> setDetailList(List<Map> detailList) {
        ArrayList<LinkedHashMap> resultList = new ArrayList();
        if (detailList != null) {
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
        } else {
            return resultList;
        }
        return resultList;
    }

}
