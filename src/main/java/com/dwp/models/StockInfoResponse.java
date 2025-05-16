package com.dwp.models;

public class StockInfoResponse {
    String productId;
    int totalBuys;
    int totalSells;
    int currentStock;
    double totalTransactions;

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

    public double getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(double totalTransactions) {
        this.totalTransactions = totalTransactions;
    }
}
