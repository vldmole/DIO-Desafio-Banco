package bank.account;

import bank.account.exception.AccountAlreadyClosedException;
import bank.account.exception.InsufficientAccountBalanceException;

import java.io.StringBufferInputStream;
import java.time.LocalDateTime;

public interface Account extends Comparable<Account>
{
    String getId();
    String getOwnerId();
    LocalDateTime getOpenDateTime();
    LocalDateTime getClosedDateTime();
    void close() throws AccountAlreadyClosedException;
    float getBalance();
    void deposit(float value);
    void withdraw(float value) throws InsufficientAccountBalanceException;

    @Override
    default int compareTo(Account account)
    {
        return getId().compareTo(account.getId());
    }
}
