package com.dwp.models;

public class StockLevelResponse {
    private String productId;
    private int totalBuys;
    private int totalSells;
    private int currentStock;
    private int totalTransactions;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getTotalBuys() {
        return totalBuys;
    }

    public void setTotalBuys(int totalBuys) {
        this.totalBuys = totalBuys;
    }

    public int getTotalSells() {
        return totalSells;
    }

    public void setTotalSells(int totalSells) {
        this.totalSells = totalSells;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }
}
