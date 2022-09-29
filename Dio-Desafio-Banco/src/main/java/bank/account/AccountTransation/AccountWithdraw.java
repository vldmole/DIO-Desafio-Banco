package bank.account.AccountTransation;

public class AccountWithdraw extends AbstractSingleAccountTransaction
{
    public AccountWithdraw(String accountIdentity, float value)
    {
        super(accountIdentity, value);
    }
}
