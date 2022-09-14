-- --------------------------------------------------------
-- 主機:                           127.0.0.1
-- 伺服器版本:                        10.8.3-MariaDB - mariadb.org binary distribution
-- 伺服器作業系統:                      Win64
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 傾印 esmp 的資料庫結構
CREATE DATABASE IF NOT EXISTS `esmp` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `esmp`;

-- 傾印  資料表 esmp.calendar 結構
CREATE TABLE IF NOT EXISTS `calendar` (
  `day` char(8) DEFAULT NULL,
  `week` char(3) DEFAULT NULL,
  `type` char(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  esmp.calendar 的資料：~30 rows (近似值)
/*!40000 ALTER TABLE `calendar` DISABLE KEYS */;
REPLACE INTO `calendar` (`day`, `week`, `type`) VALUES
	('20220901', '四', 'N'),
	('20220902', '五', 'N'),
	('20220903', '六', 'Y'),
	('20220904', '日', 'Y'),
	('20220905', '一', 'N'),
	('20220906', '二', 'N'),
	('20220907', '三', 'N'),
	('20220908', '四', 'N'),
	('20220909', '五', 'Y'),
	('20220910', '六', 'Y'),
	('20220911', '日', 'Y'),
	('20220912', '一', 'N'),
	('20220913', '二', 'N'),
	('20220914', '三', 'N'),
	('20220915', '四', 'N'),
	('20220916', '五', 'N'),
	('20220917', '六', 'Y'),
	('20220918', '日', 'Y'),
	('20220919', '一', 'N'),
	('20220920', '二', 'N'),
	('20220921', '三', 'N'),
	('20220922', '四', 'N'),
	('20220923', '五', 'N'),
	('20220924', '六', 'Y'),
	('20220925', '日', 'Y'),
	('20220926', '一', 'N'),
	('20220927', '二', 'N'),
	('20220928', '三', 'N'),
	('20220929', '四', 'N'),
	('20220930', '五', 'N');
/*!40000 ALTER TABLE `calendar` ENABLE KEYS */;

-- 傾印  程序 esmp.getAddDetailList 結構
DELIMITER //
CREATE PROCEDURE `getAddDetailList`(
	IN `branchNoParam` CHAR(50),
	IN `custSeqParam` CHAR(50),
	IN `tradeDateParam` CHAR(50),
	IN `docSeqParam` CHAR(50)
)
    COMMENT 'get unreal add detailList'
BEGIN
SELECT t.TradeDate, t.DocSeq, t.Stock, m.StockName, CAST(t.Price AS DECIMAL(10,2)) AS BuyPrice, CAST(m.CurPrice AS DECIMAL(10,2)) AS NowPrice, t.Qty, t.RemainQty, t.Fee
	, CAST(t.Cost AS DECIMAL(16,0)) AS Cost
	, CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0)) AS MarketValue
	, CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0)) AS UnrealProfit
	, CAST(CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0)) 
		/ CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0)) * 100 AS DECIMAL(10,2)) AS ProfitRate
   FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock
   WHERE t.BranchNo=branchNoParam AND t.CustSeq=custSeqParam AND t.TradeDate=tradeDateParam AND t.DocSeq=docSeqParam;
END//
DELIMITER ;

-- 傾印  程序 esmp.getDeliveryFeeDetail 結構
DELIMITER //
CREATE PROCEDURE `getDeliveryFeeDetail`(
	IN `branchNoParam` CHAR(50),
	IN `custSeqParam` CHAR(50),
	IN `tradeDateParam` CHAR(8)
)
BEGIN
SELECT t.TradeDate, t.DocSeq, t.Stock, m.StockName, CAST(t.Price AS DECIMAL(10,2)) AS BuyPrice, CAST(m.CurPrice AS DECIMAL(10,2)) AS NowPrice, t.Qty, t.RemainQty, t.Fee
	, CAST(t.Cost AS DECIMAL(16,0)) AS Cost
   , CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0)) AS MarketValue
	, CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0)) AS UnrealProfit
	, CAST(CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0)) 
		/ CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0)) * 100 AS DECIMAL(10,2)) AS ProfitRate
FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock
WHERE t.BranchNo=branchNoParam AND t.CustSeq=custSeqParam AND t.TradeDate=tradeDateParam;
END//
DELIMITER ;

-- 傾印  程序 esmp.getDeliveryFeeSum 結構
DELIMITER //
CREATE PROCEDURE `getDeliveryFeeSum`(
	IN `branchNoParam` CHAR(50),
	IN `custSeqParam` CHAR(50),
	IN `tradeDateParam` CHAR(50)
)
BEGIN
SELECT CAST(SUM(t.Cost) AS DECIMAL(16,0))AS `sum`
FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock
WHERE t.BranchNo=branchNoParam AND t.CustSeq=custSeqParam AND t.TradeDate=tradeDateParam;
END//
DELIMITER ;

-- 傾印  程序 esmp.getDetailList 結構
DELIMITER //
CREATE PROCEDURE `getDetailList`(
	IN `branchNoParam` CHAR(50),
	IN `custSeqParam` CHAR(50),
	IN `stockParam` CHAR(50)
)
    COMMENT 'UnrealDetail have Stock'
BEGIN
SELECT t.TradeDate, t.DocSeq, t.Stock, m.StockName, CAST(t.Price AS DECIMAL(10,2)) AS BuyPrice, CAST(m.CurPrice AS DECIMAL(10,2)) AS NowPrice, t.Qty, t.RemainQty, t.Fee
	, CAST(t.Cost AS DECIMAL(16,0)) AS Cost
   , CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0)) AS MarketValue
	, CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0)) AS UnrealProfit
	, CAST(CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0)) 
		/ CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0)) * 100 AS DECIMAL(10,2)) AS ProfitRate
FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock
WHERE t.BranchNo=branchNoParam AND t.CustSeq=custSeqParam AND t.Stock=stockParam;
END//
DELIMITER ;

-- 傾印  程序 esmp.getDetailNoStock 結構
DELIMITER //
CREATE PROCEDURE `getDetailNoStock`(
	IN `branchNoParam` CHAR(50),
	IN `custSeqParam` CHAR(50)
)
    COMMENT 'UnrealDetail No Stock'
BEGIN
SELECT t.TradeDate, t.DocSeq, t.Stock, m.StockName, CAST(t.Price AS DECIMAL(10,2)) AS BuyPrice, CAST(m.CurPrice AS DECIMAL(10,2)) AS NowPrice, t.Qty, t.RemainQty, t.Fee
	, CAST(t.Cost AS DECIMAL(16,0)) AS Cost
	, CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0)) AS MarketValue
	, CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0)) AS UnrealProfit
	, CAST(CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0)) 
		/ CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0)) * 100 AS DECIMAL(10,2)) AS ProfitRate
FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock
WHERE t.BranchNo=branchNoParam AND t.CustSeq=custSeqParam;
END//
DELIMITER ;

-- 傾印  程序 esmp.getSumDetailList 結構
DELIMITER //
CREATE PROCEDURE `getSumDetailList`(
	IN `branchNoParam` CHAR(50),
	IN `custSeqParam` CHAR(50),
	IN `stockParam` CHAR(50)
)
    COMMENT 'UnrealSumList'
BEGIN
SELECT t.Stock, m.StockName, CAST(m.CurPrice AS DECIMAL(10,2)) AS NowPrice
	, SUM(t.RemainQty) AS SumRemainQty, SUM(t.Fee) AS SumFee
	, SUM(CAST(t.Cost AS DECIMAL(16,0))) AS SumCost
   , SUM(CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0))) AS SumMarketValue
   , SUM(CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0))) AS SumUnrealProfit
  	, CAST(SUM(CAST(((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) - t.Cost) AS DECIMAL(16,0)))
		/ SUM(CAST((m.CurPrice * t.Qty) - ROUND((m.CurPrice * t.Qty) * 0.003) - ROUND((m.CurPrice * t.Qty) * 0.001425) AS DECIMAL(16,0))) *100 AS DECIMAL(10,2)) AS SumProfitRate
