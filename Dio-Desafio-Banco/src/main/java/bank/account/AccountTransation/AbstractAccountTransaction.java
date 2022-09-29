package bank.account.AccountTransation;

import util.database.Entity;

import java.time.LocalDateTime;

public abstract class AbstractAccountTransaction
    implements Entity<Long>, AccountTransaction
{
    protected final Long id;
    protected final float value;
    protected final LocalDateTime dateTime = LocalDateTime.now();

    public AbstractAccountTransaction(Long id, float value)
    {
        this.id = id;
        this.value = value;
    }

    @Override
    public float getValue()
    {
        return value;
    }

    @Override
    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    @Override
    public Long getId()
    {
        return this.id;
    }
}
