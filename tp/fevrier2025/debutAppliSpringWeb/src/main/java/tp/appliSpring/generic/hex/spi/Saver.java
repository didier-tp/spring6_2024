package tp.appliSpring.generic.hex.spi;

public interface Saver<T,ID> {
	public T saveNew(T obj);
	public void  updateExisting(T obj);
	public void deleteFromId(ID id);
}
