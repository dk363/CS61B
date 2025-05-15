import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    /** there are four methods in AD
     *  addlast addFirst removeLast removeFirst
     *  correct is what we want
     *  methods in students have something wrong
     *  we need to write a test to find out the incorrect point
     */
    public void Test1() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> correct = new ArrayDequeSolution<>();

        StringBuilder log = new StringBuilder();


        for (int i = 0; i < 100; i++) {
            if (correct.isEmpty()) { // correct is what we want, so we can just check it only
                int HeadOrBack = StdRandom.uniform(2); // 随机决定是在前面还是在后面
                int addNumber = StdRandom.uniform(1000);
                if (HeadOrBack == 0) {
                    log.append("addFirst(").append(addNumber).append(")\n");
                    student.addFirst(addNumber);
                    correct.addFirst(addNumber);
                } else {
                    log.append("addLast(").append(addNumber).append(")\n");
                    student.addLast(addNumber);
                    correct.addLast(addNumber);
                }
            } else {
                Integer studentRemoveNumber = null;
                Integer correctRemoveNumber = null;
                int status = StdRandom.uniform(4); // 用status决定随机调用哪一个 method
                int addNumber = StdRandom.uniform(1000);
                if (status == 0) {
                    log.append("addFirst(").append(addNumber).append(")\n");
                    student.addFirst(addNumber);
                    correct.addFirst(addNumber);
                } else if (status == 1) {
                    log.append("addLast(").append(addNumber).append(")\n");
                    student.addLast(addNumber);
                    correct.addLast(addNumber);
                } else if (status == 2) {
                    log.append("RemoveFirst()\n");// 只有在remove* method 中我们才能获得return value 才能比较是否相等
                    studentRemoveNumber = student.removeFirst();
                    correctRemoveNumber = correct.removeFirst();
                    assertEquals(log.toString(), studentRemoveNumber, correctRemoveNumber);
                } else {
                    log.append("RemoveLast()\n");
                    studentRemoveNumber = student.removeLast();
                    correctRemoveNumber = correct.removeLast();
                    assertEquals(log.toString(), studentRemoveNumber, correctRemoveNumber);
                }
            }
        }
    }
}
