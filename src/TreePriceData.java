
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;


class TreePriceData {
    private ArrayList<TreePrice> treePrices;
    private int getMonthNumber(String monthAbbreviation) {
        String[] months = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(monthAbbreviation)) {
                return i + 1; // Return the month index as int
            }
        }
        return -1; // Return -1 if month abbreviation is not found
    }


    public ArrayList<TreePrice> readTreePriceDataFromFile(String fileName) {
        treePrices = new ArrayList<>(); //create an empty ArrayList to store the tree prices

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) { //continues as long as 'line' is not null, which indicates that there are more lines to be read
                String[] data = line.split(","); //split the line by comma
                int day;
                String monthAbbreviation;
                int year;
                double nominalPrice;
                double realPrice;

                try {
                    day = Integer.parseInt(data[0].substring(0, 2)); //first two characters from the string at index 0 of the 'data' array, using the 'substring' method
                    monthAbbreviation = data[0].substring(3, 6).toLowerCase(); //data[0].substring(3, 6) will extract the characters from index 3 (inclusive) to index 6 (exclusive)
                    year = Integer.parseInt(data[0].substring(data[0].length() - 2));//parses the last two characters of a string in data[0] as an integer and stores the result in the year variable
                    if (year < 50) {
                        year += 2000;
                    } else {
                        year += 1900;
                    }

                    // error checks for year range
                    if (year < 1900 || year > 2023) {
                        System.err.println("Skipping line: " + line + " due to invalid year");
                        continue;
                    }

                    nominalPrice = Double.parseDouble(data[1]);
                    realPrice = Double.parseDouble(data[2]); //
                } catch (NumberFormatException e) {
                    System.err.println("Skipping line: " + line + " due to invalid data format");
                    continue;
                }

                String month = String.format("%02d", getMonthNumber(monthAbbreviation)); // Get the month number as a two-digit String with leading zeros
                if (month == null) {
                    System.err.println("Skipping line: " + line + " due to invalid month abbreviation");
                    continue;
                }

                PriceDate priceDate = new PriceDate(day, month, year); // Pass month as string
                TreePrice treePrice = new TreePrice(realPrice, nominalPrice, priceDate); //creates a new instance of the TreePrice class, with the constructor taking three arguments
                treePrices.add(treePrice); //adds the treePrice object  to a collection or list called treePrices
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return treePrices;
    }
}
