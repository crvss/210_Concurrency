/**
 * Application Class
 *
 * @author Tom Cresswell 1903451
 */
public class Application {

    public static void main(String[] args) throws InterruptedException {
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        Client client4 = new Client();
        Client client5 = new Client();

        client1.deposit(1000);
        client2.deposit(19000);
        client3.deposit(23000);
        client4.deposit(45000);
        client5.deposit(8000);

        Thread t1 = new Thread(client1);
        Thread t2 = new Thread(client2);
        Thread t3 = new Thread(client3);
        Thread t4 = new Thread(client4);
        Thread t5 = new Thread(client5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
    }
}
