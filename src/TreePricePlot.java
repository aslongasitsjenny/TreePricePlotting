import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

class TreePricePlot extends JComponent {
    private ArrayList<TreePrice> treePrices;

    public TreePricePlot() {
        TreePriceData treePriceData = new TreePriceData(); // creates an instance of TreePriceData
        treePrices = treePriceData.readTreePriceDataFromFile("C:\\Users\\User\\Desktop\\treePrices\\treePrices.csv");

        // Add mouse listener to this component
        addMouseListener(new TreePriceMouseListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set up colors for nominal and real price lines
        g.setColor(new Color(255, 105, 180)); // Bright pink for nominal price
        // Plot nominal price line
        int[] xPointsNominal = new int[treePrices.size()]; // integer array xPointsNominal with a size equal to the size of the treePrices list
        int[] yPointsNominal = new int[treePrices.size()]; // integer array yPointsNominal with a size equal to the size of the treePrices list
        for (int i = 0; i < treePrices.size(); i++) { // initial value of i equal to 0, continues as long as i is less than the size of the treePrices list, and increments i after each iteration
            TreePrice currentPrice = treePrices.get(i); // retrieves the i-th element from the treePrices list and assigns it to a variable called currentPrice of type TreePrice
            int x = i * getWidth() / (treePrices.size() - 1); // X coordinate of current data point
            int y = getHeight() - (int) (currentPrice.getNominalPrice() * getHeight() / getMaxPrice()); // Y coordinate of current nominal price
            xPointsNominal[i] = x; //assigns the value of x to an array element at index i in the array xPointsNominal
            yPointsNominal[i] = y; //assigns the value of y to an array element at index i in the array yPointsNominal
            g.drawString(currentPrice.getPriceDate().toString(), x, getHeight() - 5); // Print date beneath nominal price data point
        }
        g.drawPolyline(xPointsNominal, yPointsNominal, treePrices.size()); // Draw nominal price line

        g.setColor(new Color(30, 144, 255)); // Neon blue for real price
        // Plot real price line
        int[] XPR = new int[treePrices.size()]; // integer array XPR with a size equal to the size of the treePrices list
        int[] YPR = new int[treePrices.size()]; // integer array YPR with a size equal to the size of the treePrices list
        for (int i = 0; i < treePrices.size(); i++) { // initial value of i equal to 0, continues as long as i is less than the treePrices list, and increments i after each iteration
            TreePrice currentPrice = treePrices.get(i); // retrieves the i-th element from the treePrices list and assigns it to a variable called
            int x = i * getWidth() / (treePrices.size() - 1); // X coordinate of current data point
            int y = getHeight() - (int) (currentPrice.getRealPrice() * getHeight() / getMaxPrice()); // Y coordinate of current real price
            XPR[i] = x; //assigns the value of x to an array element at index i in the array XPR.
            YPR[i] = y; //assigns the value of y to an array element at index i in the array XPR.
        }
        g.drawPolyline(XPR, YPR, treePrices.size()); // Draw real price line
    }

    private double getMaxPrice() {
        double maxPrice = 0.0;
        for (TreePrice price : treePrices) { // iterates through each TreePrice object in the treePrices ArrayList using a for-each loop
            maxPrice = Math.max(maxPrice, Math.max(price.getNominalPrice(), price.getRealPrice()));//updates the value of maxPrice with the maximum value between maxPrice, price.getNominalPrice(), and price.getRealPrice()
        }
        return maxPrice; // returns the value of maxPrice
    }

    private class TreePriceMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX(); // Get the X coordinate of the mouse click
            int mouseY = e.getY(); // Get the Y coordinate of the mouse click

            // Calculate the closest price data point to the mouse click based on Euclidean distance
            TreePrice closestPrice = null;
            double minDistance = Double.MAX_VALUE; // Initialise the minimum distance
            for (TreePrice price : treePrices) { //iterate over each element in the treePrices list, assigning each element to a variable called price of type TreePrice
                int x = treePrices.indexOf(price) * 10; // Calculate the X coordinate of the data point
                int y = getHeight() - (int) (price.getNominalPrice() * 10); // Calculate the Y coordinate of the data point

                // Calculate the Euclidean distance between the mouse click and the data point
                double distance = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2));

                // Update the closest price if the current distance is smaller than the current minimum distance
                if (distance < minDistance) { // Update the closest price
                    minDistance = distance;
                    closestPrice = price;
                }
            }

            if (closestPrice != null) {
                System.out.println("Closest Price to Mouse Click:");
                System.out.println("Date: " + closestPrice.getPriceDate());
                System.out.println("Nominal Price: " + closestPrice.getNominalPrice());
                System.out.println("Real Price: " + closestPrice.getRealPrice());
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tree Price Plot");
        TreePricePlot treePricePlot = new TreePricePlot();
        treePricePlot.addMouseListener(treePricePlot.new TreePriceMouseListener()); // Add mouse listener to the component
        frame.add(treePricePlot);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
