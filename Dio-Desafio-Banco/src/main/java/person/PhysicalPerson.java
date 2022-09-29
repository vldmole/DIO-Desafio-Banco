package person;

import java.time.LocalDate;

public class PhysicalPerson extends AbstractPerson
{
    PhysicalPerson(String name, LocalDate birthDate, String cpf)
    {
        super(cpf, name, birthDate);
    }
}
