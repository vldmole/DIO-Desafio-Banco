package bank.account.repository;

import bank.account.AbstractAccount;
import util.database.inMemory.AbstractInMemoryRepository;

public class SavingAccountRepository
        extends AbstractInMemoryRepository<String, AbstractAccount>
{

}
