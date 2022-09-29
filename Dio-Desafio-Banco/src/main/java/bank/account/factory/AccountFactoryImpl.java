package bank.account.factory;

import bank.account.AbstractAccount;
import bank.account.Account;
import bank.account.SavingAccount;
import bank.account.SimpleAccount;
import bank.account.exception.UnknownAccountTypeException;
import bank.account.repository.AccountRepository;
import person.AbstractPerson;

import java.util.concurrent.atomic.AtomicLong;

public class AccountFactoryImpl
{
    static
    AtomicLong accountNumber = new AtomicLong(1);

    protected final AccountRepository accountRepository;

    //----------------------------------------------------------------------------------
    public AccountFactoryImpl(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    //----------------------------------------------------------------------------------
    static public
    String getNextAccountId()
    {
        return  "" + accountNumber.incrementAndGet();
    }

    //----------------------------------------------------------------------------------
    private Account createSimpleAccount(AbstractPerson owner)
    {
        String accountId = getNextAccountId();
        String ownerId = "" + owner.getId();

        AbstractAccount account = new SimpleAccount(accountId, ownerId);
        accountRepository.add(SimpleAccount.class, account);
        return account;
    }

    //----------------------------------------------------------------------------------
    private
    Account createSavingAccount(AbstractPerson owner)
    {
        String accountId = getNextAccountId();
        String ownerId = "" + owner.getId();

        AbstractAccount account = new SavingAccount(accountId, ownerId);
        accountRepository.add(SimpleAccount.class, account);
        return account;
    }

    //----------------------------------------------------------------------------------
    public
    Account createAccount(String accountType, AbstractPerson owner)
    {
        if("SimpleAccount".equalsIgnoreCase(accountType))
            return createSimpleAccount(owner);

        if("SavingAccount".equalsIgnoreCase(accountType))
            return createSavingAccount(owner);

        throw new UnknownAccountTypeException(accountType);
    }
}
