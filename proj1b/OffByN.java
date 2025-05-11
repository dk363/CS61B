public class OffByN implements CharacterComparator {
    private int N;


    public OffByN(int i) {
        N = i;
    }

    /* Determine whether two letters differ by N */
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == N;
    }
}
