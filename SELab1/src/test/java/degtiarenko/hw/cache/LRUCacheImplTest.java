package degtiarenko.hw.cache;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LRUCacheImplTest {

    private Cache<Integer, String> namesCache;

    @Before
    public void enableAssert() {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    @Test
    public void testGet() {
        Cache<Integer, String> namesCache = new LRUCacheImpl<>(100);

        namesCache.put(1, "Ivan");
        namesCache.put(2, "Ksyusha");
        namesCache.put(3, "Leha");

        assertEquals("Ivan", namesCache.get(1));
        assertEquals("Ksyusha", namesCache.get(2));
        assertEquals("Leha", namesCache.get(3));
    }

    @Test
    public void testPutReturnPreviousValue() {
        namesCache = new LRUCacheImpl<>(3);

        namesCache.put(1, "a");
        namesCache.put(2, "b");

        assertEquals("a", namesCache.put(1, "c"));
        assertEquals(null, namesCache.put(3, "d"));
    }

    @Test
    public void testPutDeleteOldValues() {
        namesCache = new LRUCacheImpl<>(3);

        namesCache.put(1, "a");
        namesCache.put(2, "b");
        namesCache.put(3, "c");
        namesCache.put(4, "d");

        assertEquals(null, namesCache.get(1));

        namesCache.put(5, "e");

        assertEquals(null, namesCache.get(2));

        namesCache.put(6, "sixth letter");
        namesCache.put(7, "seventh letter");
        namesCache.put(8, "eighth letter");

        assertEquals(null, namesCache.get(3));
        assertEquals(null, namesCache.get(4));
        assertEquals(null, namesCache.get(5));
        assertEquals("sixth letter", namesCache.get(6));
        assertEquals("seventh letter", namesCache.get(7));
        assertEquals("eighth letter", namesCache.get(8));
    }

    @Test
    public void testPutImprovePriority() {
        namesCache = new LRUCacheImpl<>(2);

        namesCache.put(1, "a");
        namesCache.put(2, "b");

        namesCache.put(1, "c");
        namesCache.put(3, "d");

        assertEquals("c", namesCache.get(1));
        assertEquals(null, namesCache.get(2));
        assertEquals("d", namesCache.get(3));
    }

    @Test
    public void testGetImprovePriority() {
        namesCache = new LRUCacheImpl<>(2);

        namesCache.put(1, "a");
        namesCache.put(2, "b");

        namesCache.get(1);
        namesCache.put(3, "c");

        assertEquals("a", namesCache.get(1));
        assertEquals(null, namesCache.get(2));
        assertEquals("c", namesCache.get(3));
    }
}
