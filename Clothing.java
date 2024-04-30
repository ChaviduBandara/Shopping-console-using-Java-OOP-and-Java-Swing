public class Clothing extends Product{
    private int size;
    private String colour;

    public Clothing(String productID, String productName, int numberOfAvailableItems, double price, int size, String colour){
        super(productID, productName, numberOfAvailableItems, price);
        this.size = size;
        this.colour = colour;
    }

    public Clothing(String productID, String productName, double price) {
        super(productID, productName, price);
    }

    public Clothing(String productID, String productName, int numberOfAvailableItems, double price) {
        super(productID, productName, numberOfAvailableItems, price);
    }

    public int getSize(){
        return size;
    }
    public String getColour(){
        return colour;
    }
    public void setSize(int size){
        this.size = size;
    }
    public void setColour(String colour){
        this.colour = colour;
    }

    @Override
    public String toString(){
        return "Clothing{" + "Size ='" + size + '\'' + ", Colour ='" + colour + '\'' + '}';
    }
}
