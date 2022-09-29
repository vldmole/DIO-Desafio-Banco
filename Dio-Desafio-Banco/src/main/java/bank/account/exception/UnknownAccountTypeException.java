package bank.account.exception;

public class UnknownAccountTypeException extends RuntimeException
{
    public UnknownAccountTypeException(String accountType)
    {
        super(String.format("The Type '%s' is uncknown",accountType));
    }
}
