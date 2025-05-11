public class Palindrome {
    /* create a character list */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> res = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            res.addLast(word.charAt(i));
        }
        return res;
    }

    /* Determine whether a number is a palindrome and return a boolean value */
    public boolean isPalindrome(String word) {
        Deque d = wordToDeque(word);
        if (d.size() == 0 || d.size() == 1) {
            return true;
        }
        if (d.removeFirst() != d.removeLast()) {
            return false;
        }
        return isPalindrome(isPalindromeHelper(word));
    }

    /* make string -> trin */
    private String isPalindromeHelper(String word) {
        String res = word.substring(1, word.length() - 1);
        return res;
    }

    /* return true if the word is a palindrome according to the character comparison test
        provided by the CharacterComparator passed in as argument cc. */
    public boolean isPalindrome(String word, CharacterComparator obo) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        if (!obo.equalChars(word.charAt(0), word.charAt(word.length() - 1))) {
            return false;
        }
        return isPalindrome(isPalindromeHelper(word), obo);
    }

}
