import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    public static List<Product> shoppingCart = new ArrayList<>();

    public static void addToCart(Product product) {
        shoppingCart.add(product);
    }

    public static void printList(){
        for(Product prod : shoppingCart){
            System.out.println(prod.getProductID());
        }
    }
}
