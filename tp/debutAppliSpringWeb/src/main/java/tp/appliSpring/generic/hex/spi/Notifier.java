package tp.appliSpring.generic.hex.spi;

public  interface Notifier<T> {
	public void notifyNewState(T entity);
	public void notifyNewEvent(T entity,String eventHint);//eventHint may be a lifecyle event like "created" or "deleted" or else
	//...
}
