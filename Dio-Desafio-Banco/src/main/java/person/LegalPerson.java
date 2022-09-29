package person;

import java.time.LocalDate;

public class LegalPerson extends AbstractPerson
{
    LegalPerson(String name, String cnpj, LocalDate legalDate)
    {
        super(cnpj, name, legalDate);
    }
}
