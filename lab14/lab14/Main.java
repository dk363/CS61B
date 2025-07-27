package lab14;

import lab14lib.*;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		/** Your code here. */
		/**
		// 输出一个 440 Hz 的正弦波样本
		Generator generator = new SineWaveGenerator(440);
		// 创建一个播放器
		GeneratorPlayer gp = new GeneratorPlayer(generator);
		// 将生成器的前 1000000 个样本作为声音播放
		gp.play(1000000);
		 */

		/**
		 * 波形生成器
		Generator generator = new SineWaveGenerator(200);
		GeneratorDrawer gd = new GeneratorDrawer(generator);
		gd.draw(4096);
		*/

		/**
		 * 波形生成器 + 声音播放
		Generator generator = new SineWaveGenerator(200);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(4096, 1000000);
		 */

		/**
		 * 两种不同频率的声音合在一起
		 * 拍音
		Generator g1 = new SineWaveGenerator(60);
		Generator g2 = new SineWaveGenerator(61);

		ArrayList<Generator> generators = new ArrayList<>();
		generators.add(g1);
		generators.add(g2);
		MultiGenerator mg = new MultiGenerator(generators);

		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(mg);
		gav.drawAndPlay(500000, 1000000);
		 */

		/**
		 * 生成锯齿波
		Generator generator = new SawToothGenerator(512);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(4096, 1000000);
		 */


		/** 生成加速锯齿波
		Generator generator = new AcceleratingSawToothGenerator(100, 1.1);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(4096, 1000000);
		 */

		// 神秘时刻
		Generator generator = new StrangeBitwiseGenerator(1024);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(128000, 1000000);
	}
} 