/**
 * @description Obtained from Open Data Structures by Pat Morin
 * @author Pat Morin
 *
 * @param <T>
 */
public interface USet<T> extends Iterable<T> {
	public int size();
	public boolean add(T x);
	public T remove(T x);
	public T find(T x);
	public void clear();
}
