import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @description 5.1 A chained hash table from Open Data Structures by Pat Morin.
 *              This class implements hashing with chaining using multiplicative
 *              hashing.
 * @author Pat Morin
 * @author Eric Dunbar
 * @param <T>
 * @date 22/7/2016
 */
public class ChainedHashTable<T> implements USet<T> {

	List<T>[] t; // the hash table
	int d; // table dimension, table.length = 2^d
	int n; // number of elements in table
	int z; // the multiplier (an odd integer)
	protected static final int w = 32; // number of bits in the an int (see ODS,
										// chapter 1 for word length)

	/**
	 * Constructs a new empty hash table. From ODS.
	 * 
	 */
	public ChainedHashTable() {
		d = 1; // set initial table dimension
		t = allocTable(1 << d); // allocate a table using bitshift magic
		Random r = new Random();
		z = r.nextInt() | 1; // is a random odd integer
	}

	/**
	 * Allocate and initialize a new empty table. From ODS.
	 * 
	 * @param tableSize size of empty table
	 * @return array of lists
	 */
	@SuppressWarnings("unchecked")
	protected List<T>[] allocTable(int tableSize) {
		List<T>[] table = new ArrayList[tableSize];
		for (int i = 0; i < tableSize; i++) {
			table[i] = new ArrayList<T>();
		}
		return table;
	}

	/**
	 * Clear all elements from ChainedHashTable and reset its size to minimum.
	 * From ODS.
	 */
	@Override
	public void clear() {
		d = 1; // reset dimension to minimum
		t = allocTable(1 << d); // reallocate a table with minimum size
		n = 0; // no elements
	}

	/**
	 * Resize the table so that it has size 2^d. From ODS.
	 */
	protected void resize() {
		d = 1;
		while (1 << d <= n)
			d++; // determine the appropriate dimension
		n = 0;
		List<T>[] oldTable = t;
		t = allocTable(1 << d);
		for (int i = 0; i < oldTable.length; i++) {
			for (T x : oldTable[i]) {
				add(x);
			}
		}
	}

	/**
	 * Return the number of elements stored in this hash table. From ODS.
	 */
	public int size() {
		return n;
	}

	/**
	 * Add the element x to the hashtable if it doesn't already appear in it.
	 * From ODS.
	 * 
	 * @param x element to be added
	 * @return true if not already present
	 */
	public boolean add(T x) {
		if (find(x) != null)
			return false;
		if (n + 1 > t.length)
			resize();
		t[hash(x)].add(x);
		n++;
		return true;
	}

	/**
	 * Removes the element x from the hash table. From ODS.
	 * 
	 * @param element to be removed
	 * @return null if element not found or removed element if present
	 */
	public T remove(T x) {
		Iterator<T> it = t[hash(x)].iterator();
		while (it.hasNext()) {
			T y = it.next();
			if (y.equals(x)) {
				it.remove();
				n--;
				return y;
			}
		}
		return null;
	}

	/**
	 * Find and return a copy of x stored in this table. From ODS.
	 * 
	 * @param x the element to find
	 * @return the element y stored in this table such that x.equals(y), or null
	 *         if element y is missing
	 */
	public T find(Object x) {
		for (T y : t[hash(x)])
			if (y.equals(x))
				return y;
		return null;
	}

	/**
	 * Computes the hash for the element, x. From ODS.
	 * 
	 * @param x
	 * @return hash, ((x.hashCode() * z) mod 2^w) div 2^(w-d)
	 */
	protected final int hash(Object x) {
		// Why is final used in the method statement? Cannot be overriden.
		/*
		 * Java Practices - Use final liberally. (2013, September 21). Retrieved
		 * July 22, 2016, from
		 * http://www.javapractices.com/topic/TopicAction.do?Id=23
		 */

		return (z * x.hashCode()) >>> (w - d);
	}

	/**
	 * The iterator for the hash table. From ODS.
	 */
	@Override
	public Iterator<T> iterator() {
		class IT implements Iterator<T> {
			int i, j;
			int ilast, jlast;

			IT() {
				i = 0;
				j = 0;
				while (i < t.length && t[i].isEmpty())
					i++;
			}

			protected void jumpToNext() {
				while (i < t.length && j + 1 > t[i].size()) {
					j = 0;
					i++;
				}
			}

			public boolean hasNext() {
				return i < t.length;
			}

			public T next() {
				ilast = i;
				jlast = j;
				T x = t[i].get(j);
				j++;
				jumpToNext();
				return x;
			}

			public void remove() {
				ChainedHashTable.this.remove(t[ilast].get(jlast));
			}
		}
		return new IT();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * Programming With Assertions. (n.d.). Retrieved July 22, 2016, from
		 * https://docs.oracle.com/javase/8/docs/technotes/guides/language/
		 * assert.html
		 * 
		 * Eclipse: Enable assertions. (n.d.). Retrieved July 22, 2016, from
		 * http://stackoverflow.com/questions/5509082/eclipse-enable-assertions
		 * 
		 * 1. Go to the menu Run, and then to the menu item Run Configurations;
		 * 2. In the left panel, go to Java Application, and then go to
		 * Assertions; 3. In the right panel, choose the tab Arguments; and, 4.
		 * Under the field for VM arguments, type -ea to enable assertions.
		 */
		int n = 100000;
		ChainedHashTable<Integer> t = new ChainedHashTable<Integer>();
		for (int i = 0; i < n; i++) {
			t.add(i * 2);
		}
		for (int i = 0; i < 2 * n; i++) {
			Integer x = t.find(i);
			if (i % 2 == 0) {
				assert (x.intValue() == i);
			} else {
				assert (x == null);
			}
		}
	}
}
