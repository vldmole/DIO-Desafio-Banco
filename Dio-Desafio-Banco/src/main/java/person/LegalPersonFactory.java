package person;

import util.validator.Validator;

import java.time.LocalDate;

public class LegalPersonFactory
{
    private Validator cnpjValidator = null;

    public LegalPersonFactory(Validator cnpjValidator)
    {
        this.cnpjValidator = cnpjValidator;
    }

    public LegalPerson create(String name, String cnpj, LocalDate legalDate)
    {
        if(!this.cnpjValidator.isValid(cnpj))
            throw new IllegalArgumentException(String.format("CNPJ is invalid '%s'", cnpj));

        return new LegalPerson(name, cnpj, legalDate);
    }
}
