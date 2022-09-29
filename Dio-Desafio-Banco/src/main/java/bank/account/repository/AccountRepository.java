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
/*
    Observable<Collection<SimpleAccount>> readAllSimpleAccounts();
    Observable<Collection<SavingAccount>> readAllSavingAccounts();

    Observable<Collection<SimpleAccount>> filterSimpleAccounts(Predicate<SimpleAccount> predicate);
    Observable<Collection<SavingAccount>> filterSavingAccounts(Predicate<SavingAccount> predicate);

    Observable<SimpleAccount> findSimpleAccountById(String id);
    Observable<SavingAccount> findSavingAccountById(String id);

    Observable<SimpleAccount> addSimpleAccount(SimpleAccount data);
    Observable<SavingAccount> addSimpleAccount(SavingAccount data);

    Observable<SimpleAccount> deleteSimpleAccountById(String id);
    Observable<SavingAccount> deleteSavingAccountById(String id);
*/
    /*
        @Override
        public Observable<Collection<SimpleAccount>> readAllSimpleAccounts()
        {

            return null;
        }

        @Override
        public Observable<Collection<SimpleAccount>> filterSimpleAccounts(Predicate<SimpleAccount> predicate)
        {
            return null;
        }

        @Override
        public Observable<SimpleAccount> findSimpleAccountById(String s)
        {
            return null;
        }

        @Override
        public Observable<SimpleAccount> addSimpleAccount(SimpleAccount account)
        {
            return null;
        }

        @Override
        public Observable<SimpleAccount> deleteSimpleAccountById(String s)
        {
            return null;
        }
    */
    //-----------------------------------------------------------------------------------------
    <T> Observable<Collection<AbstractAccount>> readAllAccounts(Class<T> clazz);

    <T> Observable<Collection<AbstractAccount>> filterAccounts(Class<T> clazz, Predicate<AbstractAccount> predicate);

    <T> Observable<AbstractAccount> findById(Class<T> clazz, String id);

    <T> Observable<AbstractAccount> add(Class<T> clazz, AbstractAccount account);

    <T> Observable<AbstractAccount> deleteById(Class<T> clazz, String id);

    /*
    public Observable<Collection<Account>> readAll();
    public Observable<Collection<Account>> readAll(String accountClassName);

    public Observable<Collection<Account>> filter(Predicate<Account> predicate);
    public Observable<Account> findById(String accountId);
    public Observable<SimpleAccount> findSimpleAccountById(String accountId);
    public Observable<SavingAccount> findSavingAccountById(String accountId);

    public Observable<? extends Account> save(Account account) throws UnknownAccountTypeException;

    public Observable<Account> delete(Account account);
    public Observable<SimpleAccount> deleteSimpleAccount(SimpleAccount account);
    public Observable<SavingAccount> deleteSavingAccount(SavingAccount account);
    public Observable<Account> deleteById(String accountId);
    public Observable<SimpleAccount> deleteSimpleAccountById(String accountId);
    public Observable<SavingAccount> deleteSavingAccountById(String accountId);

     */
}
