import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;
    private double[][] energy;
    private int width;
    private int height;

    // 使用一张图片初始化 SeamCarver
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture); // 深拷贝防止外部修改
        this.width = picture.width();
        this.height = picture.height();
        energy = new double[picture.width()][picture.height()];
        calculateEnergy();
    }

    private void calculateEnergy() {
        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                energy[i][j] = energy(i, j);
            }
        }
    }

    // 返回当前的图片
    public Picture picture() {
        return picture;
    }

    // 返回当前图片的宽度（单位：像素列数）
    public int width() {
        return width;
    }

    // 返回当前图片的高度（单位：像素行数）
    public int height() {
        return height;
    }

    // 返回像素 (x, y) 的能量值（用于判断其重要性）
    public double energy(int x, int y) {
        // 使用双梯度能量函数
        // x 方向上是左右两边图像的 RGB 之差 的平方和
        // y 方向上类似
        // 如果是边缘就用一个环的方式计算
        int leftX = (x - 1 + width) % width;
        int rightX = (x + 1) % width;

        int upY = (y - 1 + height) % height;
        int downY = (y + 1) % height;

        Color left = picture.get(leftX, y);
        Color right = picture.get(rightX, y);
        Color up = picture.get(x, upY);
        Color down = picture.get(x, downY);

        int dxR = right.getRed() - left.getRed();
        int dxG = right.getGreen() - left.getGreen();
        int dxB = right.getBlue() - left.getBlue();
        int deltaX2 = dxR * dxR + dxG * dxG + dxB * dxB;

        int dyR = down.getRed() - up.getRed();
        int dyG = down.getGreen() - up.getGreen();
        int dyB = down.getBlue() - up.getBlue();
        int deltaY2 = dyR * dyR + dyG * dyG + dyB * dyB;

        return (double) (deltaY2 + deltaX2);
    }


    // 找到一条水平的 seam（即从左到右的一条路径），返回路径上每列对应的行索引序列
    // 这里是要找到一条最短路径的问题
    public int[] findHorizontalSeam() {
        return findSeam(true);
    }

    // 找到一条垂直的 seam（即从上到下的一条路径），返回路径上每行对应的列索引序列
    public int[] findVerticalSeam() {
        return findSeam(false);
    }

    // 将水平和竖直方向上的 方法 合在一起
    private int[] findSeam(boolean isHorizontal) {
        int w = isHorizontal ? width : height;
        int h = isHorizontal ? height : width;

        double[][] disTo = new double[w][h];
        int[][] edgeTo = new int[w][h];

        // 初始化
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                disTo[x][y] = Double.MAX_VALUE;
            }
        }

        for (int y = 0; y < h; y++) {
            disTo[0][y] = energy[0][y];
        }

        for (int x = 1; x < w; x++) {
            for (int y = 0; y < h; y++) {
                // 三个方向
                for (int dy = -1; dy <= 1; dy++) {
                    int prevY = y + dy;
                    // 前一个 Y 坐标是有效的
                    if (prevY >= 0 && prevY < h) {
                        double e = isHorizontal ? energy[x][y] : energy[y][x];
                        double preDis = disTo[x - 1][prevY];
                        double dis = preDis + e;
                        // 如果小于当前的dis 我们就更新因为目标是找到最短的路径
                        if (dis < disTo[x][y]) {
                            disTo[x][y] = dis;
                            edgeTo[x][y] = prevY;
                        }
                    }
                }
            }
        }

        // 找到最短的 dist
        double minDist = Double.MAX_VALUE;
        int minIndex = -1;
        for (int y = 0; y < h; y++) {
            if (minDist > disTo[w - 1][y]) {
                minDist = disTo[w - 1][y];
                minIndex = y;
            }
        }

        int[] path = new int[w];
        path[w - 1] = minIndex;
        // 因为这里我们 path 保存的是 y 方向上的坐标
        // 所以
        for (int x = w - 1; x > 0; x--) {
            path[x - 1] = edgeTo[x][path[x]];
        }

        return path;
    }


    // 删除指定的水平 seam 路径（每列移除指定行的像素）
    public void removeHorizontalSeam(int[] seam) {
        removeSeam(true, seam);
    }

    // 删除指定的垂直 seam 路径（每行移除指定列的像素）
    public void removeVerticalSeam(int[] seam) {
        removeSeam(false, seam);
    }

    // 行和列的问题一定要画图理解
    private void removeSeam(boolean isHorizontal, int[] seam) {
        if ((isHorizontal && (height <= 1 || seam.length != width)) ||
                (!isHorizontal && (width <= 1 || seam.length != height))) {
            throw new IllegalArgumentException("Invalid seam size");
        }


        for (int i = 0; i < seam.length; i++) {
            int val = seam[i];
            int bound = isHorizontal ? width : height;
            // seam 不是有效值
            if (val < 0 || val >= bound) {
                throw new IllegalArgumentException("seam index out of bound");
            }
            // 不能够构成一条路径
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException("seam discontinuous");
            }
        }

        Picture newPic = isHorizontal ?
                new Picture(width, height - 1) :
                new Picture(width - 1, height);

        // 重新复制一副新的图画，跳过要去掉的部分
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isHorizontal) {
                    if (y < seam[x]) {
                        newPic.set(x, y, picture.get(x, y));
                    } else if (y > seam[x]) {
                        newPic.set(x, y - 1, picture.get(x, y));
                    }
                } else {
                    if (x < seam[y]) {
                        newPic.set(x, y, picture.get(x, y));
                    } else if (x > seam[y]) {
                        newPic.set(x - 1, y, picture.get(x, y));
                    }
                }
            }
        }

        this.picture = newPic;
        this.width = picture.width();
        this.height = picture.height();
        this.energy = new double[width][height];
        calculateEnergy();

    }
}
