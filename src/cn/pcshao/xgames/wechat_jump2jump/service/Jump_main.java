package cn.pcshao.xgames.wechat_jump2jump.service;

import java.awt.image.BufferedImage;
import java.io.File;

import cn.pcshao.xgames.wechat_jump2jump.bean.Postion;
/**
 * 2018/3/9
 * @author pcshao-mini pcshao.cn
 * 核心部分用的gay ha上另一个大神的算法，此版本意义在于方便java萌新看
 *
 */
public interface Jump_main {
	
	void Connect_phone(File file);
	
	void Control_phone(int distance);
	
	BufferedImage GetImage(String path);
	
	Postion GetPostion(BufferedImage bufferedImage);
	
	int Distance(Postion postion);
	
}
