package bank.account.AccountTransation;

public abstract class AbstractSingleAccountTransaction
        extends AbstractAccountTransaction
    implements SingleAccountTransaction
{
    private final String accountId;

    public AbstractSingleAccountTransaction(Long transactionId, String accountId, float value)
    {
        super(transactionId, value);
        this.accountId = accountId;
    }

    @Override
    public String getAccountIdentity()
    {
        return this.accountId;
    }
}
