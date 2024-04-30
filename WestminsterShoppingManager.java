import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager{
    Scanner scanner = new Scanner(System.in);

    public boolean IDChecker(String productId){
        boolean value = false;
        for(Product prod : Main.products){
            if(prod.getProductID().equals(productId)){
                value = true;
                System.out.println("ID already in used");
                break;
            }else{
                value = false;
            }
        }
        return value;
    }

    @Override
    public void addProduct() {

        // checking the number of products.
        if (Main.products.size() >= Main.maxProducts){
            System.out.println("Cannot add products. Maximum limit reached.");
            return;
        }

        String type;

        do {
            System.out.println("\nEnter product type E for Electronics or C for Clothing");
            System.out.print(">>> ");
            type = scanner.nextLine().toLowerCase();

            // checking the type is electronics or clothing.
            if (!type.equals("e") && !type.equals("c")) {
                System.out.println("Invalid input Please enter either Electronics or Clothing.");
                System.out.println();
            }
        } while (!type.equals("e") && !type.equals("c"));

        double price;
        String prodctID;
        do {
            System.out.println("Enter the Product ID: ");
            System.out.print(">>> ");
            prodctID = scanner.nextLine();
        }while(IDChecker(prodctID));


        System.out.println("Enter the Product Name: ");
        System.out.print(">>> ");
        String prodctName = scanner.nextLine();

        int noOfItems;
        do {
            System.out.println("Enter the Number of Items: ");
            System.out.print(">>> ");
            try {
                noOfItems = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. please enter a valid integer");
                System.out.print("> ");
            }
        } while (true);

        do {
            try {
                System.out.println("Enter the Price:");
                System.out.print(">>> ");
                price = scanner.nextDouble();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. please enter a valid Price");
                System.out.print("> ");
                scanner.nextLine();
            }
        } while (true);

        // Consume the newline character
        scanner.nextLine();

        if (type.equals("e")) {

            System.out.println("Enter the Brand of the product: ");
            System.out.print(">>> ");
            String brand = scanner.nextLine();

            int warranty;
            do {
                System.out.println("Enter the Warranty period in years: ");
                System.out.print(">>> ");
                try{
                    warranty = Integer.parseInt(scanner.nextLine());
                    break;
                }catch (Exception e){
                    System.out.println("Invalid input. please enter a valid warranty");
                    System.out.print("> ");
                }
            }while(true);


            Product product = new Electronics(prodctID, prodctName, noOfItems, price, brand, warranty);
            Main.products.add(product);
            System.out.println("Product has successfully added.");

        } else {


            int size;
            do {
                System.out.println("Enter the Size: ");
                System.out.print(">>> ");
                try{
                    size = Integer.parseInt(scanner.nextLine());
                    break;
                }catch (Exception e){
                    System.out.println("Invalid input. please enter a valid size");
                    System.out.print("> ");
                }
            }while(true);

            System.out.println("Enter the Colour of the Product: ");
            System.out.print(">>> ");
            String colour = scanner.nextLine();

            // creating an object of clothing
            Product product = new Clothing(prodctID, prodctName, noOfItems, price, size, colour);
            Main.products.add(product);
            System.out.println("Product has successfully added.");
        }
    }
    @Override
    public void deleteProduct() {
        System.out.println("Enter the Product ID of the Product you have to delete");
        System.out.print(">>> ");
        String deleteId = scanner.nextLine();

        // creating an iterator to traverse through the list of products in the Main class.
        Iterator<Product> iterator = Main.products.iterator();
        while(iterator.hasNext()){
            Product product = iterator.next();
            if(product.getProductID().equals(deleteId)){
                // displaying information about the deleted product
                String typeOfProduct;

                if(product instanceof Electronics){
                    typeOfProduct = "Electronics";
                }
                else {
                    typeOfProduct = "Clothing";
                }
                iterator.remove();
                System.out.println("The Product with ID " + deleteId + " in the" + " " + typeOfProduct + " category has been deleted successfully."  );

                System.out.println("Total number of products left: " + Main.products.size());
                // exiting the while loop
                return;
            }
        }
    }

    @Override
    public void printList() {
        // sorting the list of products based on the product ID
        Collections.sort(Main.products, Comparator.comparing(Product::getProductID));

        // iterating through the list of products stored in the main class using enhanced for loop.
        for (Product existingProduct : Main.products){
            String typeOfProduct;

            if(existingProduct instanceof Electronics){
                typeOfProduct = "Electronics";
            }
            else{
                typeOfProduct = "Clothing";
            }

            String result = String.format("Product ID: %s,\nProduct Name: %s,\nAvailable Items: %d,\nPrice: %2f", existingProduct.getProductID(), existingProduct.getProductName(), existingProduct.getNumberOfAvailableItems(), existingProduct.getPrice());
            System.out.println();

            if (existingProduct instanceof Electronics){
                result = result + String.format(",\nBrand: %s,\nWarranty period in years: %d", ((Electronics) existingProduct).getBrandName(), ((Electronics) existingProduct).getWarrantyPeriod());
            }
            else if (existingProduct instanceof Clothing){
                result = result + String.format(",\nSize: %d,\nColour: %s", ((Clothing) existingProduct).getSize(), ((Clothing) existingProduct).getColour());
            }

            System.out.println("Type of the product: " + typeOfProduct +"\n" + result);

        }
    }

    @Override
    public void saveToFile() {
        try (FileWriter writer = new FileWriter("Coursework.txt")){
            for(Product product : Main.products){
                String productDetails = String.format("%s,%s,%d,%2f",
                        product.getProductID(),
                        product.getProductName(),
                        product.getNumberOfAvailableItems(),
                        product.getPrice());

                if(product instanceof Electronics){
                    productDetails = productDetails + String.format(",%s,%d,Electronic", ((Electronics)product).getBrandName(), ((Electronics)product).getWarrantyPeriod());
                }
                else if (product instanceof Clothing){
                    productDetails = productDetails + String.format("," + ((Clothing)product).getSize() + ",%s,Clothing", ((Clothing)product).getColour());
                }

                writer.write(productDetails + System.lineSeparator());
            }

            System.out.println("Products saved to File Successfully");

        }catch(IOException e){
            System.out.println("Error occurred when saving to File");
        }
    }

    @Override
    public void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Coursework.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productInfo = line.split(",");

                if (productInfo[6].equals("Electronic")) {
                    try{
                        // Electronics: ID, Name, Items, Price, Brand, Warranty
                        Product product = new Electronics(productInfo[0], productInfo[1], Integer.parseInt(productInfo[2]), Double.parseDouble(productInfo[3]),productInfo[4], Integer.parseInt(productInfo[5]));
                        Main.products.add(product);
                    }
                    catch(NumberFormatException e){
                        System.out.println("Error reading products from file" + e.getMessage());
                    }

                } else{
                    try {
                        // Clothing: ID, Name, Items, Price, Size, Colour
                        Product product = new Clothing(productInfo[0], productInfo[1], Integer.parseInt(productInfo[2]), Double.parseDouble(productInfo[3]), Integer.parseInt(productInfo[4]), productInfo[5]);
                        Main.products.add(product);
                    }
                    catch (NumberFormatException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            System.out.println("Products loaded from file successfully.");
        } catch (IOException e) {
            System.out.println("Error reading products from file: " + e.getMessage());
        }
    }
}
