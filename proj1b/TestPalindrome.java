import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("aaa"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("cuc"));
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testPalindrome2() {
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("jkjk", obo));
        assertTrue(palindrome.isPalindrome("", obo));
        assertFalse(palindrome.isPalindrome("nonon", obo));
        assertFalse(palindrome.isPalindrome("abcdsadefg", obo));
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("a", obo));
        assertFalse(palindrome.isPalindrome("bing", obo));
        assertFalse(palindrome.isPalindrome("aabaa", obo));
        assertFalse(palindrome.isPalindrome("noon", obo));
    }

}

