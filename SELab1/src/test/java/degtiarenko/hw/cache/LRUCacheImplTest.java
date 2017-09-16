package degtiarenko.hw.cache;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LRUCacheImplTest {

    private static final String VALUE_DIFFERS_MSG = "Value differs from what expected.";
    private static final String VALUE_NOT_EXPECTED_MSG = "Value is not expected to be present.";

    private Cache<Integer, String> namesCache;

    @Before
    public void enableAssert() {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    @Test
    public void testGet() {
        namesCache = new LRUCacheImpl<>(100);

        namesCache.put(1, "Ivan");
        namesCache.put(2, "Ksyusha");
        namesCache.put(3, "Leha");

        assertEquals(VALUE_DIFFERS_MSG, "Ivan", namesCache.get(1));
        assertEquals(VALUE_DIFFERS_MSG, "Ksyusha", namesCache.get(2));
        assertEquals(VALUE_DIFFERS_MSG, "Leha", namesCache.get(3));
    }

    @Test
    public void testPutReturnPreviousValue() {
        namesCache = new LRUCacheImpl<>(3);

        namesCache.put(1, "a");
        namesCache.put(2, "b");

        assertEquals(VALUE_DIFFERS_MSG, "a", namesCache.put(1, "c"));
        assertEquals(VALUE_NOT_EXPECTED_MSG, null, namesCache.put(3, "d"));
    }

    @Test
    public void testPutDeleteOldValues() {
        namesCache = new LRUCacheImpl<>(3);

        namesCache.put(1, "a");
        namesCache.put(2, "b");
        namesCache.put(3, "c");
        namesCache.put(4, "d");

        assertEquals(VALUE_NOT_EXPECTED_MSG, null, namesCache.get(1));

        namesCache.put(5, "e");

        assertEquals(VALUE_NOT_EXPECTED_MSG, null, namesCache.get(2));

        namesCache.put(6, "sixth letter");
        namesCache.put(7, "seventh letter");
        namesCache.put(8, "eighth letter");

        assertEquals(VALUE_NOT_EXPECTED_MSG, null, namesCache.get(3));
        assertEquals(VALUE_NOT_EXPECTED_MSG, null, namesCache.get(4));
        assertEquals(VALUE_NOT_EXPECTED_MSG, null, namesCache.get(5));
        assertEquals(VALUE_DIFFERS_MSG, "sixth letter", namesCache.get(6));
        assertEquals(VALUE_DIFFERS_MSG, "seventh letter", namesCache.get(7));
        assertEquals(VALUE_DIFFERS_MSG, "eighth letter", namesCache.get(8));
    }

    @Test
    public void testPutImprovePriority() {
        namesCache = new LRUCacheImpl<>(2);

        namesCache.put(1, "a");
        namesCache.put(2, "b");

        namesCache.put(1, "c");
        namesCache.put(3, "d");

        assertEquals(VALUE_DIFFERS_MSG, "c", namesCache.get(1));
        assertEquals(VALUE_NOT_EXPECTED_MSG, null, namesCache.get(2));
        assertEquals(VALUE_DIFFERS_MSG, "d", namesCache.get(3));
    }

    @Test
    public void testGetImprovePriority() {
        namesCache = new LRUCacheImpl<>(2);

        namesCache.put(1, "a");
        namesCache.put(2, "b");

        namesCache.get(1);
        assertEquals(VALUE_NOT_EXPECTED_MSG, null, namesCache.put(3, "c"));

        assertEquals(VALUE_DIFFERS_MSG, "a", namesCache.get(1));
        assertEquals(VALUE_NOT_EXPECTED_MSG, null, namesCache.get(2));
        assertEquals(VALUE_DIFFERS_MSG, "c", namesCache.get(3));
    }
}
