package person.exception;

public class InvalidCnpjException extends Exception
{
    public InvalidCnpjException()
    {
        super("Cnpf inválido");
    }

    public InvalidCnpjException(String message)
    {
        super(message);
    }
}
