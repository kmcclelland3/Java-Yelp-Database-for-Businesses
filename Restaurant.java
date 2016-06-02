/**
 * Creates and manages a Restaurant with a name, cusine, price range
 * and associated Reviews.
 * @author Kristen McClelland
 * @version 2.0
 */
public class Restaurant {
    private String name;
    private String cusine;
    private Review[] arrReviews;
    private String priceRange;
    private double avgRating;
    private int numReviews;
    /**
     * Creates a Restaurant with a name, type of cusine, and price
     * range.
     * @param name Name of the Restaurant
     * @param cusine Type of cusine
     * @param priceRange Price range of the restaurant, represented in
     * number of dollar signs ($ to $$$$)
     */
    public Restaurant(String name, String cusine,
        String priceRange) {
        this.name = name;
        this.cusine = cusine;
        this.priceRange = priceRange;
        arrReviews = new Review[10];
        avgRating = 0;
        numReviews = 0;
    }
    /**
     * Adds a Review to the Review array for this Restaurant and
     * recalculates the average rating.
     * @param r Review to be added to the Review array for the
     * Restaurant
     */
    public void addReview(Review r) {
        boolean check = true;
        for (int j = 0; j < numReviews; j++) {
            if (arrReviews[j].equals(r)) {
                check = false;
            }
        }
        if (check) {
            numReviews++;
            if (numReviews > arrReviews.length) {
                Review[] temp = arrReviews;
                arrReviews = new Review[temp.length * 2];
                for (int i = 0; i < temp.length; i++) {
                    arrReviews[i] = temp[i];
                }
            }
            arrReviews[numReviews - 1] = r;
            this.calcAvgRating();
        }
    }
    /**
     * Calculates the average rating for this Restaurant based on
     * Reviews.
     */
    private void calcAvgRating() {
        int totalRating = 0;
        if (numReviews == 0) {
            this.avgRating = totalRating;
        } else {
            for (int i = 0; i < numReviews; i++) {
                totalRating += arrReviews[i].getRating();
            }
            this.avgRating = (double) totalRating / numReviews;
        }
    }
    /**
     * Deletes a Review from this Restaurant based on the specified
     * input.
     * @param r the indice of the Review to be deleted, plus 1
     */
    public void deleteReview(int r) {
        if (r > numReviews) {
            System.out.println("You input an incorrect number to "
                + "delete a review. No reviews were deleted.");
        } else {
            Review[] test = new Review[arrReviews.length];
            int loc = 0;
            for (int i = 0; i < numReviews; i++) {
                if (i != (r - 1)) {
                    test[loc] = arrReviews[i];
                    loc++;
                }
            }
            arrReviews = test;
            numReviews--;
            this.calcAvgRating();
        }
    }
    /**
     * Returns a String representation of this Restaurant.
     * @return String representation of this Restaurant
     */
    public String toString() {
        int temp = (int) (avgRating * 100);
        double r = (double) temp / 100;
        String str = this.name + " " + this.cusine + " "
            + r + " " + this.priceRange + "\n";
        for (int i = 0; i < numReviews; i++) {
            str = str + arrReviews[i].toFile();
        }
        return str;
    }
    /**
     * Returns a version of this Restaurant to be printed.
     * @return String containing the name, cusine, average rating, and
     * price range of this Restaurant
     */
    public String toStringSimple() {
        int temp = (int) (avgRating * 100);
        double r = (double) temp / 100;
        String str = this.name + " " + this.cusine + " "
            + r + " " + this.priceRange + "\n";
        return str;
    }
    /**
     * Returns whether these Restaurants are equal if their names
     * are the same.
     * @param o Object to be compared to this Restaurant
     * @return a boolean representing whether these Restaurants are
     * equal
     */
    @Override
    public boolean equals(Object o) {
        Restaurant r;
        if (o instanceof Restaurant) {
            r = (Restaurant) o;
            return this.name.equalsIgnoreCase(r.name);
        } else {
            return false;
        }
    }
    /**
     * Overrides the hashCode() method of Object.
     * @return int 1
     */
    @Override
    public int hashCode() {
        return 1;
    }
    /**
     * Returns the name of this Restaurant.
     * @return the String name of this Restaurant
     */
    public String getName() {
        return this.name;
    }
    /**
     * Returns the cusine of this Restaurant.
     * @return the String cusine of this Restaurant
     */
    public String getCusine() {
        return this.cusine;
    }
    /**
     * Returns the price range of this Restaurant.
     * @return the String price range of this Restaurant
     */
    public String getPriceRange() {
        return this.priceRange;
    }
    /**
     * Returns the average rating of this Restaurant.
     * @return double value of the average rating of this Restaurant
     */
    public double getAvgRating() {
        return this.avgRating;
    }
    /**
     * Returns the correct output to printing this Restaurant to a file.
     * @return String representation of the output, including Reviews
     */
    public String toFile() {
        // restaurant name
        // restaurant cusine
        // average rating
        int temp = (int) (avgRating * 100);
        double r = (double) temp / 100;
        // price range
        // number of reviews
        String str = name + "\n"
            + cusine + "\n"
            + r + "\n"
            + priceRange + "\n"
            + numReviews + "\n";
        for (int i = 0; i < numReviews; i++) {
            str = str + arrReviews[i].toFile();
        }
        return str;
    }
    /**
     * Returns a numbered list of Reviews.
     * @return String representation of the Review to list by number
     */
    public String printReviews() {
        String str = "";
        for (int i = 0; i < numReviews; i++) {
            int j = i + 1;
            str = str + j + ".\n" + arrReviews[i].toString() + "\n";
        }
        return str;
    }
    public int getNumReviews() {
        return this.numReviews;
    }
}