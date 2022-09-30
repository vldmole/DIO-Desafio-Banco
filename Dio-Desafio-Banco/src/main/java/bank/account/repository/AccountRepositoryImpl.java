package bank.account.repository;

import bank.account.AbstractAccount;
import bank.account.SavingAccount;
import bank.account.SimpleAccount;
import bank.account.exception.UnknownAccountTypeException;
import util.observable.Observable;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class AccountRepositoryImpl implements AccountRepository
{
    //----------------------------------------------------------------------------------------------------------
    //--to get access to protected methods----------------------------------------------------------------------
    class ObservableResult<T> extends Observable<T>
    {
        //-----------------------------------------------------------------------------------------
    @Override
        protected void fireNext(T data)
        {
            super.fireNext(data);
        }

        //-----------------------------------------------------------------------------------------
    @Override
        protected void fireError(Exception exception)
        {
            super.fireError(exception);
        }
    }

    //--------------------------------------------------------------------------------------------
    protected SavingAccountRepository savingAccountRepository = new SavingAccountRepository();
    protected SimpleAccountRepository simpleAccountRepository = new SimpleAccountRepository();

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

    //-----------------------------------------------------------------------------------------
    @Override
    public <T> Observable<Collection<AbstractAccount>> filterAccounts(Class<T> clazz,
                                                          Predicate<AbstractAccount> predicate)
    {
        if(SimpleAccount.class == clazz)
            return simpleAccountRepository.filter(predicate);

        if(SavingAccount.class == clazz)
            return savingAccountRepository.filter(predicate);

        throw new UnknownAccountTypeException(clazz.getName());
    }

    //-----------------------------------------------------------------------------------------
    @Override
    public <T> Observable<AbstractAccount> findById(Class<T> clazz, String id)
    {
        if(SimpleAccount.class == clazz)
            return simpleAccountRepository.findById(id);

        if(SavingAccount.class == clazz)
            return savingAccountRepository.findById(id);

        throw new UnknownAccountTypeException(clazz.getName());
    }

    //-----------------------------------------------------------------------------------------
    @Override
    public Observable<AbstractAccount> findById(String id)
    {
        ObservableResult<AbstractAccount> observable = new ObservableResult<>();

        AtomicInteger countDone = new AtomicInteger(0);
        final int DONE_WHEN_COUNT = 2;
        final AbstractAccount[] doneAccount = {null};

        this.savingAccountRepository.findById(id).subscribe(
            account -> {
                doneAccount[0] = account;
                if(countDone.incrementAndGet() == DONE_WHEN_COUNT)
                    observable.fireNext(doneAccount[0]);
            },
            error ->{
                if(countDone.incrementAndGet() == DONE_WHEN_COUNT)
                {
                    if (doneAccount[0] != null)
                        observable.fireNext(doneAccount[0]);
                    else
                        observable.fireError(error);
                }
            }
        );

        this.simpleAccountRepository.findById(id).subscribe(
            account -> {
                doneAccount[0] = account;
                if(countDone.incrementAndGet() == DONE_WHEN_COUNT)
                    observable.fireNext(doneAccount[0]);
            },
            error ->{
                if(countDone.incrementAndGet() == DONE_WHEN_COUNT)
                {
                    if (doneAccount[0] != null)
                        observable.fireNext(doneAccount[0]);
                    else
                        observable.fireError(error);
                }
            }
        );

        return observable;
    }

    //-----------------------------------------------------------------------------------------
    @Override
    public <T> Observable<AbstractAccount> add(Class<T> clazz, AbstractAccount account)
    {
        if(SimpleAccount.class == clazz)
            return simpleAccountRepository.add(account);

        if(SavingAccount.class == clazz)
            return savingAccountRepository.add(account);

        throw new UnknownAccountTypeException(clazz.getName());
    }

    //-----------------------------------------------------------------------------------------
    @Override
    public <T> Observable<AbstractAccount> deleteById(Class<T> clazz, String id)
    {
        if(clazz == SimpleAccount.class)
            return this.simpleAccountRepository.deleteById(id);

        if(clazz == SavingAccount.class)
            return this.savingAccountRepository.deleteById(id);

        throw new UnknownAccountTypeException(clazz.getName());
    }
}
