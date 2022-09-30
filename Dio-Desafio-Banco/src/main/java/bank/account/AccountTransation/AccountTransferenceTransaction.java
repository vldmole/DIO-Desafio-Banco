package bank.account.AccountTransation;

public interface AccountTransferenceTransaction
{
    Long getId();
    String getOriginAccount();
    String getDestinationAccount();
}
