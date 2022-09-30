package util.database.inMemory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import util.database.Entity;
import util.database.Repository;
import util.observable.Observable;

public abstract class AbstractInMemoryRepository<TId extends Comparable<TId>, TData extends Entity<TId>>
	implements Repository<TId, TData>
{
	//----------------------------------------------------------------------------------------------------------
	//--to get access to protected methods----------------------------------------------------------------------
	static
	class ObservableResult<T> extends Observable<T>
	{
		@Override
		protected void fireNext(T data)
		{
			super.fireNext(data);
		}
		
		@Override
		protected void fireError(Exception exception)
		{
			super.fireError(exception);
		}
	}
	
	//----------------------------------------------------------------------------------------------------------
	static ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(3);	
	protected List<TData> listData = new ArrayList<>();
	private final float ERROR_RATE=0.01F;
	private final long DELAY_TIME = 1000;
	
	//----------------------------------------------------------------------------------------------------------
	protected void load(TData[] arrayData)
	{
		Collections.addAll(listData, arrayData);
	}
	
	//----------------------------------------------------------------------------------------------------------
	@Override
	public Observable<Collection<TData>> readAll()
	{
		
		ObservableResult<Collection<TData>> observable = new ObservableResult<>();
		
		executor.schedule(()->
		{
			if (Math.random() > ERROR_RATE)
				observable.fireNext(this.listData);
			else
				observable.fireError(new RuntimeException("An unexpected error ocurred while reading all!"));
		
		}, DELAY_TIME, TimeUnit.MILLISECONDS);
		
		return observable;
	}

	//----------------------------------------------------------------------------------------------------------
	@Override
	public Observable<Collection<TData>> filter(Predicate<TData> predicate)
	{
		ObservableResult<Collection<TData>> observable = new ObservableResult<>();
		
		executor.schedule(()->
		{
			if (Math.random() > ERROR_RATE)
				observable.fireNext(listData.stream().filter(predicate).collect(Collectors.toList()));
			else
				observable.fireError(new RuntimeException("An unexpected error ocurred while filtering!"));
			
		}, DELAY_TIME, TimeUnit.MILLISECONDS);
		
		return observable;
	}

	//----------------------------------------------------------------------------------------------------------
	@Override
	public Observable<TData> findById(TId id)
	{
		final ObservableResult<TData> observable = new ObservableResult<>();
		final Predicate<TData> predicate = data -> data.getId() == id;

		executor.schedule(()->
		{
			if (Math.random() > ERROR_RATE)
			{
				List<TData> lst = listData.stream().filter(predicate).toList();
				if( lst.size() > 0 )
					observable.fireNext(lst.get(0));
				else
					observable.fireError(new RuntimeException("Not Found"));
			}
			else
				observable.fireError(new RuntimeException("An unexpected error occurred while filtering!"));

		}, DELAY_TIME, TimeUnit.MILLISECONDS);

		return observable;
	}

	//----------------------------------------------------------------------------------------------------------
	@Override
	public Observable<TData> add(TData data)
	{
		ObservableResult<TData> observable = new ObservableResult<>();
		
		executor.schedule(()->
		{
			if (Math.random() > ERROR_RATE)
			{
				this.listData.add(data);
				observable.fireNext(data);
			}
			else
				observable.fireError(new RuntimeException("An unexpected error occurred while adding!"));
			
		}, DELAY_TIME, TimeUnit.MILLISECONDS);
		
		return observable;
	}

	//----------------------------------------------------------------------------------------------------------
	@Override
	public Observable<TData> deleteById(TId id)
	{
		ObservableResult<TData> observable = new ObservableResult<>();
		
		executor.schedule(()->
		{
			if (Math.random() > ERROR_RATE)
			{
				TData data = null;
				for(TData element: this.listData)
				{
					if(element.getId().compareTo(id) == 0)
					{
						data = element;
						break;
					}
				}
				this.listData.remove(data);
				
				if( data == null)
					observable.fireError(new RuntimeException("Deleting error: Unknown id '" + id + "'"));
				else
					observable.fireNext(data);
			}
			else
				observable.fireError(new RuntimeException("An unexpected error ocurred while deleting!"));
			
		}, DELAY_TIME, TimeUnit.MILLISECONDS);
		
		return observable;
	}
	//----------------------------------------------------------------------------------------------------------
}
