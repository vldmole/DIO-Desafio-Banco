package bank.account.AccountTransation;

import bank.account.AbstractAccount;

public class AccountTransference
        extends AbstractAccountTransaction
    implements AccountTransferenceTransaction
{
    private final String toAccountId;
    public AccountTransference(Long id, String fromAccountId, String toAccountId, float value)
    {
        super(id, fromAccountId, OperationType.D, value);
        this.toAccountId = toAccountId;
    }



    @Override
    public String getOriginAccount()
    {
        return null;
    }

    @Override
    public String getDestinationAccount()
    {
        return null;
    }
}
