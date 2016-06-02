//import java.io.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * This class creates a Yelp database of Restaurants.
 * @author Kristen McClelland
 * @version 2.0
 */
public class YelpDB {
    private Restaurant[] resArray;
    private int numRestaurants;
    private String filename;
    /**
     * Creates a new Yelp database with an initial size of 20 and
     * 0 Restaurants.
     */
    public YelpDB() {
        resArray = new Restaurant[20];
        numRestaurants = 0;
    }
    /**
     * Loads a file and imports its values into the database. If the
     * file is not formatted correctly, matching the designed specifications
     * or contains duplicate Restaurants, then the method throws a
     * CorruptDatabaseException, telling the user that their file is corrupt.
     * If the file does not exist in the specified location, then a
     * FileNotFoundException is thrown.
     * @param fname String representation of the filename including the
     * txt extension
     * @throws FileNotFoundException Thrown it the file cannot be found
     * based on the filename passed in
     * @throws CorruptDatabaseException Thrown if the database has
     * duplicate restaurants or other issues in formatting
     */
    public void load(String fname) throws FileNotFoundException,
        CorruptDatabaseException {
        if (fname == null) {
            throw new FileNotFoundException();
        } else {
            this.filename = fname;
            File file = new File(fname);
            Scanner scan = new Scanner(file);
            try {
                int numRes = scan.nextInt();
                scan.nextLine();
                for (int i = 0; i < numRes; i++) {
                    String name = scan.nextLine();
                    String cusine = scan.nextLine();
                    double avgRating = scan.nextDouble();
                    scan.nextLine();
                    String priceRange = scan.nextLine();
                    Restaurant r = new Restaurant(name, cusine, priceRange);
                    int numReviews = scan.nextInt();
                    if (numReviews == 0 && scan.hasNext()) {
                        scan.nextLine();
                    } else {
                        for (int j = 0; j < numReviews; j++) {
                            if (j == 0) {
                                scan.nextLine();
                            }
                            int rating = scan.nextInt();
                            scan.nextLine();
                            String date = scan.nextLine();
                            Review rev = new Review(rating, date, r);
                            r.addReview(rev);
                        }
                    }
                    for (int k = 0; k < numRestaurants; k++) {
                        if (r.equals(resArray[k])) {
                            throw new CorruptDatabaseException("This file has "
                                + "duplicate restaurant entries!");
                        }
                    }
                    resArray[i] = r;
                    numRestaurants++;
                    // can check that Restaurant allocated properly by comparing
                    // average ratings and number of restaurants
                }
            // if at any point the program thinks a value should be a certain
            // type in the file but it isnt, this exception will be thrown
            } catch (InputMismatchException e) {
                throw new CorruptDatabaseException("Corrupt data file: format "
                    + "is incorrect! Input Mismatch");
            // attempts to convert a string to a numeric but the string is
            // not a numeric
            } catch (NumberFormatException e) {
                throw new CorruptDatabaseException("Corrupt data file: format "
                    + "is incorrect! Number Format");
            } finally {
                scan.close();
            }
        }
    }
    /**
     * Saves the final database to the file designated.
     */
    public void save() {
        // FIX THIS FILE
        try {
            FileWriter outFile = new FileWriter(filename);
            PrintWriter outWriter = new PrintWriter(outFile);
            // number of restaurants
            outWriter.println(numRestaurants);
            for (int i = 0; i < numRestaurants; i++) {
                // restaurant toFile
                String str = resArray[i].toFile();
                str = str.replaceAll("\n", System.lineSeparator());
                outWriter.print(str);
            }
            outWriter.close();
        } catch (IOException e) {
            System.out.println("Unable to save database.");
        }
    }
    /**
     * Adds a Restaurant to the database, unless that Restaurant is already
     * in the database.
     * @param r Restaurant to be added to the database
     * @throws DuplicateRestaurantException Thrown if the restaurant added is
     * identical to a restaurant already in the database
     */
    public void addToDatabase(Restaurant r)
        throws DuplicateRestaurantException {
        for (int i = 0; i < numRestaurants; i++) {
            if (r.equals(resArray[i])) {
                throw new DuplicateRestaurantException("The restaurant "
                    + r.getName() + " already exists in the database. "
                    + "Can't add a duplicate!");
            }
        }
        numRestaurants++;
        if (numRestaurants > resArray.length) {
            Restaurant[] temp = resArray;
            resArray = new Restaurant[temp.length * 2];
            for (int i = 0; i < temp.length; i++) {
                resArray[i] = temp[i];
            }
        }
        resArray[numRestaurants - 1] = r;
    }
    /**
     * Search database based on restaurant name
     * @param name Name of the restaurant to be searched for
     */
    public void search(String name) {
        boolean check = false;
        Restaurant r = null;
        for (int i = 0; i < numRestaurants; i++) {
            if (resArray[i].getName().equalsIgnoreCase(name)) {
                r = resArray[i];
                check = true;
            }
        }
        if (check && r != null) {
            System.out.println(r.toStringSimple());
        } else {
            System.out.println(name + " does not exist in the database!");
        }
    }
    /**
     * Search database based on restaurant cusine and priceRange
     * @param cusine Type of food to be searched for
     * @param priceRange Price ($ to $$$$) to be searched for
     */
    public void search(String cusine, String priceRange) {
        boolean check = false;
        for (int i = 0; i < numRestaurants; i++) {
            if (resArray[i].getCusine().equalsIgnoreCase(cusine)
                && resArray[i].getPriceRange().equalsIgnoreCase(priceRange)) {
                System.out.println(resArray[i].toStringSimple());
                check = true;
            }
        }
        if (!check) {
            System.out.println("No restaurants matched your search "
                + "for " + cusine + " and " + priceRange + "!");
        }
    }
    /**
     * Search database with no parameters, printing the restuarant
     * with the highest average rating. If multiple restaurants have
     * the highest rating, all of them are printed out
     */
    public void search() {
        double max = 0;
        if (numRestaurants == 0) {
            System.out.println("No search results. There are no "
                + "restaurants in this database.");
        } else {
            // determine max rating
            for (int i = 0; i < numRestaurants; i++) {
                if (resArray[i].getAvgRating() > max) {
                    max = resArray[i].getAvgRating();
                }
            }
            // print out restaurants matching max rating
            for (int i = 0; i < numRestaurants; i++) {
                if (resArray[i].getAvgRating() == max) {
                    System.out.println(resArray[i].toStringSimple());
                }
            }
        }
    }
    /**
     * Returns an array of Restaurants without null values.
     * @return A Restaurant array without null values
     */
    public Restaurant[] getRestaurants() {
        Restaurant[] arr = new Restaurant[numRestaurants];
        for (int i = 0; i < numRestaurants; i++) {
            arr[i] = resArray[i];
        }
        return arr;
    }
}