package person;

import util.validator.Validator;

import java.time.LocalDate;

public class PhysicalPersonFactory
{
    private Validator cpfValidator = null;

    public PhysicalPersonFactory(Validator cpfValidator)
    {
        this.cpfValidator = cpfValidator;
    }

    public PhysicalPerson create(String name, LocalDate birthDate, String cpf)
    {
        if(!this.cpfValidator.isValid(cpf))
            throw new IllegalArgumentException(String.format("cpf is invalid '%s'", cpf));

        return new PhysicalPerson(name,birthDate, cpf);
    }
}
