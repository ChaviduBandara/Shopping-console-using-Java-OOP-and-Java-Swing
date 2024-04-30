public class Electronics extends Product{
    private String brand;
    private int warranty;

    public Electronics(String productID, String productName, int numberOfAvailableItems, double price, String brand, int warranty){
        super(productID, productName, numberOfAvailableItems, price);
        this.brand = brand;
        this.warranty = warranty;
    }

    public Electronics(String productID, String productName,  double price) {
        super(productID, productName, price);

    }


    public String getBrandName(){
        return brand;
    }
    public int getWarrantyPeriod(){
        return warranty;
    }
    public void setBrandName(String brand){
        this.brand = brand;
    }
    public void setWarrantyPeriod(int warranty){
        this.warranty = warranty;
    }

    @Override
    public String toString(){
        return "Electronics { " + "Brand = " + brand + ", " + " Warranty Period = " + warranty + " }";
    }
}
