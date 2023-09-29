package com.example.geekster.project.StockManagementApplication.Service;


import com.example.geekster.project.StockManagementApplication.Model.Stock;
import com.example.geekster.project.StockManagementApplication.Model.StockType;
import com.example.geekster.project.StockManagementApplication.Repository.IStockRepo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {
    @Autowired

    IStockRepo stockRepo;

    //custom finder
    public List<Stock> getStocksByType(StockType stockType) {
        return stockRepo.findByStockType(stockType);
    }

    //implicit methods in crudRepo
    public String addStocks(List<Stock> stockList) {

        Iterable<Stock> list = stockRepo.saveAll(stockList);
        if(list!=null)
        {
            return "Added list of Stocks.....!!!";
        }
        else
        {
            return "Could not added..!!!";
        }
    }

    public List<Stock> getStocksAbovePriceAndLowerDate(Double price, String date) {

        LocalDateTime myDate = LocalDateTime.parse(date);
        return stockRepo.findByStockPriceGreaterThanAndStockBirthTimeStampLessThanOrderByStockName(price, myDate);
    }

    public List<Stock> getAllStocksAboveMarketCap(Double capPercentage) {
        return stockRepo.getAllStocksAboveMarketCap(capPercentage);
    }

    @Transactional
    public void updateMarketCap(Double marketCap, Integer id) {
        stockRepo.updateMarketCapById(marketCap,id);
    }

    @Transactional
    public void deleteStocksBasedOnCount(Integer count) {
        stockRepo.deleteStocksBasedOnCount(count);
    }



    @Transactional
    public void updateTypeById(StockType stockType, Integer id) {

        //int value = stockType.ordinal();
        String enumValue = stockType.name();
        stockRepo.modifyStockTypeById(enumValue, id);

        throw new IllegalStateException("Testing done by Amit");

    }

    @Transactional
    public void updateStockById(Integer id, Stock myStock) {

        stockRepo.updateStockById(id,myStock.getStockId(),myStock.getStockName(),myStock.getStockPrice(),myStock.getStockBirthTimeStamp());
    }
}