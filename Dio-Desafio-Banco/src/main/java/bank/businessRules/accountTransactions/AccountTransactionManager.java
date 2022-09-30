package bank.businessRules.accountTransactions;

import bank.account.Account;
import bank.account.AccountTransation.*;
import bank.account.AccountTransation.repository.TransactionRepository;
import bank.account.exception.InsufficientAccountBalanceException;
import bank.account.repository.AccountRepository;
import util.observable.Observable;

import java.util.concurrent.atomic.AtomicLong;

public class AccountTransactionManager
{
    //--------------------------------------------------------------------------------------------
    //--to get access to protected methods--------------------------------------------------------
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
    //--------------------------------------------------------------------------------------------

    static AtomicLong counterId = new AtomicLong();
    final AccountRepository accountRepository;
    final TransactionRepository transactionRepository;

    //-----------------------------------------------------------------------------------------------------------
    public
    AccountTransactionManager( AccountRepository accountRepository, TransactionRepository transactionRepository)
    {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    //-----------------------------------------------------------------------------------------------------------
    public
    Observable<AbstractAccountTransaction>
    doWithdraw(Account account, float value)
    {
        ObservableResult<AbstractAccountTransaction> observable = new ObservableResult<>();

        try
        {
            account.withdraw(value);

            AbstractAccountTransaction transaction =
                    new AccountWithdraw(counterId.getAndIncrement(), account.getId(), value);

            transactionRepository.add(transaction).subscribe(
                transact -> observable.fireNext(transact),
                error -> {
                    account.deposit(value);
                    observable.fireError(error);
                }
            );
        }
        catch (InsufficientAccountBalanceException e)
        {
            observable.fireError(e);
        }

        return observable;
    }

    //-----------------------------------------------------------------------------------------------------------
    public
    Observable<AbstractAccountTransaction>
    doWithdraw(String accountId, float value)
    {
        ObservableResult<AbstractAccountTransaction> observable = new ObservableResult<>();

        accountRepository.findById(accountId).subscribe(
            account ->
            {
                doWithdraw(account, value).subscribe(
                        observable::fireNext,
                        observable::fireError
                );
            },
            observable::fireError
        );

        return observable;
    }

    //-----------------------------------------------------------------------------------------------------------
    public
    Observable<AbstractAccountTransaction>  doDeposit(Account account, float value)
    {
        ObservableResult<AbstractAccountTransaction> observable = new ObservableResult<>();

        AbstractAccountTransaction transaction = new AccountDeposit(
                counterId.getAndIncrement(), account.getId(), value );

        transactionRepository.add(transaction).subscribe(
            transact -> {
                observable.fireNext(transact);
                account.deposit(value);
            },
            observable::fireError
        );

        return observable;
    }

    //-----------------------------------------------------------------------------------------------------------
    public
    Observable<AbstractAccountTransaction>
    doDeposit(String accountId, float value)
    {
        ObservableResult<AbstractAccountTransaction> observable = new ObservableResult<>();

        accountRepository.findById(accountId).subscribe(
            account -> {
                doDeposit(account, value).subscribe(
                    transaction -> observable.fireNext(transaction),
                    observable::fireError
                );
            },
            observable::fireError
        );

        return observable;
    }

    //-----------------------------------------------------------------------------------------------------------
    public
    ObservableResult<AccountTransference>
    doTransference(Account fromAccount, Account toAccount, float value)
    {
        ObservableResult<AccountTransference> observable = new ObservableResult<>();

        doWithdraw(fromAccount, value).subscribe(
            withdrawTransaction ->
            {
                doDeposit(toAccount, value).subscribe(
                    depositTransaction ->
                    {
                        AccountTransference transference = new AccountTransference(
                                counterId.getAndIncrement(), fromAccount.getId(), toAccount.getId(), value );
                        transactionRepository.add(transference).subscribe(
                                observable::fireNext,
                                observable::fireError
                        );
                    },
                    observable::fireError
                );
            },
            observable::fireError
        );

        return observable;
    }
}
