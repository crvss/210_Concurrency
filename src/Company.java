/**
 * The type Company.
 *
 * @author Tom Cresswell 1903451
 */
public class Company {
    private String name;
    private float numberOfShares;
    private float availableNumberOfShares;
    private float price;

    /**
     * Instantiates a new, empty Company.
     */
    public Company() {

    }

    public Company(String name) {
        this.name = name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public synchronized void setName(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets total number of shares.
     *
     * @param number the number
     */
    public synchronized void setTotalNumberOfShares(float number) {
        this.numberOfShares = number;
    }

    /**
     * Gets total number of shares.
     *
     * @return the total number of shares
     */
    public float getTotalNumberOfShares() {
        return this.numberOfShares;
    }

    /**
     * Sets available number of shares.
     *
     * @param availableNumberOfShares the available number of shares
     */
    public synchronized void setAvailableNumberOfShares(float availableNumberOfShares) {
        this.availableNumberOfShares = availableNumberOfShares;
    }

    /**
     * Gets available number of shares.
     *
     * @return the available number of shares
     */
    public float getAvailableNumberOfShares() {
        return availableNumberOfShares;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public synchronized void setPrice(float price) {
        this.price = price;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public float getPrice() {
        return price;
    }


}
