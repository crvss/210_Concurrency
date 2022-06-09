import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Stock exchange.
 *
 * @author Tom Cresswell 1903451
 */
public class StockExchange {

    private final ArrayList<Client> clients = new ArrayList<>();
    private final HashMap<Company, Float> companies = new HashMap<>();

    /**
     * The empty constructor for StockExchange
     */
    public StockExchange() {

    }

    /**
     * Register a company on the stock exchange
     *
     * @param company        The company to add
     * @param numberOfShares The number of shares the company has
     * @return Boolean check if the company was successfully registered
     */
    public Boolean registerCompany(Company company, float numberOfShares) {
        synchronized (companies) {
            if (!companies.containsKey(company)) {
                companies.put(company, numberOfShares);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Deregister a company from the stock exchange
     *
     * @param company The company to deregister
     * @return Boolean check if the company was successfully deregistered
     */
    public Boolean deregisterCompany(Company company) {
        synchronized (companies) {
            if (companies.containsKey(company)) {
                setPrice(company, (float) 0);
                companies.remove(company);
                for (Client c : clients) {
                    c.removeCompany(company);
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Add a client to the stock exchange
     *
     * @param client The client to add to the stock exchange
     * @return Boolean check if
     */
    public Boolean addClient(Client client) {
        synchronized (clients) {
            if (!clients.contains(client)) {
                clients.add(client);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Remove a client from the stock exchange
     *
     * @param client The client to remove
     * @return Boolean check if the client was succesfully removed
     */
    public Boolean removeClient(Client client) {
        synchronized (clients) {
            if (clients.contains(client)) {
                clients.remove(client);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Return a list of the clients in a stock exchange
     *
     * @return An ArrayList of all clients.
     */
    public ArrayList<Client> getClients() {
        return clients;
    }

    /**
     * Gets a HashMap of companies on the stock exchange.
     *
     * @return the HashMap of companies with the shares they have.
     */
    public HashMap<Company, Float> getCompanies() {
        return companies;
    }

    /**
     * Sets a new price per stock for a specified company.
     *
     * @param company the company
     * @param price   the price
     */
    public void setPrice(Company company, float price) {
        synchronized (companies) {
            if (companies.containsKey(company)) {
                company.setPrice(price);
            }
        }
    }

    /**
     * Change the price per stock by a specified amount for a company.
     *
     * @param company the company
     * @param price   the price
     */
    public void changePriceBy(Company company, float price) {
        synchronized (companies) {
            if (companies.containsKey(company)) {
                float newPrice = company.getPrice() + price;
                company.setPrice(newPrice);
            }
        }
    }
}
