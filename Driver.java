// I worked on this assignment alone, only using class materials.

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class loads a file to establish a Yelp database full
 * of Restaurants and reviews and allows a user to interact,
 * alter, and save this database.
 * @author Sylvia Necula
 * @author Kristen McClelland
 * @version 2.0
 */
public class Driver {
    private static Scanner scan = new Scanner(System.in);

    /**
     * The main method that runs the yelp simulated program.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Driver driver = new Driver();
        YelpDB database = new YelpDB();
        String fname;
        if (args.length > 0) {
            fname = args[0];
        } else {
            fname = "YelpDB.txt";
        }

        try {
            database.load(fname);
        } catch (FileNotFoundException e) {
            System.err.println(fname + " cannot be found");
            System.exit(1);
        } catch (CorruptDatabaseException e) {
            System.err.println("Corrupt database problem");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        boolean cont = true;
        while (cont) {
            System.out.println("Welcome to Yelp! What would you like to do?");
            System.out.println("1. View all Restaurants in database");
            System.out.println("2. Search for a Restaurant");
            System.out.println("3. Add a Restaurant to the Database");
            System.out.println("4. Exit");
            int select = scan.nextInt();

            if (select == 1) {
                driver.viewRestaurants(database);
            } else if (select == 2) {
                driver.search(database);
            } else if (select == 3) {
                driver.addRestaurant(database);
            } else if (select == 4) {
                System.out.println("Goodbye!");
                cont = false;
            } else {
                System.out.println("That's not really an option, try again.");
            }
        }

        database.save();
    }

    /**
     * viewRestaurants allows a user to select a restaurant to edit reviews
     * @param db the database being used
     */
    public void viewRestaurants(YelpDB db) {
        System.out.println("============================================");
        System.out.println("Restaurants currently in Database");
        System.out.println("============================================");
        boolean go = true;
        while (go) {
            int i = 0;
            for (Restaurant r : db.getRestaurants()) {
                System.out.print(++i + ". " + r.toString());
            }
            System.out.println(++i + ". Go back");
            System.out.println("If you would like to change the reviews for a "
                + "restaurant, type the number of the restaurant you would "
                + "like to change the reviews for. Otherwise type " + i
                +  " to quit.\n");
            System.out.print("What would you like to do? ");
            int select = scan.nextInt();
            if (select == i) {
                go = false;
            } else {
                Restaurant r = db.getRestaurants()[select - 1];
                boolean cont = true;
                System.out.println("========================================"
                    + "====");
                System.out.println("Changing reviews for " + r.getName());
                System.out.println("========================================"
                    + "====");
                while (cont) {
                    System.out.println("What would you like to do?");
                    System.out.println("1. Add a review");
                    System.out.println("2. Delete a review");
                    System.out.println("3. Go back");
                    int sel = scan.nextInt();
                    int before = r.getNumReviews();
                    //Implement adding or deleting a review here
                    if (sel == 1) {
                        System.out.println("================================"
                            + "===="
                            + "========");
                        System.out.println("Adding a review");
                        System.out.println("================================"
                            + "===="
                            + "========");
                        System.out.println("What's the rating? (whole number "
                            + "of stars from 1 to 5)");
                        int rating = scan.nextInt();
                        scan.nextLine();
                        System.out.println("What date is the review for? "
                            + "(format: mm/dd/yy");
                        String date = scan.nextLine();
                        Review rev = new Review(rating, date, r);
                        r.addReview(rev);
                        if (r.getNumReviews() == before) {
                            System.out.println("That review could not be added "
                                + "to " + r.getName() + " because it is a "
                                + "duplicate!");
                        }
                    // show reviews, give user number option for which to delete
                    } else if (sel == 2) {
                        System.out.println("==================================="
                            + "=========");
                        System.out.println("Deleting a review");
                        System.out.println("==================================="
                            + "=========");
                        if (r.getNumReviews() != 0) {
                            System.out.print(r.printReviews());
                            System.out.println("Select the review to delete by "
                                + "typing in the number of the review.");
                            int num = scan.nextInt();
                            r.deleteReview(num);
                        } else {
                            System.out.println("There are no reviews to "
                                + "delete!");
                        }
                    } else if (sel == 3) {
                        cont = false;
                        //go = false;
                    } else {
                        System.out.println("That's not an option, try again.");
                    }
                }
            }
        }
    }

    /**
     * Allows a user to add a restaurant to the database
     * @param db the database being used
     */
    public void addRestaurant(YelpDB db) {
        System.out.println("=============================================");
        System.out.println("Adding a Restaurant");
        System.out.println("=============================================");

        scan.nextLine();
        System.out.println("What is the Restaurant's name?");
        String name = scan.nextLine();
        System.out.println("What is the type of cuisine served at " + name
            + "?");
        String cusine = scan.nextLine();
        System.out.println("What is the price range?");
        String priceRange = scan.nextLine();
        Restaurant r = new Restaurant(name, cusine, priceRange);
        try {
            db.addToDatabase(r);
        } catch (DuplicateRestaurantException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Allows a user to search for a restaurant in the database
     * @param db the database being used
     */
    public void search(YelpDB db) {
        System.out.println("==============================================");
        System.out.println("Searching");
        System.out.println("==============================================");

        boolean go = true;
        while (go) {
            System.out.println("What search option would you like to use?");
            System.out.println("1. Search by name");
            System.out.println("2. Search by cuisine and price range");
            System.out.println("3. Give me your best option");
            System.out.println("4. Go back");

            int opt = scan.nextInt();

            if (opt == 1) {
                System.out.println("What restaurant would you like to search"
                    + " for?");
                scan.nextLine();
                db.search(scan.nextLine());
            } else if (opt == 2) {
                scan.nextLine();
                System.out.println("What cuisine would you like to search"
                    + " for?");
                String cuisine = scan.nextLine();
                System.out.println("What price range?");
                db.search(cuisine, scan.nextLine());
            } else if (opt == 3) {
                System.out.println("Our highest rated restaurant is ");
                db.search();
            } else if (opt == 4) {
                go = false;
            } else {
                System.out.println("That's not an option, try again.");
            }
        }
    }

}