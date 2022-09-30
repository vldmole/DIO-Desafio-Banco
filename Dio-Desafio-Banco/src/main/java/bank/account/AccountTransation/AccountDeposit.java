package bank.account.AccountTransation;

public class AccountDeposit
        extends AbstractAccountTransaction
{
    public AccountDeposit(Long transactionId, String accountIdentity, float value)
    {
        super(transactionId, accountIdentity, OperationType.C, value);
    }
}
