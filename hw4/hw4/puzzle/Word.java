package hw4.puzzle;

import java.util.Set;
import java.util.HashSet;

import edu.princeton.cs.introcs.In;

public class Word implements WorldState {
    private static Set<String> words;
    private static final String WORDFILE = "input/words10000.txt";
    private final String word;
    private final String goal;

    /**
     * Reads the wordfile specified by the wordfile variable.
     * 读取由wordfile变量指定的单词文件。
     */
    private void readWords() {
        words = new HashSet<String>();

        In in = new In(WORDFILE);
        while (!in.isEmpty()) {
            words.add(in.readString());
        }
    }

    /**
     * Creates a new Word.
     * 创建一个新的Word文档。
     */
    public Word(String w, String g) {
        /* If words hasn't been read yet, read it. */
        if (words == null) {
            readWords();
        }

        if (!words.contains(w)) {
            throw new IllegalArgumentException("Invalid word: " + w);
        }

        if (!words.contains(g)) {
            throw new IllegalArgumentException("Invalid goal: " + g);
        }

        word = w;
        goal = g;
    }

    /**
     * Computes the edit distance between a and b. From
     * https://rosettacode.org/wiki/Levenshtein_distance.
     * 计算a与b之间的编辑距离。源自
     * https://rosettacode.org/wiki/Levenshtein_distance。
     */
    private static int editDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                         a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }


    /** 提供此 WorldState 所有邻居的可迭代对象。 */
    @Override
    public Iterable<WorldState> neighbors() {
        Set<WorldState> neighbors = new HashSet<>();
        for (String s : words) {
            if (editDistance(this.word, s) == 1) {
                neighbors.add(new Word(s, goal));
            }
        }
        return neighbors;
    }

    /** 提供到达目标所需移动次数的估计值，
     * 该值必须小于或等于实际距离。 */
    @Override
    public int estimatedDistanceToGoal() {
        return editDistance(this.word, goal);
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Word word1 = (Word) o;

        if (word != null ? !word.equals(word1.word) : word1.word != null) {
            return false;
        }
        return goal != null ? goal.equals(word1.goal) : word1.goal == null;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (goal != null ? goal.hashCode() : 0);
        return result;
    }
}
