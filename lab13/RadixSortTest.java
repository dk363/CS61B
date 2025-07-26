import static org.junit.Assert.*;
import org.junit.Test;

public class RadixSortTest {
    @Test
    public void testBasicSorting() {
        String[] input = {"bca", "acb", "abc", "cba", "cab", "bac"};
        String[] expected = {"abc", "acb", "bac", "bca", "cab", "cba"};
        assertArrayEquals(expected, RadixSort.sort(input));
    }

    @Test
    public void testDifferentLengths() {
        String[] input = {"a", "ab", "abc", "abcd", "abcde", "abcdef"};
        String[] expected = {"a", "ab", "abc", "abcd", "abcde", "abcdef"};
        assertArrayEquals(expected, RadixSort.sort(input));
    }

    @Test
    public void testWithEmptyStrings() {
        String[] input = {"abc", "", "a", "ab", ""};
        String[] expected = {"", "", "a", "ab", "abc"};
        assertArrayEquals(expected, RadixSort.sort(input));
    }

    @Test
    public void testWithDuplicates() {
        String[] input = {"apple", "banana", "apple", "orange", "banana"};
        String[] expected = {"apple", "apple", "banana", "banana", "orange"};
        assertArrayEquals(expected, RadixSort.sort(input));
    }

    @Test
    public void testWithSpecialCharacters() {
        String[] input = {"abc", "a@c", "a#c", "a$c", "a!c"};
        String[] expected = {"a!c", "a#c", "a$c", "a@c", "abc"};
        assertArrayEquals(expected, RadixSort.sort(input));
    }

    @Test
    public void testAllSame() {
        String[] input = {"same", "same", "same"};
        String[] expected = {"same", "same", "same"};
        assertArrayEquals(expected, RadixSort.sort(input));
    }

    @Test
    public void testPrefixRelationship() {
        String[] input = {"a", "ab", "abc", "abcd"};
        String[] expected = {"a", "ab", "abc", "abcd"};
        assertArrayEquals(expected, RadixSort.sort(input));
    }

    @Test
    public void testReversedOrder() {
        String[] input = {"zebra", "yak", "xray", "wolf", "vulture"};
        String[] expected = {"vulture", "wolf", "xray", "yak", "zebra"};
        assertArrayEquals(expected, RadixSort.sort(input));
    }

    @Test
    public void testCaseSensitive() {
        String[] input = {"apple", "Apple", "banana", "Banana"};
        String[] expected = {"Apple", "Banana", "apple", "banana"};
        assertArrayEquals(expected, RadixSort.sort(input));
    }

    @Test
    public void testSingleCharacterStrings() {
        String[] input = {"d", "a", "z", "b"};
        String[] expected = {"a", "b", "d", "z"};
        assertArrayEquals(expected, RadixSort.sort(input));
    }
}
