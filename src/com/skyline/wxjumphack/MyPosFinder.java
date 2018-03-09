package com.skyline.wxjumphack;

import javax.imageio.ImageIO;
import cn.pcshao.xgames.wechat_jump2jump.bean.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by chenliang on 2017/12/31.
 */
public class MyPosFinder {

    public static final int R_TARGET = 40;

    public static final int G_TARGET = 43;

    public static final int B_TARGET = 86;

    public int[] find(BufferedImage image) {
        if (image == null) {
            return null;
        }
        int width = image.getWidth();
        int height = image.getHeight();
        //ret即要返回的二维坐标值
        int[] ret = {0, 0};
        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        for (int i = 0; i < width; i++) {
            //从图像纵向1/4到3/4进行图形解析
            for (int j = height / 4; j < height * 3 / 4; j++) {
                int pixel = image.getRGB(i, j);
                int r = (pixel & 0xff0000) >> 16;
                int g = (pixel & 0xff00) >> 8;
                int b = (pixel & 0xff);
                /**
                 * 【大萌】()有吃喵？(1316172762)  16:59:57
                 RGB的颜色数值存储的时候，是存成一个数的，其中R G B三个值分别占255。取rgb
                 的时候是单独这么算的
                 比如0x66ccff
                 0x660000 >> 16 ->0x66
                 0x00cc00 >> 8 ->0xcc
                 0x0000ff ->0xff
                 */
                if (ToleranceHelper.match(r, g, b, R_TARGET, G_TARGET, B_TARGET, 16)) {
                    maxX = Integer.max(maxX, i);
                    minX = Integer.min(minX, i);
                    maxY = Integer.max(maxY, j);
                    minY = Integer.min(minY, j);
                }
            }
        }
        ret[0] = (maxX + minX) / 2 +3;
        ret[1] = maxY;
        System.out.println(maxX + ", " + minX);
        System.out.println("pos, x: " + ret[0] + ", y: " + ret[1]);
        return ret;
    }

    public static void main(String... strings) throws IOException {
    	Image image = new Image();
        MyPosFinder t = new MyPosFinder();
        String root = t.getClass().getResource("/").getPath();
        System.out.println("root: " + root);
        String imgsSrc = root + "imgs/src";
        String imgsDesc = root + "imgs/my_pos";
        File srcDir = new File(imgsSrc);
        System.out.println(srcDir);
        long cost = 0;
        for (File file : srcDir.listFiles()) {
            if (!file.getName().endsWith(".png")) {
                continue;
            }
            System.out.println(file);
            BufferedImage img = image.load(file.getAbsolutePath());
            long t1 = System.nanoTime();
            int[] pos = t.find(img);
            long t2 = System.nanoTime();
            cost += (t2 - t1);
            BufferedImage desc = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            desc.getGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null); // 绘制缩小后的图
            desc.getGraphics().drawRect(pos[0] - 5, pos[1] - 5, 10, 10);
            File descFile = new File(imgsDesc, file.getName());
            if (!descFile.exists()) {
                descFile.mkdirs();
                descFile.createNewFile();
            }
            ImageIO.write(desc, "png", descFile);
        }
        System.out.println("avg time cost: " + (cost / srcDir.listFiles().length / 1_000_000));

    }
}
