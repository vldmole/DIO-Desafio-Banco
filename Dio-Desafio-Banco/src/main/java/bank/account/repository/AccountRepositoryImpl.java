package bank.account.repository;

import bank.account.AbstractAccount;
import bank.account.SavingAccount;
import bank.account.SimpleAccount;
import bank.account.exception.UnknownAccountTypeException;
import util.observable.Observable;

import java.util.Collection;
import java.util.function.Predicate;

public class AccountRepositoryImpl implements AccountRepository
{
    //----------------------------------------------------------------------------------------------------------
    //--to get access to protected methods----------------------------------------------------------------------
    class ObservableResult<T> extends Observable<T>
    {
        @Override
        protected void fireNext(T data)
        {
            super.fireNext(data);
        }

        @Override
        protected void fireError(Exception exception)
        {
            super.fireError(exception);
        }
    }

    //--------------------------------------------------------------------------------------------
    protected SavingAccountRepository savingAccountRepository = new SavingAccountRepository();
/*
    @Override
    public Observable<Collection<SavingAccount>> readAllSavingAccounts()
    {

        return null;
    }

    @Override
    public Observable<Collection<SavingAccount>> filterSavingAccounts(Predicate<SavingAccount> predicate)
    {
        return null;
    }

    @Override
    public Observable<SavingAccount> findSavingAccountById(String s)
    {
        return null;
    }

    @Override
    public Observable<SavingAccount> add(SavingAccount abstractAccount)
    {
        return null;
    }

    @Override
    public Observable<SavingAccount> delete(String s)
    {
        return null;
    }
*/
    //--------------------------------------------------------------------------------------------
    protected SimpleAccountRepository simpleAccountRepository = new SimpleAccountRepository();
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
    @Override
    public <T> Observable<Collection<AbstractAccount>> readAllAccounts(Class<T> clazz)
    {
        if(SimpleAccount.class == clazz)
            return simpleAccountRepository.readAll();

        if(SavingAccount.class == clazz)
            return savingAccountRepository.readAll();

        throw new UnknownAccountTypeException(clazz.getName());
    }

    @Override
    public <T> Observable<Collection<AbstractAccount>> filterAccounts(Class<T> clazz, Predicate<AbstractAccount> predicate)
    {
        if(SimpleAccount.class == clazz)
            return simpleAccountRepository.filter(predicate);

        if(SavingAccount.class == clazz)
            return savingAccountRepository.filter(predicate);

        throw new UnknownAccountTypeException(clazz.getName());
    }

    @Override
    public <T> Observable<AbstractAccount> findById(Class<T> clazz, String id)
    {
        if(SimpleAccount.class == clazz)
            return simpleAccountRepository.findById(id);

        if(SavingAccount.class == clazz)
            return savingAccountRepository.findById(id);

        throw new UnknownAccountTypeException(clazz.getName());
    }

    @Override
    public <T> Observable<AbstractAccount> add(Class<T> clazz, AbstractAccount account)
    {
        if(SimpleAccount.class == clazz)
            return simpleAccountRepository.add(account);

        if(SavingAccount.class == clazz)
            return savingAccountRepository.add(account);

        throw new UnknownAccountTypeException(clazz.getName());
    }

