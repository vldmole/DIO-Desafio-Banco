package util.observable;

import java.util.ArrayList;
import java.util.List;

public class Observable<TData>
{
	//-------------------------------------------------------------------
	public static class Observer<TData>
	{
		Callback<TData> next = null;
		Callback<Exception> error = null;
		
		public Observer(Callback<TData> next, Callback<Exception> error)
		{
			this.next = next;
			this.error = error;
		}
	}
	
	//-------------------------------------------------------------------
	protected List<Observer<TData>> callbacks = new ArrayList<>();
	
	
	//-------------------------------------------------------------------
	public void subscribe(Observer<TData> observer)
	{
		callbacks.add(observer);
	}
	
	//-------------------------------------------------------------------
	public void subscribe(Callback<TData> next, Callback<Exception> error)
	{
		subscribe(new Observer<TData>(next, error));
	}
	
	//-------------------------------------------------------------------
	public void unsubscribe(Observer<TData> observer)
	{
		callbacks.remove(observer);
	}
	
	//----------------------------------------------------------------------
	public void unsubscribe(Callback<TData> next, Callback<Exception> error)
	{
		for(int i=0; i<callbacks.size(); i++)
		{
			Observer<?> current = callbacks.get(i);
			if(current.next == next || current.error == error)
			{
				callbacks.remove(i);
				return;
			}
		}
	}
	
	//----------------------------------------------------------------------
	protected void fireNext(TData data)
	{
		callbacks.forEach((pair) -> pair.next.callback(data));
	}

	//----------------------------------------------------------------------
	protected void fireError(Exception exception)
	{
		callbacks.forEach((pair) -> pair.error.callback(exception));
	}
}
