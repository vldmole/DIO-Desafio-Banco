package bank.account.exception;

public class InsufficientAccountBalanceException extends Exception
{
    public InsufficientAccountBalanceException(String accountId, float currentBalance, float withdrawValue)
    {
        super(  String.format(
                "Error while doing a withdrawn: 'account: %s' 'balance: %.2f' 'withdraw: %.2f",
                accountId, currentBalance, withdrawValue )
        );
    }
}
