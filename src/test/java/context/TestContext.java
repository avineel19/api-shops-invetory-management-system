package context;

import com.dwp.clients.ProductManagement;
import com.dwp.clients.StockManagement;
import com.dwp.models.Orders;
import com.dwp.models.Product;
import com.dwp.models.StockLevelResponse;
import io.restassured.response.Response;

public class TestContext {
    private final ProductManagement productManagement;
    private final StockManagement stockManagement;
    public TestContext(ProductManagement productManagement, StockManagement stockManagement) {
        this.productManagement = productManagement;
        this.stockManagement = stockManagement;
    }
    private Product product;
    private String productId;
    private String orderId;
    private String productName;
    private StockLevelResponse stockLevelResponse;
    private Orders orders;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public StockLevelResponse getStockLevelResponse() {
        return stockLevelResponse;
    }

    public void setStockLevelResponse(StockLevelResponse stockLevelResponse) {
        this.stockLevelResponse = stockLevelResponse;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductManagement getProductManagement() {
        return productManagement;
    }

    public StockManagement getStockManagement() {
        return stockManagement;
    }

    private Response lastResponse;

    public void setLastResponse(Response lastResponse) {
        this.lastResponse = lastResponse;
    }
    public Response getLastResponse() {
        return lastResponse;
    }

}
