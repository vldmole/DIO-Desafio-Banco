package bank.account.AccountTransation.repository;


import bank.account.AccountTransation.AbstractAccountTransaction;
import bank.account.AccountTransation.AbstractDoubleAccountTransaction;
import bank.account.AccountTransation.AbstractSingleAccountTransaction;

import util.observable.Observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionRepository
{
    //----------------------------------------------------------------------------------------------------------
    //--to get access to protected methods----------------------------------------------------------------------
    static 
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
    //----------------------------------------------------------------------------------------------------------
    protected final
    SingleAccountTransactionRepository singleAccountTransactionRepository =
            new SingleAccountTransactionRepository();
    protected final
    DoubleAccountTransactionRepository doubleAccountTransactionRepository =
            new DoubleAccountTransactionRepository();

    //----------------------------------------------------------------------------------------------------------
    public
    Observable<Collection<AbstractSingleAccountTransaction>> readAllSingleAccountTransactions()
    {
        return singleAccountTransactionRepository.readAll();
    }

    //----------------------------------------------------------------------------------------------------------
    public
    Observable<Collection<AbstractDoubleAccountTransaction>> readAllDoubleAccountTransactions()
    {
        return doubleAccountTransactionRepository.readAll();
    }

    //----------------------------------------------------------------------------------------------------------
    public
    Observable<Collection<AbstractAccountTransaction>> readAllTransactions()
    {
        final List<AbstractAccountTransaction> accountList = new ArrayList<>();
        final ObservableResult<Collection<AbstractAccountTransaction>> observable = new ObservableResult<>();
        final AtomicBoolean loaded = new AtomicBoolean(false);

        this.readAllSingleAccountTransactions().subscribe(
            list -> {
                accountList.addAll(list);
                if (loaded.get())
                    observable.fireNext(accountList);
                else
                    loaded.set(true);
            },
            observable::fireError
        );

        this.readAllDoubleAccountTransactions().subscribe(
            list ->
            {
                accountList.addAll(list);
                if (loaded.get())
                    observable.fireNext(accountList);
                else
                    loaded.set(true);
            },
            observable::fireError
        );

        return observable;
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<Collection<AbstractSingleAccountTransaction>>
    filterSingleAccountTransactions(Predicate<AbstractSingleAccountTransaction> predicate)
    {
        return this.singleAccountTransactionRepository.filter(predicate);
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<Collection<AbstractDoubleAccountTransaction>>
    filterDoubleAccountTransactions(Predicate<AbstractDoubleAccountTransaction> predicate)
    {
        return this.doubleAccountTransactionRepository.filter(predicate);
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<Collection<AbstractAccountTransaction>> filter(Predicate<AbstractAccountTransaction> predicate)
    {
        final ObservableResult<Collection<AbstractAccountTransaction>> observable = new ObservableResult<>();

        this.readAllTransactions().subscribe(
            list -> {
                List<?> filteredList = list.stream().filter(predicate).toList();
                observable.fireNext((Collection<AbstractAccountTransaction>) filteredList);
            },
            observable::fireError
        );

        return observable;
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<AbstractSingleAccountTransaction> addSimpleAccount(AbstractSingleAccountTransaction account)
    {
        return singleAccountTransactionRepository.add(account);
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<AbstractDoubleAccountTransaction> addSavingAccount(AbstractDoubleAccountTransaction account)
    {
        return doubleAccountTransactionRepository.add(account);
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<AbstractSingleAccountTransaction> deleteSimpleAccount(AbstractSingleAccountTransaction account)
    {
        return singleAccountTransactionRepository.deleteById(account.getId());
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<AbstractDoubleAccountTransaction> deleteSavingAccount(AbstractDoubleAccountTransaction account)
    {
        return doubleAccountTransactionRepository.deleteById(account.getId());
    }
}