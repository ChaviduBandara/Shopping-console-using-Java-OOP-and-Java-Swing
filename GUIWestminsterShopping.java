import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

public class GUIWestminsterShopping extends JFrame {

    GridBagConstraints c = new GridBagConstraints();

    // sorting the jcombobox according to all,Electronics and Clothing.
    JComboBox<String> jComboBox;
    JTable jt;
    String[][] data;
    JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;

    private JTable cartTable;

    GUIWestminsterShopping(){
        setLayout(new GridBagLayout());

        cartTable = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Product ID", "Product", "Quantity", "Price"}));

        JLabel jLabel = new JLabel("Select product Category");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(50,10,50,50);
        c.anchor = GridBagConstraints.CENTER;

        add(jLabel, c);

        String[] items = {"All", "Electronics", "Clothing"};
        jComboBox = new JComboBox<>(items);
        c.gridx = 1;
        c.gridy = 0;

        add(jComboBox, c);

        JButton btn1 = new JButton("Shopping Cart");
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weighty = 0.2;
        c.gridheight = 2;
        c.insets = new Insets(0,0,20,0);
        add(btn1, c);

        // adding ActionListener to " Add to Shopping cart" button.
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // creating a new frame for the shopping cart.
                JFrame shoppingCartFrame = new JFrame("Shopping Cart");

                String[] coloumnNames = {"Product ID", "Product", "Quantity", "Price"};
                Object[][] cartData = {};

                // creating a JTable for the shopping cart
                JTable cartTable = new JTable(cartData, coloumnNames);

                // adding a scrollPane to the table for scrolling.
                JScrollPane scrollpane = new JScrollPane(cartTable);

                // adding the scroll pane to the frame
                shoppingCartFrame.add(scrollpane);


                shoppingCartFrame.setSize(650,500);
                shoppingCartFrame.setVisible(true);
                // putting dispose on close to only exit the Shopping cart frame.
                shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            }
        });

        c.insets = new Insets(5,-60,5,-60);


        // inserting the product information to the JTable.
        // creating an instance of WestminsterShoppingManager.
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // calling the readFile method to load products from the file
        shoppingManager.readFile();

        // getting the list of products from main class. (Main.products)
        ArrayList<Product> productsFromMain = Main.products;

        // create data array for JTable.
        data = new String[productsFromMain.size()][];

        for (int i = 0; i< productsFromMain.size(); i++){
            Product product = productsFromMain.get(i);

            // a method to get the category of the product.
            String category = (product instanceof Electronics) ? "Electronics" : "Clothing";

            // a method to get additional information based on the product
            String information = (product instanceof Electronics)
                    ? String.format("%s, %d", ((Electronics) product).getBrandName(), ((Electronics) product).getWarrantyPeriod())
                    : String.format("%d, %s", ((Clothing) product).getSize(), ((Clothing) product).getColour());

            // including the Available item details in the data Array.
            String itemsAvailable = String.valueOf(product.getNumberOfAvailableItems());

            // creating an array of strings(String[]) and assiging it to the i th element of the data array.
            data[i] = new String[]{
                    product.getProductID(),
                    product.getProductName(),
                    category,
                    String.valueOf(product.getPrice()),
                    information,
                    itemsAvailable
            };
        }

        // creating the JTable.
        String column[]={"Product ID","Name","Category", "Price", "Info"};
        String rows[][]={ {"101","Amit","670000","657","Samsung,12 weeks warranty"},
                {"101","Amit","670000","657","Samsung,12 weeks warranty"},
                {"101","Amit","670000","657","Samsung,12 weeks warranty"}};

        jt = new JTable(data,column);
        jt.setBounds(30,40,200,300);
        JScrollPane sp = new JScrollPane(jt);
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 3;

        add(sp, c);


        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(8,1));

        String proId = (String) jt.getValueAt(0,0);

        JLabel productDetailBtn = new JLabel("Selected Product-Details");
        lbl1 = new JLabel("Product Id: ");
        lbl2 = new JLabel("Category: ");
        lbl3 = new JLabel("Name: ");
        lbl4 = new JLabel("Size: ");
        lbl5 = new JLabel("Colour: ");
        lbl6 = new JLabel("Items Available: ");

        JButton shoppingBtn = new JButton("Add to Shopping cart");
        shoppingBtn.addActionListener(e -> addToShoppingCart());


        JPanel p2 = new JPanel();
        p2.add(shoppingBtn);

        p1.add(lbl1);
        p1.add(lbl2);
        p1.add(lbl3);
        p1.add(lbl4);
        p1.add(lbl5);
        p1.add(lbl6);
        p1.add(p2);
        c.gridx = 0;
        c.gridy = 2;

        add(p1, c);

        // add ListSelectionListener to tht JTable
        jt.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateLabels();
            }
        });


        // adding ActionListener to jCombobox
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        // Initial update of the Labels.
        updateLabels();

        setTitle("Westminster Shopping Center");
        setSize(700,800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addToShoppingCart() {

        int selectedRow = jt.getSelectedRow();

        if (selectedRow != -1) {
            // Getting the selected product details from the data array
            String productId = data[selectedRow][0].trim();
            String productName = data[selectedRow][1];
            double price = Double.parseDouble(data[selectedRow][3]);

            // Declare the category variable
            String category = data[selectedRow][2];

            // Depending on your product types, create the appropriate Product instance
            // (assuming you have an Electronics and Clothing class)
            Product product = null;
            if ("Electronics".equals(category)) {
                String brand = data[selectedRow][4].split(",")[0].trim();
                int warranty = Integer.parseInt(data[selectedRow][4].split(",")[1].trim());
                product = new Electronics(productId, productName, price);
            } else if ("Clothing".equals(category)) {
                int size = Integer.parseInt(data[selectedRow][4].split(",")[0].trim());
                String color = data[selectedRow][4].split(",")[1].trim();
                product = new Clothing(productId, productName, price);
            } else {
                // Handle other categories if needed
                return;
            }

            // Add the selected product to the shopping cart
            ShoppingCart.addToCart(product);

            // Update the shopping cart table
            updateShoppingCartTable();
        }
    }

    private void updateShoppingCartTable() {

        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();
        cartModel.setRowCount(0); // Clear existing rows

        // Add rows to the cartTable based on the products in the shopping cart
        for (Product product : ShoppingCart.shoppingCart) {
            cartModel.addRow(new Object[]{
                    product.getProductID(),
                    product.getProductName(),
                    1, // Assuming quantity is always 1 for simplicity
                    product.getPrice()
            });
        }
        // printing the shoppingCart ArrayList.
        ShoppingCart.printList();
    }

    private void updateLabels(){
        int selectedRow = jt.getSelectedRow();

        if (selectedRow >= 0){
            // getting the selected product details from the data Array
            String proId = data[selectedRow][0];
            String category = data[selectedRow][2];
            String name = data[selectedRow][1];
            String sizeColourInfo = data[selectedRow][4];
            String itemsAvailable = data[selectedRow][5];

            // updating the labels with the selected product details
            lbl1.setText("Product Id: " + proId);
            lbl2.setText("Category: " + category);
            lbl3.setText("Name: " + name);
            lbl6.setText("Items Available: " + itemsAvailable);

            // checking if it's Electronics or Clothing to set the size/colour details
            if("Electronics".equals(category)){
                lbl4.setText("Brand: " + sizeColourInfo.split(",")[0]);
                lbl5.setText("Warranty: " + sizeColourInfo.split(",")[1]);
            } else if ("Clothing".equals(category)){
                lbl4.setText("Size: " + sizeColourInfo.split(",")[0]);
                lbl5.setText("Colour: " + sizeColourInfo.split(",")[1]);
            }
        }
    }

    // method to update the JTable based on the selecte category in Jcombobox
    private void updateTable() {
        String selectedCategory = (String) jComboBox.getSelectedItem();
        ArrayList<Product> comboboxProducts = new ArrayList<>();

        for (Product product : Main.products) {
            if ("All".equals(selectedCategory) ||
                    (product instanceof Electronics && "Electronics".equals(selectedCategory)) ||
                    (product instanceof Clothing && "Clothing".equals(selectedCategory))) {
                comboboxProducts.add(product);
            }
        }

        // sorting the products based on the selected category
        if ("Electronics".equals(selectedCategory)) {
            comboboxProducts.sort(Comparator.comparing(p -> ((Electronics) p).getBrandName()));
        } else if ("Clothing".equals(selectedCategory)) {
            // sorting alphabetically by name
            comboboxProducts.sort(Comparator.comparing(Product::getProductName));
        }

        // updating the JTable with the sorted and filtered products
        updateJTable(comboboxProducts);
    }

    // updating the JTable with new data
    private void updateJTable(ArrayList<Product> products){
        //sorting the products alphabetically by name.
        products.sort(Comparator.comparing(Product::getProductName));

        data = new String[products.size()][];

        for(int i=0; i<products.size(); i++){
            Product product = products.get(i);

            String category = (product instanceof Electronics) ? "Electronics" : "Clothing";
            String information = (product instanceof Electronics)
                    ? String.format("%s, %d", ((Electronics) product).getBrandName(), ((Electronics) product).getWarrantyPeriod())
                    : String.format("%d, %s", ((Clothing) product).getSize(), ((Clothing) product).getColour());

            String itemsAvailable = String.valueOf(product.getNumberOfAvailableItems());

            data[i] = new String[]{
                    product.getProductID(),
                    product.getProductName(),
                    category,
                    String.valueOf(product.getPrice()),
                    information,
                    itemsAvailable
            };
        }

        // update the JTable with the new data
        DefaultTableModel model1 = new DefaultTableModel(data, new String[]{"Product ID", "Name", "Category", "Price", "Info"});
        jt.setModel(model1);
    }

    public static void main(String[] args) {
        new GUIWestminsterShopping();
    }

}
