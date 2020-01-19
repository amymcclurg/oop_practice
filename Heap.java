
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;

/** An instance is a max-heap or a min-heap of distinct values of type E <br>
 * with priorities of type double. */
public class Heap<E> {

	/** Class Invariant: <br>
	 * 1. b[0..size-1] represents a complete binary tree. <br>
	 * b[0] is the root. <br>
	 * For each k, b[2k+1] and b[2k+2] are the left and right children of b[k]. <br>
	 * If k != 0, b[(k-1)/2] (using integer division) is the parent of b[k].
	 *
	 * 2. For k in 0..size-1, b[k] contains a value and its priority.<br>
	 * The values in b[size..] may or may not be null. DO NOT RELY ON THEM BEING NULL. <br>
	 * NEVER TEST WHETHER ONE OF THESE VALUES IS NULL OR IS NOT NULL.
	 *
	 * 3. The values in b[0..size-1] are all different.
	 *
	 * 4. For k in 1..size-1, <br>
	 * .. if isMinHeap, (b[k]'s parent's priority) <= (b[k]'s priority),<br>
	 * .. if !isMinHeap, (b[k]'s parent's priority) >= (b[k]'s priority).<br>
	 * <br>
	 * .. Examples: min-heap ... max-heap <br>
	 * ................ 2 ........ 5 <br>
	 * ............... / \ ...... / \ <br>
	 * .............. 5 . 3 .... 2 . 3 <br>
	 *
	 * ===================================================================
	 *
	 * map and the tree are in sync, meaning:
	 *
	 * 5. The keys of map are the values in b[0..size-1]. <br>
	 * This implies that size = map.size().
	 *
	 * 6. if value v is in b[k], then map.get(v) returns k. */
	protected final boolean isMinHeap;
	protected VP[] b;
	protected int size;
	protected HashMap<E, Integer> map; // "map" for dictionary

	/** Constructor: an empty min-heap with capacity 10. */
	public Heap() {
		isMinHeap= true;
		b= createVPArray(10);
		map= new HashMap<>();
	}

	/** Constructor: an empty heap with capacity 10. <br>
	 * It is a min-heap if isMin is true, a max-heap if isMin is false. */
	public Heap(boolean isMin) {
		isMinHeap= isMin;
		b= createVPArray(10);
		map= new HashMap<>();
	}

	/** A VP object houses a value and a priority. */
	class VP {
		E val;             // The value
		double priority;   // The priority

		/** An instance with value v and priority p. */
		VP(E v, double p) {
			val= v;
			priority= p;
		}

		/** Return a representation of this VP object. */
		@Override
		public String toString() {
			return "(" + val + ", " + priority + ")";
		}
	}

	/** Add v with priority p to the heap. <br>
	 * Throw an illegalArgumentException if v is already in the heap. <br>
	 * The expected time is logarithmic and the <br>
	 * worst-case time is linear in the size of the heap. */
	public void add(E v, double p) throws IllegalArgumentException {
		// TODO #1: Write this whole method. Note that bubbleUp is not implemented
		// and simply returns, so calling it has no effect (yet).

		// Instructions.
		// 1. Read the spec of ensureSpace and the hint in its body.
		// 2. Calling bubbleUp is the last thing to be done. Make sure
		// the class invariant is true (except for value v being in the wrong place)
		// before bubbling up.

		// Testing: The first tests of insert, procedures
		// test00insert and test01insertException, should work if this method
		// is written properly, even if ensureSpace and bubbleUp are not yet
		// written.
		if (map.containsKey(v) == true) { throw new IllegalArgumentException("v is already in HashMap"); }
		ensureSpace();
		VP newobject= new VP(v, p);
		b[size]= newobject;
		map.put(v, size);
		size= size + 1;
		bubbleUp(size - 1);

	}

	/** If size = length of b, double the length of array b. <br>
	 * The worst-case time is proportional to the length of b. */
	protected void ensureSpace() {
		// TODO #2. Note: Any method that increases the size of the heap must
		// call this method before increasing the size.
		// Look at method Arrays.copyOf in the Java API documentation.

		// If this method is written correctly, testing procedures
		// test10ensureSpace, test11ensureSpace, and
		// test12ensureSpace will work correctly
		if (size == b.length) {
			int newsize= size * 2;
			b= Arrays.copyOf(b, newsize);
		}
	}

	/** Return the size of this heap. <br>
	 * This operation takes constant time. */
	public int size() { // Do not change this method
		return size;
	}

	/** Swap b[i] and b[j]. <br>
	 * Precondition: 0 <= i < heap-size, 0 <= j < heap-size. */
	void swap(int i, int j) {
		assert 0 <= i && i < size && 0 <= j && j < size;
		// TODO 3: When bubbling values up and (later on) down, two values,
		// say b[i] and b[j], will have to be swapped. At the same time,
		// the definition of map has to be maintained.
		// In order to always get this right, always use method swap for this.
		// Method swap is tested by testing procedure test12Swap
		// --it will find no errors if you write this method properly.
		Heap<E>.VP x= b[i];
		b[i]= b[j];
		b[j]= x;

		map.put(b[i].val, i);
		map.put(b[j].val, j);
	}

	/** If a value with priority p1 should be above a value with priority p2 in the heap, return 1. If
	 * priority p1 and priority p2 are the same, return 0. <br>
	 * If a value with priority p1 should be below a value with priority p2 in the heap, return -1. This
	 * is based on what kind of a heap this is, <br>
	 * ... E.g. a min-heap, the value with the smallest priority is in the root. <br>
	 * ... E.g. a max-heap, the value with the largest priority is in the root. */
	public int compareTo(double p1, double p2) {
		if (p1 == p2) return 0;
		if (isMinHeap) { return p1 < p2 ? 1 : -1; }
		return p1 > p2 ? 1 : -1;
	}

