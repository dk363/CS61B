package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private double factor;       // 周期变化因子（乘法）
    private int currentPeriod;   // 当前周期（不断变化）
    private int state;           // 当前在周期中的采样位置

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.factor = factor;
        this.currentPeriod = period;
        this.state = 0;
    }

    @Override
    public double next() {
        // 计算当前采样值：将 [0, currentPeriod) 映射为 [-1.0, 1.0)
        double sample = 2.0 * state / currentPeriod - 1.0;

        state++;

        // 如果到达一个周期末尾，重置 state，并更新周期
        if (state >= currentPeriod) {
            state = 0;
            currentPeriod = (int) (currentPeriod * factor);
            if (currentPeriod < 1) {
                currentPeriod = 1; // 安全保护，避免周期为 0
            }
        }

        return sample;
    }
}
