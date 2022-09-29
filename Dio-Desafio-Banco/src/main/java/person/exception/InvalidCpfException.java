package person.exception;

public class InvalidCpfException extends Exception
{
    public InvalidCpfException()
    {
        super("Cpf inválido");
    }

    public InvalidCpfException(String message)
    {
        super(message);
    }
}
