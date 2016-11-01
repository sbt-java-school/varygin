package lesson24.db.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TransactionRequest {
    final PlatformTransactionManager transactionManager;

    @Autowired
    public TransactionRequest(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Transactional
    public void action(Runnable action) {
        action.run();
    }
}
