package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.request.AddHcmioAndTcnud;
import com.practice.esmp_demo.controller.dto.request.Search;
import com.practice.esmp_demo.controller.dto.request.SearchDeliveryFee;
import com.practice.esmp_demo.controller.dto.response.UnrealResponse;
import com.practice.esmp_demo.model.TestRepository;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UnrealService {
    @Autowired
    TestRepository testRepository;
    @Autowired
    HcmioService hcmioService;
    @Autowired
    TcnudService tcnudService;

    @Autowired
    Calculate calculate;

    public LinkedHashMap unrealDetail(Search request) {
        try {
            List<Map> detailList;
            List resultList = new ArrayList();

            if (request.getStock() != null && request.getStock() != "") {
                // 情況一 : 有股票
                detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq(), request.getStock());
            } else {
                // 情況二 : 沒股票
                detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq());
            }

            if (detailList.size() != 0) {
                // 情況一之一 : 有資料，有獲利區間
                if (request.getMax() != 0 || request.getMin() != 0) {
                    for (int i = 0; i < detailList.size(); i++) {
                        Double profitRate = Double.parseDouble(detailList.get(i).get("ProfitRate").toString());
                        if (request.getMin() < profitRate && profitRate < request.getMax()) {
                            resultList.add(detailList.get(i));
                        }
                    }
                    // 將資料(DetailList)送去整理成(ResultList)
                    resultList = setDetailList(resultList);
                } else {
                    // 情況一之二 : 有資料，沒有獲利區間
                    // 將資料(DetailList)送去整理成(ResultList)
                    resultList = setDetailList(detailList);
                }
                // 查詢獲利區間後，沒有資料
                if (resultList.size() == 0) {
                    return UnrealResponse.unrealResponse(null, "001", "查無結果");
                }
            } else {
                // 情況二 : 沒資料
                return UnrealResponse.unrealResponse(null, "001", "查無結果");
            }
            return UnrealResponse.unrealResponse(resultList, "000", "");
        } catch (Exception e) {
            return UnrealResponse.unrealResponse(null, "005", "伺服器忙碌中，請稍後嘗試");
        }
    }

    public LinkedHashMap unrealSum(Search request) {
        try {
            List<Map> sumList;
            List<Map> detailList;
            List resultList = new ArrayList<>();

            if (request.getStock() != null && request.getStock() != "") {
                // 情況一 : 有股票
                sumList = testRepository.getUnrealSum(request.getBranchNo(), request.getCustSeq(), request.getStock());
                detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq(), request.getStock());
                // 判斷有沒有資料
                if (sumList.get(0).get("Stock") != null) {
                    // 情況一之一 : 有股票，有獲利率
                    if (request.getMax() != 0 || request.getMin() != 0) {
                        // 檢查獲利率
                        for (int i = 0; i < sumList.size(); i++) {
                            Double profitRate = Double.parseDouble(sumList.get(i).get("SumProfitRate").toString());
                            if (request.getMin() < profitRate && profitRate < request.getMax()) {
                                // 符合條件留下
                                resultList.add(setSumList(sumList, detailList));
                            }
                        }
                        // 查詢獲利區間後，沒有資料
                        if (resultList.size() == 0) {
                            return UnrealResponse.unrealResponse(null, "001", "查無結果");
                        }
                    }
                    // 情況一之二 : 有股票，沒有獲利率
                    else {
                        resultList.add(setSumList(sumList, detailList));
                    }
                } else {
                    return UnrealResponse.unrealResponse(null, "001", "查無結果");
                }
                return UnrealResponse.unrealResponse(resultList, "000", "");

            } else {
                // 情況二 : 沒股票
                // 先找出客戶所持有的哪些股票
                // 依序取得單一股票的總滙，合成一個List
                List<String> stockList = testRepository.getCustAllStock(request.getBranchNo(), request.getCustSeq());
                // 判斷有沒有資料
                if (stockList.size() != 0) {
                    for (String stock : stockList) {
                        sumList = testRepository.getUnrealSum(request.getBranchNo(), request.getCustSeq(), stock);
                        detailList = testRepository.getUnrealDetail(request.getBranchNo(), request.getCustSeq(), stock);

                        // 情況二之一 : 沒股票，有獲利率
                        if (request.getMax() != 0 || request.getMin() != 0) {
                            // 檢查獲利率
                            for (int i = 0; i < sumList.size(); i++) {
                                Double profitRate = Double.parseDouble(sumList.get(i).get("SumProfitRate").toString());
                                if (request.getMin() < profitRate && profitRate < request.getMax()) {
                                    // 符合條件留下
                                    resultList.add(setSumList(sumList, detailList));
                                }
                            }
                        }
                        // 情況二之二 : 沒股票，沒有獲利率
                        else {
                            resultList.add(setSumList(sumList, detailList));
                        }
                    }
                    // 查詢獲利區間後，沒有資料
                    if (resultList.size() == 0) {
                        return UnrealResponse.unrealResponse(null, "001", "查無結果");
                    }
                } else {
                    return UnrealResponse.unrealResponse(null, "001", "查無結果");
                }
                return UnrealResponse.unrealResponse(resultList, "000", "");
            }
        } catch (Exception e) {
            return UnrealResponse.unrealResponse(null, "005", "伺服器忙碌中，請稍後嘗試");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public LinkedHashMap unrealAdd(AddHcmioAndTcnud request) throws Exception {
        try {
            List<Map> check = testRepository.getUnrealAddDetail(request.getBranchNo(), request.getCustSeq(), request.getTradeDate(), request.getDocSeq());
            if (check.size() == 0) {
                boolean addHcmio = hcmioService.createHcmio(request);
                boolean addTcnud = tcnudService.createTcnud(request);
                List<LinkedHashMap> resultList;
                // 判斷是否成功新增，失敗丟出 Exception 來 rollback
                if (addHcmio && addTcnud) {
                    List<Map> detailList = testRepository.getUnrealAddDetail(request.getBranchNo(), request.getCustSeq(), request.getTradeDate(), request.getDocSeq());
                    resultList = setDetailList(detailList);
                } else {
                    throw new Exception();
                }
                return UnrealResponse.unrealResponse(resultList, "000", "");
            } else {
                return UnrealResponse.unrealResponse(null, "002", "已有資料，新增失敗");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception();
        }
    }

    public LinkedHashMap getDeliveryFee(SearchDeliveryFee request) {
        int count = 2; // T + 2
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();

//        測試用: 可自訂日期
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//        Date date = df.parse("20220913");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);

        System.out.println("today: " + dateFormat.format(calendar.getTime()));
        String today = this.testRepository.findByDay(dateFormat.format(calendar.getTime()));  // 取得今日是否為假日

        if (today.equals("Y")) {
            return UnrealResponse.deliveryFeeResponse(0, null, "000", "今天是假日");
        } else {
            calendar.add(Calendar.DATE, -1);
            String tradeDate = dateFormat.format(calendar.getTime());  // 取得要查詢的交易日
            while (count != 0) {
                String isWeekend = this.testRepository.findByDay(tradeDate);  // 取得交易日是否為休息天
                System.out.println("for " + tradeDate + " isWeekend? " + isWeekend);

                if (isWeekend.equals("N")) {
                    count -= 1;
                    if (count == 0) break;
                }

                calendar.add(Calendar.DATE, -1);
                tradeDate = dateFormat.format(calendar.getTime());

            }
            System.out.println("Search: " + tradeDate);
            List<Map> detailList = this.testRepository.getDeliveryFeeDetail(request.getBranchNo(), request.getCustSeq(), tradeDate);
            Map sumDeliveryFee = this.testRepository.getDeliveryFeeSum(request.getBranchNo(), request.getCustSeq(), tradeDate);
            List<LinkedHashMap> resultList = setDetailList(detailList);
            return UnrealResponse.deliveryFeeResponse(sumDeliveryFee.get("sum"), resultList, "000", "");
        }
    }

    public LinkedHashMap setSumList(List<Map> sumList, List<Map> detailList) {
        LinkedHashMap sumMap = new LinkedHashMap();
        for (Map sum : sumList) {
            sumMap.put("stock", sum.get("Stock"));
            sumMap.put("stockName", sum.get("stockName"));
            sumMap.put("nowPrice", sum.get("nowPrice"));
            sumMap.put("sumRemainQty", sum.get("sumRemainQty"));
            sumMap.put("sumFee", sum.get("sumFee"));
            sumMap.put("sumCost", sum.get("sumCost"));
            sumMap.put("sumMarketValue", sum.get("sumMarketValue"));
            sumMap.put("sumUnrealProfit", sum.get("sumUnrealProfit"));
            sumMap.put("sumProfitRate", sum.get("SumProfitRate") + "%");
            sumMap.put("detailList", setDetailList(detailList));
        }
        return sumMap;
    }

    public List<LinkedHashMap> setDetailList(List<Map> detailList) {
        List<LinkedHashMap> resultList = new ArrayList<>();
        for (Map detail : detailList) {
            LinkedHashMap detailMap = new LinkedHashMap();
            detailMap.put("tradeDate", detail.get("TradeDate"));
            detailMap.put("docSeq", detail.get("DocSeq"));
            detailMap.put("stock", detail.get("Stock"));
            detailMap.put("stockName", detail.get("StockName"));
            detailMap.put("buyPrice", detail.get("BuyPrice"));
            detailMap.put("nowPrice", detail.get("NowPrice"));
            detailMap.put("qty", detail.get("Qty"));
            detailMap.put("remainQty", detail.get("RemainQty"));
            detailMap.put("fee", detail.get("Fee"));
            detailMap.put("cost", detail.get("Cost"));
            detailMap.put("marketValue", detail.get("MarketValue"));
            detailMap.put("unrealProfit", detail.get("UnrealProfit"));
            detailMap.put("profitRate", detail.get("ProfitRate") + "%");
            resultList.add(detailMap);
        }
        return resultList;
    }

}
