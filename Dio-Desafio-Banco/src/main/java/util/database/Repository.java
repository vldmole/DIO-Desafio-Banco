package util.database;

import java.util.Collection;
import java.util.function.Predicate;

import util.observable.Observable;

public interface Repository<TId extends Comparable<TId>, TData extends Entity<TId>>
{
	public Observable<Collection<TData>> readAll();
	
	public Observable<Collection<TData>> filter(Predicate<TData> predicate);
	public Observable<TData> findById(TId id);

	public Observable<TData> add(TData data);

	Observable<TData> deleteById(TId id);
}
