package bank.account.repository;

import bank.account.AbstractAccount;
import bank.account.Account;
import bank.account.SavingAccount;
import bank.account.SimpleAccount;
import bank.account.exception.UnknownAccountTypeException;
import util.database.Repository;
import util.observable.Observable;

import java.util.Collection;
import java.util.function.Predicate;

public interface AccountRepository
{
    //-----------------------------------------------------------------------------------------
    <T> Observable<Collection<AbstractAccount>> readAllAccounts(Class<T> clazz);

    <T> Observable<Collection<AbstractAccount>> filterAccounts(Class<T> clazz, Predicate<AbstractAccount> predicate);

    <T> Observable<AbstractAccount> findById(Class<T> clazz, String id);
    Observable<AbstractAccount> findById(String id);

    <T> Observable<AbstractAccount> add(Class<T> clazz, AbstractAccount account);

    <T> Observable<AbstractAccount> deleteById(Class<T> clazz, String id);
}
