package person;

import util.database.Entity;

import java.time.LocalDate;

public class AbstractPerson
       implements Entity<String>
{
    protected String id;
    protected final String name;
    protected final LocalDate startDate;

    //-----------------------------------------------------------------------
    public String getName()
    {
        return name;
    }

    //-----------------------------------------------------------------------
    public LocalDate getStartDate()
    {
        return startDate;
    }

    //-----------------------------------------------------------------------
    public AbstractPerson(String id, String name, LocalDate startDate)
    {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
    }

    //-----------------------------------------------------------------------
    @Override
    public String getId()
    {
        return null;
    }
}
