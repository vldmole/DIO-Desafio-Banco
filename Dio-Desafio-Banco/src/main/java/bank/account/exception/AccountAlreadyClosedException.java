package bank.account.exception;

public class AccountAlreadyClosedException extends Exception
{
    public AccountAlreadyClosedException(String message)
    {
        super(message);
    }
}
