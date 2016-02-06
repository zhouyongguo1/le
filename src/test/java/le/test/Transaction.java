package le.test;

import javax.persistence.EntityTransaction;
import java.io.Closeable;
import java.io.IOException;

public class Transaction implements Closeable {

    private EntityTransaction entityTransaction;

    public Transaction(EntityTransaction entityTransaction) {
        this.entityTransaction = entityTransaction;
        this.entityTransaction.begin();
    }

    @Override
    public void close() throws IOException {
        this.entityTransaction.commit();
    }
}
