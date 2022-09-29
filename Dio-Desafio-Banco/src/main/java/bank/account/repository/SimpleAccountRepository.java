package bank.account.repository;

import bank.account.AbstractAccount;
import util.database.inMemory.AbstractInMemoryRepository;

public class SimpleAccountRepository
        extends AbstractInMemoryRepository<String, AbstractAccount>
{
}