FROM tcnud AS t INNER JOIN mstmb AS m ON t.Stock=m.Stock
WHERE t.BranchNo=branchNoParam AND t.CustSeq=custSeqParam AND t.Stock=stockParam;
END//
DELIMITER ;

-- 傾印  資料表 esmp.hcmio 結構
CREATE TABLE IF NOT EXISTS `hcmio` (
  `TradeDate` char(8) NOT NULL,
  `BranchNo` char(4) NOT NULL,
  `CustSeq` char(7) NOT NULL,
  `DocSeq` char(5) NOT NULL,
  `Stock` char(6) DEFAULT NULL,
  `BsType` char(1) DEFAULT NULL,
  `Price` decimal(10,4) DEFAULT NULL,
  `Qty` decimal(9,0) DEFAULT NULL,
  `Amt` decimal(16,2) DEFAULT NULL,
  `Fee` decimal(8,0) DEFAULT NULL,
  `Tax` decimal(8,0) DEFAULT NULL,
  `StinTax` decimal(8,0) DEFAULT NULL,
  `NetAmt` decimal(16,2) DEFAULT NULL,
  `ModDate` char(8) DEFAULT NULL,
  `ModTime` char(6) DEFAULT NULL,
  `ModUser` char(10) DEFAULT NULL,
  PRIMARY KEY (`TradeDate`,`BranchNo`,`CustSeq`,`DocSeq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  esmp.hcmio 的資料：~10 rows (近似值)
/*!40000 ALTER TABLE `hcmio` DISABLE KEYS */;
REPLACE INTO `hcmio` (`TradeDate`, `BranchNo`, `CustSeq`, `DocSeq`, `Stock`, `BsType`, `Price`, `Qty`, `Amt`, `Fee`, `Tax`, `StinTax`, `NetAmt`, `ModDate`, `ModTime`, `ModUser`) VALUES
	('20220801', 'F62W', '02', 'BB001', '2357', 'B', 282.0000, 2000, 564000.00, 804, 0, 0, -564804.00, '20220823', '113900', 'LEO'),
	('20220801', 'F62W', '02', 'BB002', '2376', 'B', 80.6000, 4000, 322400.00, 459, 0, 0, -322859.00, '20220823', '113900', 'LEO'),
	('20220805', 'F62W', '02', 'AB001', '1216', 'B', 65.0000, 1000, 65000.00, 93, 0, 0, -65093.00, '20220823', '111500', 'LEO'),
	('20220810', 'F62W', '02', 'AB002', '1218', 'S', 30.0000, 3000, 90000.00, 128, 270, 0, 89602.00, '20220823', '111500', 'LEO'),
	('20220811', 'F62W', '02', 'AB003', '1234', 'S', 33.6000, 2000, 67200.00, 96, 202, 0, 66902.00, '20220823', '111500', 'LEO'),
	('20220819', 'F62W', '02', 'CB001', '1203', 'B', 33.4500, 1000, 33450.00, 48, 0, 0, -33498.00, '20220823', '113900', 'LEO'),
	('20220819', 'F62W', '02', 'CB002', '1229', 'B', 57.6000, 3000, 172800.00, 246, 0, 0, -173046.00, '20220823', '113900', 'LEO'),
	('20220819', 'F62W', '02', 'CB003', '1217', 'B', 9.8300, 7000, 68810.00, 98, 0, 0, -68908.00, '20220823', '113900', 'LEO'),
	('20220819', 'F62W', '02', 'CB004', '2379', 'B', 352.0000, 10000, 3520000.00, 5016, 0, 0, -3525016.00, '20220823', '113900', 'LEO'),
	('20220912', 'F62W', '02', 'ZZ001', '6214', 'B', 88.8800, 1000, 88880.00, 126, 0, 0, 88754.00, '20220914', '164101', 'LEO');
/*!40000 ALTER TABLE `hcmio` ENABLE KEYS */;

-- 傾印  資料表 esmp.mstmb 結構
CREATE TABLE IF NOT EXISTS `mstmb` (
  `Stock` char(6) NOT NULL,
  `StockName` varchar(20) NOT NULL DEFAULT '',
  `MarketType` char(1) DEFAULT NULL,
  `CurPrice` decimal(10,4) DEFAULT 0.0000,
  `RefPrice` decimal(10,4) DEFAULT 0.0000,
  `Currency` char(3) DEFAULT 'NTD',
  `ModDate` char(8) DEFAULT NULL,
  `ModTime` char(6) DEFAULT NULL,
  `ModUser` char(10) DEFAULT NULL,
  PRIMARY KEY (`Stock`),
  KEY `IDX_MSTMB` (`Stock`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在傾印表格  esmp.mstmb 的資料：~3 rows (近似值)
/*!40000 ALTER TABLE `mstmb` DISABLE KEYS */;
REPLACE INTO `mstmb` (`Stock`, `StockName`, `MarketType`, `CurPrice`, `RefPrice`, `Currency`, `ModDate`, `ModTime`, `ModUser`) VALUES
	('2357', '華碩', NULL, 368.0000, 0.0000, 'NTD', '20220829', '002902', 'LEO'),
	('2376', '技嘉', NULL, 82.1000, 0.0000, 'NTD', '20220914', '095643', 'LEO'),
	('6214', '精誠', NULL, 99.9900, 0.0000, 'NTD', '20220914', '164133', 'LEO');
/*!40000 ALTER TABLE `mstmb` ENABLE KEYS */;

-- 傾印  資料表 esmp.tcnud 結構
CREATE TABLE IF NOT EXISTS `tcnud` (
  `TradeDate` char(8) NOT NULL,
  `BranchNo` char(4) NOT NULL,
  `CustSeq` char(7) NOT NULL,
  `DocSeq` char(5) NOT NULL,
  `Stock` char(6) DEFAULT NULL,
  `Price` decimal(10,4) DEFAULT NULL,
  `Qty` decimal(9,0) DEFAULT NULL,
  `RemainQty` decimal(9,0) DEFAULT NULL,
  `Fee` decimal(8,0) DEFAULT NULL,
  `Cost` decimal(16,2) DEFAULT NULL,
  `ModDate` char(8) DEFAULT NULL,
  `ModTime` char(6) DEFAULT NULL,
  `ModUser` char(10) DEFAULT NULL,
  PRIMARY KEY (`TradeDate`,`BranchNo`,`CustSeq`,`DocSeq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  esmp.tcnud 的資料：~4 rows (近似值)
/*!40000 ALTER TABLE `tcnud` DISABLE KEYS */;
REPLACE INTO `tcnud` (`TradeDate`, `BranchNo`, `CustSeq`, `DocSeq`, `Stock`, `Price`, `Qty`, `RemainQty`, `Fee`, `Cost`, `ModDate`, `ModTime`, `ModUser`) VALUES
	('20220907', 'F62W', '02', 'SS001', '2376', 80.6000, 1000, 1000, 115, 80485.00, '20220907', '120000', 'LEO'),
	('20220907', 'F62W', '02', 'SS002', '2376', 80.0000, 2000, 2000, 228, 159772.00, '20220907', '120010', 'LEO'),
	('20220907', 'F62W', '02', 'SS003', '2357', 282.0000, 2000, 2000, 804, 564804.00, '20220907', '130000', 'LEO'),
	('20220912', 'F62W', '02', 'ZZ001', '6214', 88.8800, 1000, 1000, 126, 88754.00, '20220914', '164101', 'LEO');
/*!40000 ALTER TABLE `tcnud` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
