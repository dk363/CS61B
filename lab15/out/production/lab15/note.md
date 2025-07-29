# 游戏流程
1. creatures 中的两个类继承自 huglife.Creature 类，该类提供了所有生物应该遵循的模板
2. 世界中存在一个全局管理队列，依次等待执行动作。生物按照轮转方式选择行动
3. 当某只生物位于队首时，会告知该生物四个相邻方格的状态，要求该生物选择行动
4. 调用 `chooseAction()` 方法，以四个相邻方格的集合作为参数，生物有五种动作
   1. MOVE REPLICATE ATTACK 必须使用带方向或位置的构造方法
   2. STAY DIE 必须使用无方向的构造方法
5. 主要任务是编写决定生物行为的代码
6. 生物选定Action之后，要追踪每个生物的状态变化
7. 状态变化通过 callback 机制实现
## Occupant
Occupant 是所有可能存在物的基类
有两种特殊的
1. empty 未占用格
2. impassible 不可通行格
## Creature
1. 有一个能量值 如果低于 0 就死亡
2. chooseAction 借助 getNeighborsOfTypes
3. SampleCreatures 是一个示例 后序的两种生物外观和 他一样，因此参考他
### SC

## Plips
1. 懒惰但能移动的光合生物，大多时候站在原地生长 繁殖，遇到天敌Clorus 迅速逃离