import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

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

        return deltaY2 + deltaX2;
    }


    // 找到一条水平的 seam（即从左到右的一条路径），返回路径上每列对应的行索引序列
    // 这里是要找到一条最短路径的问题
    public int[] findHorizontalSeam() {
        double[][] disTo = new double[width][height];
        int[][] edgeTo = new int[width][height];

        // 初始化
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                disTo[x][y] = Double.MAX_VALUE;
            }
        }


        for (int y = 0; y < height; y++) {
            disTo[0][y] = energy[0][y];
        }

        for (int x = 1; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double e = energy[x][y];
                // 三个方向
                for (int dy = -1; dy <= 1; dy++) {
                    int prevY = y + dy;
                    // 前一个 Y 坐标是有效的
                    if (prevY >= 0 && prevY < height) {
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
        for (int y = 0; y < height; y++) {
            if (minDist > disTo[width - 1][y]) {
                minDist = disTo[width - 1][y];
                minIndex = y;
            }
        }

        int[] path = new int[width];
        path[width - 1] = minIndex;
        // 因为这里我们 path 保存的是 y 方向上的坐标
        // 所以
        for (int x = width - 1; x > 0; x--) {
            path[x - 1] = edgeTo[x][path[x]];
        }

        return path;
    }

    // 找到一条垂直的 seam（即从上到下的一条路径），返回路径上每行对应的列索引序列
    public int[] findVerticalSeam() {
        double[][] disTo = new double[width][height];
        int[][] edgeTo = new int[width][height];

        // 初始化
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                disTo[x][y] = Double.MAX_VALUE;
            }
        }

        // 初始化第一行的距离
        for (int x = 0; x < width; x++) {
            disTo[x][0] = energy[x][0];
        }

        // 动态规划计算从上到下的最短路径
        for (int y = 1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double e = energy[x][y];
                for (int dx = -1; dx <= 1; dx++) {
                    int prevX = x + dx;
                    if (prevX >= 0 && prevX < width) {
                        double preDis = disTo[prevX][y - 1];
                        double dis = preDis + e;
                        if (dis < disTo[x][y]) {
                            disTo[x][y] = dis;
                            edgeTo[x][y] = prevX;
                        }
                    }
                }
            }
        }

        // 找到最后一行中能量最小的终点
        double minDist = Double.MAX_VALUE;
        int minIndex = -1;
        for (int x = 0; x < width; x++) {
            if (minDist > disTo[x][height - 1]) {
                minDist = disTo[x][height - 1];
                minIndex = x;
            }
        }

        // 回溯路径
        int[] path = new int[height];
        path[height - 1] = minIndex;
        for (int y = height - 1; y > 0; y--) {
            path[y - 1] = edgeTo[path[y]][y];
        }

        return path;
    }


    // 删除指定的水平 seam 路径（每列移除指定行的像素）
    public void removeHorizontalSeam(int[] seam) {
        if (!isValidSeam(seam)) {
            throw new IllegalArgumentException("seam is not continuous");
        }
        SeamRemover.removeHorizontalSeam(picture, seam);
    }

    // 删除指定的垂直 seam 路径（每行移除指定列的像素）
    public void removeVerticalSeam(int[] seam) {
        if (!isValidSeam(seam)) {
            throw new IllegalArgumentException("seam is not continuous");
        }
        SeamRemover.removeVerticalSeam(picture, seam);
    }

    private boolean isValidSeam(int[] seam) {
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                return false;
            }
        }
        return true;
    }
}
