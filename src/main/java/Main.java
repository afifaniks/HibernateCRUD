import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Bank.class);
        configuration.addAnnotatedClass(Client.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

        SessionFactory factory = configuration.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        // CREATE DATA
        Bank bank = new Bank();
        bank.setName("Babe Bank");
        session.save(bank);

        Bank bank2 = new Bank();
        bank2.setName("Blue Chips Bank");
        session.save(bank2);

        Client client = new Client();
        client.setId(5);
        client.setName("New Client");
        session.save(client);

        transaction.commit();

        // RETRIEVE DATA
        transaction = session.beginTransaction();
        Bank bank1 = session.get(Bank.class, 1);
        System.out.println(bank1.toString());

        // UPDATE DATA
        bank1.setName("Changed Name");
        transaction.commit();

        // DELETE DATA
        transaction = session.beginTransaction();
        bank2 = session.get(Bank.class, 2);
        session.delete(bank2);

        transaction.commit();

        session.close();
    }
}
