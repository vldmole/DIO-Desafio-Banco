package bank.account;

import bank.account.exception.AccountAlreadyClosedException;
import bank.account.exception.InsufficientAccountBalanceException;
import util.database.Entity;

import java.time.LocalDateTime;

public abstract class AbstractAccount
        implements Entity<String>, Account
{
    protected String id;
    protected String ownerId;
    protected LocalDateTime openDateTime;
    protected LocalDateTime closedDateTime;
    protected float balance = 0.0f;

    //------------------------------------------------------
    public AbstractAccount(String id, String ownerId)
    {
        this.id = id;
        this.ownerId = ownerId;
        openDateTime = LocalDateTime.now();
        closedDateTime = null;
    }

    //------------------------------------------------------
    @Override
    public String getId()
    {
        return id;
    }

    //------------------------------------------------------
    @Override
    public String getOwnerId()
    {
        return ownerId;
    }

    //------------------------------------------------------
    @Override
    public LocalDateTime getOpenDateTime()
    {
        return openDateTime;
    }

    //------------------------------------------------------
    @Override
    public LocalDateTime getClosedDateTime()
    {
        return closedDateTime;
    }

    //------------------------------------------------------
    @Override
    public void close() throws AccountAlreadyClosedException
    {
        if(closedDateTime != null)
            throw new AccountAlreadyClosedException("This account is already closed!");
    }

    //------------------------------------------------------
    @Override
    public float getBalance()
    {
        return this.balance;
    }

    //------------------------------------------------------
    @Override
    public void deposit(float value)
    {
        this.balance += value;
    }

    //------------------------------------------------------
    @Override
    public void withdraw(float value) throws InsufficientAccountBalanceException
    {
        if(this.balance < value)
            throw new InsufficientAccountBalanceException(this.getId(), this.getBalance(), value);

        this.balance -= value;
    }
}
