package bank.account.AccountTransation.repository;


import bank.account.AccountTransation.AbstractAccountTransaction;

import bank.account.AccountTransation.AccountTransference;
import bank.account.AccountTransation.AccountTransferenceTransaction;
import util.observable.Observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

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
    AccountTransactionRepository accountTransactionRepository =
            new AccountTransactionRepository();
    protected final
    AccountTransferenceRepository accountTransferenceRepository =
            new AccountTransferenceRepository();

    //----------------------------------------------------------------------------------------------------------
    public
    Observable<Collection<AbstractAccountTransaction>> readAllAccountTransactions()
    {
        return accountTransactionRepository.readAll();
    }

    //----------------------------------------------------------------------------------------------------------
    public
    Observable<Collection<AccountTransference>> readAllDoubleAccountTransactions()
    {
        return accountTransferenceRepository.readAll();
    }

    //----------------------------------------------------------------------------------------------------------
    public
    Observable<Collection<AbstractAccountTransaction>> readAllTransactions()
    {
        final List<AbstractAccountTransaction> accountList = new ArrayList<>();
        final ObservableResult<Collection<AbstractAccountTransaction>> observable = new ObservableResult<>();
        final AtomicBoolean loaded = new AtomicBoolean(false);

        this.readAllAccountTransactions().subscribe(
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
    Observable<Collection<AbstractAccountTransaction>>
    filterSingleAccountTransactions(Predicate<AbstractAccountTransaction> predicate)
    {
        return this.accountTransactionRepository.filter(predicate);
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<Collection<AccountTransference>>
    filterAccountTransferences(Predicate<AccountTransference> predicate)
    {
        return this.accountTransferenceRepository.filter(predicate);
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
    Observable<AbstractAccountTransaction>
    add(AbstractAccountTransaction account)
    {
        return accountTransactionRepository.add(account);
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<AccountTransference>
    add(AccountTransference account)
    {
        return accountTransferenceRepository.add(account);
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<AbstractAccountTransaction>
    deleteSimpleAccountTransaction(AbstractAccountTransaction account)
    {
        return accountTransactionRepository.deleteById(account.getId());
    }

    //--------------------------------------------------------------------------------------------------------
    public
    Observable<AbstractAccountTransaction>
    deleteSavingAccountTransaction(AbstractAccountTransaction account)
    {
        return accountTransactionRepository.deleteById(account.getId());
    }
}