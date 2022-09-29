package util.database;

public interface Entity<TId extends Comparable<TId>>
{
	TId getId();
}