    @Override
    public <T> Observable<AbstractAccount> deleteById(Class<T> clazz, String id)
    {
        if(SimpleAccount.class == clazz)
            return simpleAccountRepository.deleteById(id);

        if(SavingAccount.class == clazz)
            return savingAccountRepository.deleteById(id);

        throw new UnknownAccountTypeException(clazz.getName());
    }
/*
    //--------------------------------------------------------------------------------------------
    public Observable<Collection<SimpleAccount>> readAllSimpleAccounts()
    {
        return simpleAccountRepository.readAll();
    }

    //--------------------------------------------------------------------------------------------
    public Observable<Collection<SavingAccount>> readAllSavingAccounts()
    {
        return savingAccountRepository.readAll();
    }

    //--------------------------------------------------------------------------------------------
    public Observable<Collection<Account>> readAll()
    {
        final ObservableResult<Collection<Account>> observable = new ObservableResult<>();
        final AtomicBoolean loaded = new AtomicBoolean(false);
        final List<Account> accountList = new ArrayList<>();

        this.readAllSimpleAccounts().subscribe(
            list -> {
            accountList.addAll(list);
            if (loaded.get())
                observable.fireNext(accountList);
            else
                loaded.set(true);
            },
            error -> observable.fireError(error)
        );

        this.readAllSavingAccounts().subscribe(
            list ->
            {
                accountList.addAll(list);
                if (loaded.get())
                    observable.fireNext(accountList);
                else
                    loaded.set(true);
            },
            error->observable.fireError(error)
        );

        return observable;
    }

    //--------------------------------------------------------------------------------------------
    @Override
    public Observable<Collection<Account>> readAll(String accountClassName)
    {
        ObservableResult<Collection<Account>> observable = new ObservableResult<>();
        final List<Account> accountList = new ArrayList<>();

        if(SimpleAccount.class.getName().equalsIgnoreCase(accountClassName))
        {
            simpleAccountRepository.readAll().subscribe(
                list -> {
                    accountList.addAll(list);
                    observable.fireNext(accountList);
                },
                errorMsg -> observable.fireError(errorMsg)
            );
        }

        if(SavingAccount.class.getName().equalsIgnoreCase(accountClassName))
        {
            savingAccountRepository.readAll().subscribe(
                    list ->{
                        accountList.addAll(list);
                        observable.fireNext(accountList);
                    },
                    errorMsg -> observable.fireError(errorMsg)
            );
        }

        return observable;
    }

    //--------------------------------------------------------------------------------------------------------
    public Observable<Collection<SimpleAccount>> filterSimpleAccounts(Predicate<SimpleAccount> predicate)
    {
        return this.simpleAccountRepository.filter(predicate);
    }

    //--------------------------------------------------------------------------------------------------------
    public Observable<Collection<SavingAccount>> filterSavingAccounts(Predicate<SavingAccount> predicate)
    {
        return this.savingAccountRepository.filter(predicate);
    }

    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<Collection<Account>> filter(Predicate<Account> predicate)
    {
        final ObservableResult<Collection<Account>> observable = new ObservableResult<>();

        this.readAll().subscribe(
            list -> {
                List<Account> filteredList = list.stream().filter(predicate).collect(Collectors.toList());
                observable.fireNext(filteredList);
            },
            error -> observable.fireError(error)
        );

        return observable;
    }

    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<Account> findById(String accountId)
    {
        final ObservableResult<Account> observableResult = new ObservableResult<>();
        final AtomicBoolean done = new AtomicBoolean(false);
        final AtomicBoolean err = new AtomicBoolean(false);

        findSimpleAccountById(accountId).subscribe(
            account-> {
                if(!done.getAndSet(true))
                    observableResult.fireNext(account);
            },
            errorMsg -> {
                if(err.getAndSet(true))
                    observableResult.fireError(errorMsg);
            }
        );

        findSavingAccountById(accountId).subscribe(
            account-> {
                if(!done.getAndSet(true))
                    observableResult.fireNext(account);
            },
            errorMsg -> {
                if(err.getAndSet(true))
                    observableResult.fireError(errorMsg);
            }
        );

        return observableResult;
    }
    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<SimpleAccount> findSimpleAccountById(String accountId)
    {
        return this.simpleAccountRepository.findById(accountId);
    }
    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<SavingAccount> findSavingAccountById(String accountId)
    {
        return this.savingAccountRepository.findById(accountId);
    }

    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<? extends Account> save(Account account) throws UnknownAccountTypeException
    {
        if(account.getClass() == SimpleAccount.class)
            return this.addSimpleAccount((SimpleAccount) account);

        if(account.getClass() == SavingAccount.class)
            return this.addSavingAccount((SavingAccount) account);

        throw new UnknownAccountTypeException(account.getClass().toString());
    }

    //--------------------------------------------------------------------------------------------------------
    private Observable<SimpleAccount> addSimpleAccount(SimpleAccount account)
    {
        return simpleAccountRepository.add(account);
    }

    //--------------------------------------------------------------------------------------------------------
    public Observable<SavingAccount> saveSavingAccount(SavingAccount account)
    {
        return savingAccountRepository.add(account);
    }

    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<SimpleAccount> deleteSimpleAccount(SimpleAccount account)
    {
        return simpleAccountRepository.delete(account.getId());
    }

    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<SavingAccount> deleteSavingAccount(SavingAccount account)
    {
        return savingAccountRepository.delete(account.getId());
    }

    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<Account> delete(Account account)
    {
        return null;
    }

    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<Account> deleteById(T accountId)
    {
        return null;
    }

    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<SimpleAccount> deleteSimpleAccountById(String accountId)
    {
        return this.simpleAccountRepository.delete(accountId);
    }

    //--------------------------------------------------------------------------------------------------------
    @Override
    public Observable<Account> deleteSavingAccountById(String accountId)
    {

    }
 */
}