	/** If b[h] should be above b[k] in the heap, return 1.<br>
	 * If b[h]'s priority and b[k]'s priority are the same, return 0. <br>
	 * If b[h] should be below b[k] in the heap, return -1. <br>
	 * This is based on what kind of a heap this is, <br>
	 * ... E.g. a max-heap, the value with the largest priority is in the root. <br>
	 * ... E.g. a min-heap, the value with the smallest priority is in the root. */
	public int compareTo(int h, int k) {
		return compareTo(b[h].priority, b[k].priority);
	}

	/** Bubble b[k] up the heap to its right place. <br>
	 * Precondition: 0 <= k < size and <br>
	 * The class invariant is true, except perhaps that b[k] belongs above its <br>
	 * parent (if k > 0) in the heap, not below it. */
	void bubbleUp(int k) {
		// TODO #4 This method should be called within insert in order
		// to bubble a value up to its proper place, based on its priority.
		// Do not use recursion. Use iteration.
		// Use method compareTo to test whether value k is in its right place.
		// If this method is written properly, testing procedures test13ensureSpace,
		// test15insert_BubbleUp(), test16insertMaxHeap_BubbleUp,
		// test17insert_BubbleUpDuplicatePriorities, and
		// test17insertMax_BubbleUpDuplicatePriorities will not find any errors.
		assert 0 <= k && k < size;
		while (k != 0) {
			int x= (k - 1) / 2;
			if (compareTo(k, x) == 1) {
				swap(k, (k - 1) / 2);
				k= x;
			} else {
				return;
			}
		}
	}

	/** If this is a max-heap, return the heap value with highest priority. <br>
	 * If this is a min-heap, return the heap value with lowest priority. <br>
	 * Do not change the heap. <br>
	 * Throw a NoSuchElementException if the heap is empty.<br>
	 * This operation takes constant time. */
	public E peek() {

		// TODO 5: Do peek. This is an easy one. If it is correct,
		// test25MinPeek() and test26MaxPeek will show no errors.

		if (b.length == 0) { throw new NoSuchElementException("heap is empty"); }
		return b[0].val;
	}

	/** If k is not in 0..size-1, return. <br>
	 * OTHERWISE:<br>
	 * Bubble b[k] down in heap until it finds the right place. <br>
	 * If there is a choice to bubble down to both the left and right children<br>
	 * (because their priorities are equal), choose the right child. <br>
	 * Precondition: 0 <= k < size and class invariant is true except that <br>
	 * perhaps b[k] belongs below one or both of its children. */
	void bubbleDown(int k) {
		// TODO 6: DO NOT USE RECURSION. Use iteration.
		// When this method is correct, all testing procedures
		// beginning with test30 and test31 will not find errors.
		if (k > size - 1 || k < 0) return;
		int x= 2 * k + 1;
		int y= 2 * k + 2;
		int swapcandidate= 0;
		while (x < size) {
			// only one child
			if (y == size || compareTo(x, y) > 0) {
				swapcandidate= x;
			}
			// two children
			else {
				swapcandidate= y;
			}
			if (compareTo(k, swapcandidate) >= 0) {
				return;
			}

			else {
				swap(k, swapcandidate);
				k= swapcandidate;
				x= 2 * k + 1;
				y= 2 * k + 2;
			}

		}
		return;

	}

	/** If this is a max-heap, remove and return the heap value with highest priority.<br>
	 * If this is a min-heap, remove and return heap value with lowest priority. <br>
	 * Throw a NoSuchElementException if the heap is empty.<br>
	 * The expected time is logarithmic and the worst-case time is linear in the <br>
	 * size of the heap. . */
	public E poll() {
		// TODO 7: When this method is written correctly, testing procedures
		// beginning with test32, test33, and test34 will not find errors.
		//
		// Note also testing procedure test40DuplicatePriorities
		// This method tests to make sure that when bubbling up or down,
		// two values with the same priority are not swapped.
		if (size == 0) { throw new NoSuchElementException("heap is empty"); }
		E end= b[0].val;
		swap(0, size - 1);
		map.remove(end);
		size= size - 1;
		if (size > 0) {
			bubbleDown(0);
		}

		return end;

	}

	/** Change the priority of value v to p. <br>
	 * Throw an IllegalArgumentException if v is not in the heap.<br>
	 * The expected time is logarithmic and the worst-case time is linear <br>
	 * in the size of the heap. */
	public void changePriority(E v, double p) {
		// TODO 8: When this method is correctly implemented, testing procedures
		// test50... won't find errors.
		// Also, if these work, testing procedures test70Strings
		// and test90bigTests should not find errors.
		if (map.containsKey(v) != true) { throw new IllegalArgumentException("v is not in the heap"); }
		int x= map.get(v);
		double op= b[x].priority;
		b[x].priority= p;
		if (compareTo(p, op) == 1) {
			bubbleUp(x);

		}
		if (compareTo(p, op) == -1) {
			bubbleDown(x);
		} else {
			return;
		}
	}

	/** Create and return an array of size n. <br>
	 * This is necessary because generics and arrays don't interoperate nicely. <br>
	 * A student in CS2110 would not be expected to know about the need for <br>
	 * this method and how to write it. <br>
	 * We had to search the web to find out how to do it. */
	VP[] createVPArray(int n) {
		return (VP[]) Array.newInstance(VP.class, n);
	}
}