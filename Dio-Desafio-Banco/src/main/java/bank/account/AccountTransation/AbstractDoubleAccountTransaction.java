package bank.account.AccountTransation;

public abstract class AbstractDoubleAccountTransaction
        extends AbstractAccountTransaction
    implements DoubleAccountTransaction
{
    protected final String originAccountId;
    protected final String destinationAccountId;

    public AbstractDoubleAccountTransaction(Long id, String fromAccountId, String toAccountId, float value)
    {
        super(id, value);
        this.originAccountId = fromAccountId;
        this.destinationAccountId = toAccountId;
    }

    @Override
    public String getOriginAccount()
    {
        return this.originAccountId;
    }

    @Override
    public String getDestinationAccount()
    {
        return this.destinationAccountId;
    }
}
