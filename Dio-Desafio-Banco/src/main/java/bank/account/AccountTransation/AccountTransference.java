package bank.account.AccountTransation;

import bank.account.AbstractAccount;

public class AccountTransference extends AbstractDoubleAccountTransaction
{
    public AccountTransference(Long id, String fromAccountId, String toAccountId, float value)
    {
        super(id, fromAccountId, toAccountId, value);
    }
}
