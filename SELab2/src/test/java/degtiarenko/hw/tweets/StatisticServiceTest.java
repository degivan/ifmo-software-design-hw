package degtiarenko.hw.tweets;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticServiceTest {
    @Mock
    private SearchService searchService;

    @Test
    public void testGetHashTagPopularity_filterRedundant() throws Exception {
        StatisticService statisticService = new StatisticServiceImpl(searchService);

        String hashTag = "Test";
        Tweet tweet1 = new Tweet(new DateTime(), "", "2");
        Tweet tweet2 = new Tweet(new DateTime().minusHours(2), "", "1");

        when(searchService.getTweetsWithHashTag(eq(hashTag), any()))
                .thenReturn(Arrays.asList(tweet1, tweet2));

        List<Long> expectedResult = Collections.singletonList(1L);
        assertEquals(expectedResult, statisticService.getHashTagPopularity(hashTag, 1));
    }

    @Test
    public void testGetHashTagPopularity_groupingByHour() throws Exception {
        StatisticService statisticService = new StatisticServiceImpl(searchService);

        String hashTag = "Test";
        Tweet tweet1 = new Tweet(new DateTime(), "", "2");
        Tweet tweet2 = new Tweet(new DateTime().minusHours(1), "", "1");
        Tweet tweet3 = new Tweet(new DateTime().minusHours(1), "", "0");

        when(searchService.getTweetsWithHashTag(eq(hashTag), any()))
                .thenReturn(Arrays.asList(tweet1, tweet2, tweet3));

        List<Long> expectedResult = Arrays.asList(1L, 2L);
        assertEquals(expectedResult, statisticService.getHashTagPopularity(hashTag, 2));
    }

}
