/**
 * This class creates a Review associated with a Restaurant,
 * including a date (mm/dd/yy) and rating from 1 to 5 stars.
 * @author Kristen McClelland
 * @version 2.0
 */
public class Review {
    private int rating;
    private String date;
    private Restaurant res;

    /**
     * Creates a Review with a rating, date, and associated Restaurant
     * @param rating int rating for the Review (1 to 5 stars)
     * @param date String date in format dd/mm/yy
     * @param res Restaurant that is associated with the Review
     */
    public Review(int rating, String date, Restaurant res) {
        this.rating = rating;
        this.date = date;
        this.res = res;
    }
    /**
     * Returns a String representation of this review.
     * @return String representation of this review
     */
    public String toString() {
        String str = "Restaurant: " + res.getName() + "\n"
            + "Review Date: " + date + "\n"
            + "Rating (out of 5 stars): " + rating;
        return str;
    }
    /**
     * Determines if two Reviews are equal by comparing their ratings,
     * dates, and Restaurants.
     * @param o Object to compare this Review to
     * @return a boolean representing whether the two Reviews are
     * equal
     */
    @Override
    public boolean equals(Object o) {
        Review r;
        if (o instanceof Review) {
            r = (Review) o;
            return (this.getRating() == r.getRating()
                && this.getDate().equalsIgnoreCase(r.getDate())
                && this.getRestaurant().equals(r.getRestaurant()));
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
     * Returns the rating of this Review
     * @return int value representing the rating of this Review
     */
    public int getRating() {
        return this.rating;
    }
    /**
     * Returns the date that this Review was made
     * @return String of the date of the Review
     */
    public String getDate() {
        return this.date;
    }
    /**
     * Returns the Restaurant that this Review is for
     * @return Restaurant that this Review is for
     */
    public String getRestaurant() {
        return this.res.getName();
    }
    /**
     * Returns the correct output to be printed to a file
     * @return String representation of the Review for a file
     */
    public String toFile() {
        // review rating
        // review date
        String str = rating + "\n"
            + date + "\n";
        return str;
    }
}