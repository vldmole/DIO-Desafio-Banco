package bank.account.factory;

import bank.account.Account;
import person.AbstractPerson;

public interface AccountFactory
{
    Account createAccount(String accountType, AbstractPerson owner);
}
