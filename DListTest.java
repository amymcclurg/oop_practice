package LinkedList;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class DListTest {

	@Test
	public void testConstructor() {
		/** Check an empty linked list */
		DList<Integer> b= new DList<>();
		assertEquals("[]", b.toString());
		assertEquals("[]", b.toStringR());
		assertEquals(0, b.size());
	}

	@Test
	public void testPrepend() {
		/** Create a list and prepend a node, check if list updates */
		DList<String> ll= new DList<>();
		ll.prepend("Sampson");
		assertEquals("[Sampson]", ll.toString());
		assertEquals("[Sampson]", ll.toStringR());
		assertEquals(1, ll.size());
		/** Prepend a second node, check if list updates */
		ll.prepend("Again");
		assertEquals("[Again, Sampson]", ll.toString());
		assertEquals("[Sampson, Again]", ll.toStringR());
		assertEquals(2, ll.size());
		/** Prepend a third node, check if string/reverse string is correct */
		ll.prepend("Third");
		assertEquals("[Third, Again, Sampson]", ll.toString());
		assertEquals("[Sampson, Again, Third]", ll.toStringR());
		assertEquals(3, ll.size());
	}

	@Test
	public void testAppend() {
		/** Create an empty linked list and add a first node */
		DList<String> aa= new DList<>();
		aa.append("hello");
		assertEquals("[hello]", aa.toString());
		assertEquals("[hello]", aa.toStringR());
		assertEquals(1, aa.size());

		/** Add a second and third values, check if linked list updates */
		aa.append("world");
		assertEquals("[hello, world]", aa.toString());
		assertEquals("[world, hello]", aa.toStringR());
		assertEquals(2, aa.size());
		aa.append("bye");
		assertEquals("[hello, world, bye]", aa.toString());
		assertEquals("[bye, world, hello]", aa.toStringR());
		assertEquals(3, aa.size());
	}

	@Test
	public void testGetNode() {
		DList<String> c= new DList<>();
		c.append("one");
		/** Test when it is the only node */
		assertEquals("one", c.getNode(0).value());
		c.append("two");

		/** Test when it is the last node in linked list of 2 */
		assertEquals("two", c.getNode(1).value());

		/** Add more nodes and test the last value in a longer list */
		c.append("three");
		c.append("four");
		c.append("five");
		assertEquals("five", c.getNode(4).value());

		/** Re-test first value, test a value before and after the midpoint */
		assertEquals("one", c.getNode(0).value());
		assertEquals("two", c.getNode(1).value());
		assertEquals("four", c.getNode(3).value());
	}

	@Test
	public void testDelete() {
		/** Create a linked list and remove the middle/2nd value until just 1 node remains */
		DList<String> d= new DList<>();
		d.append("one");
		d.append("two");
		d.append("three");
		d.delete(d.getNode(1));
		assertEquals("[one, three]", d.toString());
		assertEquals("[three, one]", d.toStringR());
		assertEquals(2, d.size());
		d.delete(d.getNode(1));
		assertEquals(1, d.size());
		assertEquals("[one]", d.toString());
		assertEquals("[one]", d.toStringR());

		/** Create a linked list and remove the last value until empty */
		DList<String> e= new DList<>();
		e.append("one");
		e.append("two");
		e.append("three");
		e.delete(e.getNode(2));
		assertEquals(2, e.size());
		assertEquals("[one, two]", e.toString());
		assertEquals("[two, one]", e.toStringR());
		e.delete(e.getNode(1));
		assertEquals(1, e.size());
		assertEquals("[one]", e.toString());
		assertEquals("[one]", e.toStringR());
		e.delete(e.getNode(0));
		assertEquals(0, e.size());
		assertEquals("[]", e.toString());
		assertEquals("[]", e.toStringR());

		/** Create a linked list and remove first values until empty */
		DList<String> f= new DList<>();
		f.append("one");
		f.append("two");
		f.append("three");
		f.delete(f.getNode(0));
		assertEquals(2, f.size());
		assertEquals("[two, three]", f.toString());
		assertEquals("[three, two]", f.toStringR());
		f.delete(f.getNode(0));
		assertEquals(1, f.size());
		assertEquals("[three]", f.toString());
		assertEquals("[three]", f.toStringR());
		f.delete(f.getNode(0));
		assertEquals(0, f.size());
		assertEquals("[]", f.toString());
		assertEquals("[]", f.toStringR());
	}

	@Test
	public void testInsertBefore() {
		DList<String> g= new DList<>();
		g.append("Bravo");
		g.append("Charlie");
		g.append("Echo");

		/** Insert before when n is first node */
		g.insertBefore("Alfa", g.getNode(0));
		assertEquals("[Alfa, Bravo, Charlie, Echo]", g.toString());
		assertEquals("[Echo, Charlie, Bravo, Alfa]", g.toStringR());
		assertEquals(4, g.size());

		/** Insert before when n is not first node */
		g.insertBefore("Delta", g.getNode(3));
		assertEquals("[Alfa, Bravo, Charlie, Delta, Echo]", g.toString());
		assertEquals("[Echo, Delta, Charlie, Bravo, Alfa]", g.toStringR());
		assertEquals(5, g.size());
	}
}
