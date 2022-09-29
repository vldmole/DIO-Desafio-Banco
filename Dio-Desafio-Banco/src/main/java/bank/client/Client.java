package bank.client;

import person.AbstractPerson;
import util.database.Entity;

import java.time.LocalDate;

public class Client implements Entity<String>
{
    private String id;
    private AbstractPerson person;
    private LocalDate sinceOf = LocalDate.now();

    //--------------------------------------------------------
    public Client(String id, AbstractPerson person)
    {
        this.id = id;
        this.person = person;
    }

    //--------------------------------------------------------
    @Override
    public String getId()
    {
        return null;
    }

    //--------------------------------------------------------
    public AbstractPerson getPerson()
    {
        return person;
    }

    //--------------------------------------------------------
    public LocalDate getSinceOf()
    {
        return sinceOf;
    }
}
