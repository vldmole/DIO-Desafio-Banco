package bank.account.AccountTransation;

public class AccountWithdraw extends AbstractAccountTransaction
{
    public AccountWithdraw(Long transactionId, String accountId, float value)
    {
        super(transactionId, accountId, OperationType.D, value);
    }
}
