package bank.account.AccountTransation.repository;

import bank.account.AccountTransation.AbstractDoubleAccountTransaction;
import util.database.inMemory.AbstractInMemoryRepository;

public class DoubleAccountTransactionRepository
    extends AbstractInMemoryRepository<Long, AbstractDoubleAccountTransaction>
{
}
