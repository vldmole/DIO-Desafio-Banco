package bank.account.AccountTransation.repository;

import bank.account.AccountTransation.AbstractAccountTransaction;
import util.database.inMemory.AbstractInMemoryRepository;

public class AccountTransactionRepository
        extends AbstractInMemoryRepository<Long, AbstractAccountTransaction>
{
}
