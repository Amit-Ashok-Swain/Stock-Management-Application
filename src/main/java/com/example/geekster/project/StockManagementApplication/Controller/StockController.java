package com.example.geekster.project.StockManagementApplication.Controller;


import com.example.geekster.project.StockManagementApplication.Model.Stock;
import com.example.geekster.project.StockManagementApplication.Model.StockType;
import com.example.geekster.project.StockManagementApplication.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "stock")
public class StockController {

    @Autowired
    StockService stockService;

    // get Stocks by type
    @GetMapping("/by-type/{stockType}")
    public ResponseEntity<List<Stock>> getStocksByType(@PathVariable StockType stockType) {
        List<Stock> stocks = stockService.getStocksByType(stockType);
        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(stocks);
        }
    }

    //get using custom finder
    @GetMapping(value = "abovePrice/price/{price}/lowerDate/date/{date}")
    public List<Stock> getStocksAbovePriceAndLowerDate(@PathVariable Double price,@PathVariable String date)
    {
        return stockService.getStocksAbovePriceAndLowerDate(price,date);
    }

    //custom query
    @GetMapping(value = "/cap/{capPercentage}")
    public List<Stock> getAllStocksAboveMarketCap(@PathVariable Double capPercentage)
    {
        return stockService.getAllStocksAboveMarketCap(capPercentage);
    }

    //post
    @PostMapping(value = "/stocks")
    public String insertStocks(@RequestBody List<Stock> stockList)
    {
        return stockService.addStocks(stockList);
    }

    //put
    @PutMapping(value = "/marketCap/{marketCap}/id/{id}")
    public void insertStocks(@PathVariable Double marketCap, @PathVariable Integer id)
    {
        stockService.updateMarketCap(marketCap,id);
    }

    //PUT USING CUSTOM QUERY :
    @PutMapping(value = "/stock/type/id")
    public void updateTypeById(@RequestParam StockType stockType, @RequestParam Integer id)
    {
        stockService.updateTypeById(stockType,id);
    }

    //put using Cq : stock, id
    @PutMapping(value = "/stock/{id}")
    public void updateStockById(@PathVariable Integer id, @RequestBody Stock myStock)
    {
        stockService.updateStockById(id,myStock);
    }

    //DELETE
    @DeleteMapping(value = "/ownerCount/{count}")
    public void removeStocksByOwnerCount(@PathVariable  Integer count)
    {
        stockService.deleteStocksBasedOnCount(count);
    }
}