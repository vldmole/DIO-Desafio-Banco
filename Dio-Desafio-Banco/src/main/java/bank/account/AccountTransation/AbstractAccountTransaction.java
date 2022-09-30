package bank.account.AccountTransation;

import util.database.Entity;

import java.time.LocalDateTime;

public abstract class AbstractAccountTransaction
    implements Entity<Long>, AccountTransaction
{
    protected final Long id;
    protected final OperationType operationType;
    protected final String accountId;
    protected final float value;
    protected final LocalDateTime dateTime = LocalDateTime.now();

    public AbstractAccountTransaction(Long id, String accountId, OperationType operationType, float value)
    {
        this.id = id;
        this.accountId = accountId;
        this.operationType = operationType;
        this.value = value;
    }

    @Override
    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public
    String getAccountId()
    {
        return this.accountId;
    }

    @Override
    public
    float getValue()
    {
        return this.value;
    }

    @Override
    public OperationType getOperationType()
    {
        return this.operationType;
    }
}
