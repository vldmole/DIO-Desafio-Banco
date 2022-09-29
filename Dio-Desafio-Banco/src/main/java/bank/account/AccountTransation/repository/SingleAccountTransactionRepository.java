package bank.account.AccountTransation.repository;

import bank.account.AccountTransation.AbstractSingleAccountTransaction;
import util.database.inMemory.AbstractInMemoryRepository;

public class SingleAccountTransactionRepository
        extends AbstractInMemoryRepository<Long, AbstractSingleAccountTransaction>
{
}
