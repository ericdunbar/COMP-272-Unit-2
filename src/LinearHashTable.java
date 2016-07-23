import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

/**
 * @description 5.2 LinearHashTable described in this section. This data
 *              structure is also described as open addressing with linear
 *              probing. Store the element x with hash value i = hash(x) in the
 *              table location t[i]. There are three types of entries in the
 *              LinearHashTable: 1. elements; 2. null values; and, 3. del
 *              values. From ODS.
 * 
 * @author Eric Dunbar
 *
 */
public class LinearHashTable<T> {
	int n; // track number of elements
	int q; // track number of non-null values
	int d; // current dimension of array
	T[] t; // store data elements, t.length = 2^d

	/**
	 * Finds and returns the element in x, if present. From ODS.
	 * 
	 * @param x element to find
	 * @return null if element not found, otherwise the element
	 */
	public T find(T x) {
		// TODO what happens if there are no nulls in the array. Infinite loop?
		// Use a for loop instead?
		int i = hash(x);
		while (t[i] != null) {
			if (t[i] != del && x.equals(t[i]))
				return t[i];
			i = (i == t.length - 1) ? 0 : i + 1; // increment i, why not
													// modulus?
		}
		return null;
	}

	/**
	 * Adds the given element to the LinearHashTable provided it's unique. From
	 * ODS.
	 * 
	 * @param x element to be added
	 * @return true if unique, false if already in the table
	 */
	public boolean add(T x) {
		if (find(x) != null)
			return false;
		if (2 * (q + 1) > t.length)
			resize(); // limit of 50% usage
		int i = hash(x);
		while (t[i] != null && t[i] != del)
			i = (i == t.length - 1) ? 0 : i + 1; // increment i, why not modulus
		if (t[i] == null)
			q++;
		n++;
		t[i] = x;
		return true;
	}

	/**
	 * Remove the given element. From ODS, modified.
	 * 
	 * @param x element to remove
	 * @return null if not present, otherwise element
	 */
	public T remove(T x) {
		// TODO what happens if there are no nulls? Is that possible?
		int i = hash(x);
		while (t[i] != null) {
			T element = t[i];
			if (element != del && x.equals(element)) {
				t[i] = del;
				n--;
				if (8 * n < t.length)
					resize(); // min 12.5% occupancy
				return element;
			}
			i = (i == t.length - 1) ? 0 : i + 1; // increment i, why not modulus
		}
		return null;
	}

	/**
	 * @description Find the smallest non-negative integer d such that 2d >= 3n.
	 *              Reallocate the array t so that it has size 2d, and then
	 *              insert all the elements in the old version of t into the
	 *              newly-resized copy of t. While doing this, reset q equal to
	 *              n since the newly-allocated t contains no del values.
	 */
	public void resize() {
		d = 1;
		while ((1 << d) < 3 * n)
			d++;
		T[] tOld = t;
		t = newArray(1 << d);
		q = n;
		for (int k = 0; k < tOld.length; k++) {
			if (tOld[k] != null && tOld[k] != del) {
				int i = hash(tOld[k]);
				while (t[i] != null)
					i = (i == t.length - 1) ? 0 : i + 1;
				t[i] = tOld[k];
			}
		}
	}

}
