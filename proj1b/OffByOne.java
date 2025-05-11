public class OffByOne implements CharacterComparator {
    /* Determine whether two letters differ by 1 */
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }
}
