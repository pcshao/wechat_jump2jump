package cn.pcshao.xgames.wechat_jump2jump.bean;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
/**
 * 2018/3/9
 * @author pcshao-mini pcshao.cn
 *
 */
public class Image {
	
	/**
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public BufferedImage load(String path) throws IOException {
        BufferedImage image = null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(path));
            image = ImageIO.read(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return image;
    }

}
