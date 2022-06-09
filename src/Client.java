import java.util.HashMap;

/**
 * The Client type.
 *
 * @author Tom Cresswell 1903451
 */
public class Client implements Runnable {
    private StockExchange stockExchange = new StockExchange();
    private Company company = new Company();
    private int sleepScaler = 10000;
    private float balance;
    private HashMap<Company, Float> shares = new HashMap<>();

    /**
     * The empty constructor for Client
     */
    public Client() {

    }

    /**
     * Returns a HashMap of shares the client owns
     *
     * @return The HashMap shares
     */
    public HashMap<Company, Float> getStocks() {
        return shares;
    }

    /**
     * Sets the stocks for the client shares
     *
     * @param company        The company to set the stocks for
     * @param numberOfShares The number of shares in the company
     */
    public void setStocks(Company company, float numberOfShares) {
        synchronized (shares) {
            shares.put(company, numberOfShares);
        }
    }

    /**
     * Allows a client to buy shares from a registered company, if they have enough money
     *
     * @param company        The company to buy stocks from
     * @param numberOfShares The number of shares the client is buying
     * @return Boolean check if buying the stocks was successful
     */
    public Boolean buy(Company company, float numberOfShares) {
        synchronized (shares) {
            if (shares.containsKey(company)) {
                if (company.getPrice() * numberOfShares <= balance && company.getAvailableNumberOfShares() >= numberOfShares) {
                    float companyShares = shares.get(company);
                    this.balance -= company.getPrice() * numberOfShares;
                    shares.replace(company, companyShares + numberOfShares);
                    company.setAvailableNumberOfShares(company.getAvailableNumberOfShares() - numberOfShares);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Allows a client to sell a number of shares of a company they own shares in
     *
     * @param company        The company the client is selling shares from
     * @param numberOfShares The number of shares to sell
     * @return Boolean check if selling the shares is successful
     */
    public Boolean sell(Company company, float numberOfShares) {
        synchronized (shares) {
            if (shares.containsKey(company)) {
                if (shares.get(company) >= numberOfShares) {
                    float companyShares = shares.get(company);
                    this.balance += company.getPrice() * numberOfShares;
                    shares.replace(company, companyShares - numberOfShares);
                    company.setAvailableNumberOfShares(company.getAvailableNumberOfShares() + numberOfShares);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Buy shares when they reach a specified limit.
     *
     * @param company        the company
     * @param numberOfShares the number of shares
     * @param limit          the limit
     * @return Check if the transaction was successful
     */
    public Boolean buyLow(Company company, float numberOfShares, float limit) {
        synchronized (shares) {
            try {
                while (company.getPrice() > limit) {
                    wait(5000);
                }
                buy(company, numberOfShares);
                return true;
            } catch (InterruptedException e1) {
                return false;
            }
        }
    }

    /**
     * Sell shares when they reach a specified high limit
     *
     * @param company        the company
     * @param numberOfShares the number of shares
     * @param limit          the limit
     * @return Check if the transaction was successful
     */
    public Boolean sellHigh(Company company, float numberOfShares, float limit) {
        synchronized (shares) {
            try {
                while (company.getPrice() < limit) {
                    wait(5000);
                }
                sell(company, numberOfShares);
                return true;
            } catch (InterruptedException e) {
                return false;
            }
        }
    }

    /**
     * Deposit an amount in the client's balance.
     *
     * @param amount the amount
     * @return Check if the deposit was successful
     */
    public synchronized Boolean deposit(float amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Withdraw an amount from the client's balance.
     *
     * @param amount the amount
     * @return Check if the withdrawal was successful
     */
    public synchronized Boolean withdraw(float amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove a specified company from the shares list.
     *
     * @param company The company
     */
    public void removeCompany(Company company) {
        synchronized (shares) {
            if (shares.containsKey(company)) {
                shares.remove(company);
            }
        }
    }

    /**
     * toString method for easy readability in the stdout.
     *
     * @return The client as a string.
     */
    public String toString() {
        String temp = Float.toString(balance);
        return "Client with balance: " + temp;
    }

    @Override
    public void run() {
        try {

            stockExchange.addClient(this);
            double randNo = Math.random();
            System.out.println(this.toString()+ " is joining the stock exchange.");
            Thread.sleep((long) (randNo * sleepScaler));
            System.out.println(this.toString() + " is added.");
            company.setTotalNumberOfShares(1000);
            company.setAvailableNumberOfShares(900);
            company.setPrice(5);
            this.setStocks(company, 100);
            this.buy(company, 100);
            System.out.println(this.toString() + " has successfully bought shares.");
            stockExchange.removeClient(this);

        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace().toString());
            return;
        }
    }
}
