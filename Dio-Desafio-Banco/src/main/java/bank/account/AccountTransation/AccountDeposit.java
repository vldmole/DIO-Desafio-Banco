package bank.account.AccountTransation;

public class AccountDeposit extends AbstractSingleAccountTransaction
{
    public AccountDeposit(Long transactionId, String accountIdentity, float value)
    {
        super(transactionId, accountIdentity, value);
    }
}
