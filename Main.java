import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static final int maxProducts = 50;
    static ArrayList<Product> products = new ArrayList<>();

    static WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();

    public static void main(String[] args) {

        int option1;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("******************************");
            System.out.println("*   Welcome to Westminster   *");
            System.out.println("******     Shopping     ******");
            System.out.println();
            System.out.println("------------------------------");
            System.out.println("1. Manager");
            System.out.println("2. Customer");
            System.out.println("3. Exit");

            try{
                System.out.println();
                System.out.print("Select an Option(1,2,3): ");
                option1 = scanner.nextInt();
                scanner.nextLine();
            }
            catch (Exception e){
                option1 = -1;
            }

            switch (option1){
                case 1:
                    runConsole();
                    option1 = 3;
                    break;
                case 2:
                    GUIWestminsterShopping frame = new GUIWestminsterShopping();
                    SwingUtilities.invokeLater(()-> {
                        frame.setVisible(true);
                            });
                    option1 = 3;
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid Input, Select an Option (1,2,3): ");
                    break;
            }
        }while (option1 != 3);

    }

    public static void runConsole() {
        int option2;

        do {
            System.out.println();
            System.out.println("-------------------------------------");
            System.out.println("Westminster Shopping Management System");
            System.out.println("--------------------------------------");
            System.out.println("Enter 1 to add a product\nEnter 2 to print product list\nEnter 3 to delete a product fpr the list\nEnter 4 to Save the products to a text file\nEnter 5 to read the products from the file\nEnter 0 to Exit the program");
            System.out.print("\nSelect an Option (1,2,3,4,5,0): ");

            Scanner scan = new Scanner(System.in);

            try {
                option2 = scan.nextInt();
            } catch (Exception e) {
                option2 = 99;
            }


            switch (option2) {
                case 1:
                    westminsterShoppingManager.addProduct();
                    break;
                case 2:
                    westminsterShoppingManager.printList();
                    break;
                case 3:
                    westminsterShoppingManager.deleteProduct();
                    break;
                case 4:
                    westminsterShoppingManager.saveToFile();
                    break;
                case 5:
                    westminsterShoppingManager.readFile();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid Input, Select an Option (1,2,3,4,5,0): ");
                    break;

            }
        } while (option2 != 0);
    }
}
