/******************************************************************************
 *  编译:       javac SCUtility.java
 *  运行:       无需运行
 *  依赖项:     SeamCarver.java
 *
 *  用于测试 SeamCarver.java 的一些实用函数。
 *
 ******************************************************************************/

import java.awt.Color;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdRandom;

public class SCUtility {

    // 创建一个宽为 W、高为 H 的随机图片
    public static Picture randomPicture(int W, int H) {
        Picture picture = new Picture(W, H);
        for (int col = 0; col < W; col++) {
            for (int row = 0; row < H; row++) {
                int r = StdRandom.uniform(256);
                int g = StdRandom.uniform(256);
                int b = StdRandom.uniform(256);
                Color color = new Color(r, g, b);
                picture.set(col, row, color);
            }
        }
        return picture;
    }

    // 将 SeamCarver 中的图片转换为宽 W、高 H 的能量矩阵
    public static double[][] toEnergyMatrix(SeamCarver sc) {
        double[][] a = new double[sc.width()][sc.height()];
        for (int col = 0; col < sc.width(); col++)
            for (int row = 0; row < sc.height(); row++)
                a[col][row] = sc.energy(col, row);
        return a;
    }

    // 将灰度值显示为能量图（转换为图片后调用 show 方法）
    public static void showEnergy(SeamCarver sc) {
        doubleToPicture(toEnergyMatrix(sc)).show();
    }

    // 返回与 SeamCarver 图片对应的能量图像
    public static Picture toEnergyPicture(SeamCarver sc) {
        double[][] energyMatrix = toEnergyMatrix(sc);
        return doubleToPicture(energyMatrix);
    }

    // 将 double 类型的值矩阵转换为归一化后的图像
    // 所有值通过最大灰度值进行归一化
    public static Picture doubleToPicture(double[][] grayValues) {

        // 每个一维数组代表图像的一列，因此一维数组的数量是宽度，每个数组的长度是高度
        int width = grayValues.length;
        int height = grayValues[0].length;

        Picture picture = new Picture(width, height);

        double maxVal = 0;
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (grayValues[col][row] > maxVal) {
                    maxVal = grayValues[col][row];
                }
            }
        }

        if (maxVal == 0)
            return picture;  // 返回全黑图像

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                float normalizedGrayValue = (float) grayValues[col][row] / (float) maxVal;
                Color gray = new Color(normalizedGrayValue, normalizedGrayValue, normalizedGrayValue);
                picture.set(col, row, gray);
            }
        }

        return picture;
    }


    // 该方法在调试 seam 时很有用。它在计算出的 seam 上覆盖红色像素。
    public static Picture seamOverlay(Picture picture, boolean isHorizontal, int[] seam) {
        Picture overlaid = new Picture(picture);

        // 如果是水平 seam，则在每一列设置一个像素
        if (isHorizontal) {
            for (int col = 0; col < picture.width(); col++)
                overlaid.set(col, seam[col], Color.RED);
        }

        // 如果是垂直 seam，则在每一行设置一个像素
        else {
            for (int row = 0; row < picture.height(); row++)
                overlaid.set(seam[row], row, Color.RED);
        }

        return overlaid;
    }

}
