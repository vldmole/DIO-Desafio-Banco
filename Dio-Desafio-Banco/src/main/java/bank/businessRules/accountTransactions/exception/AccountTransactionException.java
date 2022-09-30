package bank.businessRules.accountTransactions.exception;

public class AccountTransactionException extends Exception
{
    private AccountTransactionException (String msg)
    {
        super(msg);
    }

    public
    AccountTransactionException doWithdrawException(String accountId, float value, String reason)
    {
        String format = "Exception while doing a withdraw: 'account: %s' 'value: %.2f' 'reason: %s";
        String msg = String.format(format,accountId, value, reason);
        return new AccountTransactionException(msg);
    }

    public
    AccountTransactionException doDepositException(String accountId, float value, String reason)
    {
        String format = "Exception while doing a Deposit: 'account: %s' 'value: %.2f' 'reason: %s";
        String msg = String.format(format,accountId, value, reason);
        return new AccountTransactionException(msg);
    }

    public
    AccountTransactionException doTransferenceException(String fromAccountId, String toAccountId,
                                                         float value, String reason)
    {
        String format = "Exception while doing a Transference: 'from account: %s' 'to account: %s' 'value: %.2f' 'reason: %s";
        String msg = String.format(format,fromAccountId, toAccountId, value, reason);
        return new AccountTransactionException(msg);
    }
}
