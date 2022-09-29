package util.observable;

@FunctionalInterface
public interface Callback<T>
{
	public void callback(T data);
}
