package creatures;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

import javax.management.ImmutableDescriptor;

/** Tests the plip class
 *  @author Kuangdi Xu
 */

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus p = new Clorus(2);
        Clorus q = p.replicate();
        assertEquals(1, p.energy(), 0.01);
        assertNotSame(p, q);
    }

    @Test
    public void testChoose() {
        Clorus c = new Clorus(1);
        // 四周是满的 保持静止
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Plip(1));

        Action actual1 = c.chooseAction(surrounded);
        Action expected1 = new Action(Action.ActionType.STAY);

        assertEquals(actual1, expected1);

        // 右边是空的，能量等于 1，向随机方格复制
        surrounded.clear();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Empty());
        Action actual2 = c.chooseAction(surrounded);
        Action expected2 = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);
        assertEquals(actual2, expected2);

        c.move(); // 能量 < 1
        // 移动
        Action actual3 = c.chooseAction(surrounded);
        Action expected3 = new Action(Action.ActionType.MOVE, Direction.RIGHT);
        assertEquals(actual3, expected3);

        // 有空格而且有 Plip 攻击 Plip
        surrounded.clear();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Plip());

        Action actual4 = c.chooseAction(surrounded);
        Action expected4 = new Action(Action.ActionType.ATTACK, Direction.RIGHT);
        assertEquals(actual4, expected4);
    }
}
