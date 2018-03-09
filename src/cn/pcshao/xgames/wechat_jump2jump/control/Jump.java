package cn.pcshao.xgames.wechat_jump2jump.control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.skyline.wxjumphack.MyPosFinder;
import com.skyline.wxjumphack.NextCenterFinder;
import com.skyline.wxjumphack.WhitePointFinder;

import cn.pcshao.xgames.wechat_jump2jump.bean.Image;
import cn.pcshao.xgames.wechat_jump2jump.bean.Postion;
import cn.pcshao.xgames.wechat_jump2jump.dao_adb.ADB_tools;
import cn.pcshao.xgames.wechat_jump2jump.service.Jump_main;;

/**
 * 2018/3/9
 * @author pcshao-mini pcshao.cn
 *
 */
public class Jump implements Jump_main{
	
	/**
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {

		Jump jump = new Jump();
		BufferedImage bufferedImage = null;
		double JUMP_RATIO = 1.450f;
		double jumpRatio = 0;
		//程序目录
        String root = Jump.class.getResource("/").getPath();
         System.out.println("root: " + root);
        File rootDir = new File(root, "imgs/input");
        rootDir.mkdirs();
         System.out.println("srcDir: " + rootDir.getAbsolutePath());
        //连接手机
		File file = new File(rootDir + ".png");
	    if (file.exists()) {
	         file.deleteOnExit();
	    }
	    for (int i=0;i<1000;i++){
	        jump.Connect_phone(file);
	        //获取图像
	        bufferedImage = jump.GetImage(file.getAbsolutePath());
	        //图像识别
	        if (jumpRatio == 0) {
	            jumpRatio = JUMP_RATIO * 1080 / bufferedImage.getWidth();
	        }
	        Postion postion = jump.GetPostion(bufferedImage);
	        //计算距离
	        int distance = (int) (jump.Distance(postion)*jumpRatio);
	        //控制输出
	        Random random = new Random();
	        jump.Control_phone(distance);
	        //线程时延
	        try {
				Thread.sleep(2_000+random.nextInt(100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	@Override
	public void Connect_phone(File file) {
		ADB_tools adb_tools = new ADB_tools();
		//截图
		adb_tools.Screnntshot();
		//传送截图
	    adb_tools.Pullimage(file);
	}

	@Override
	public void Control_phone(int distance) {
		ADB_tools adb_tools = new ADB_tools();
	    Random RANDOM = new Random();
        int pressX = 400 + RANDOM.nextInt(100);
        int pressY = 500 + RANDOM.nextInt(100);
        adb_tools.Swipe(pressX, pressY, distance);
	}

	@Override
	public BufferedImage GetImage(String path) {
		BufferedImage bufferedImage = null;
		Image image = new Image();
		try {
			bufferedImage = image.load(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bufferedImage;
	}

	//重新获得位置方法
	@Override
	public Postion GetPostion(BufferedImage bufferedImage) {
		Postion postion = new Postion();
		NextCenterFinder nextcenter = new NextCenterFinder();
		MyPosFinder myPosFinder = new MyPosFinder();
		WhitePointFinder whitePointFinder = new WhitePointFinder();
		//自己位置
		postion.setMyPos(myPosFinder.find(bufferedImage));
		if(postion !=null) {
			//下一跳
			int[] nextCenter = nextcenter.find(bufferedImage, postion.getMyPos());
			if (nextCenter != null || nextCenter[0] != 0) {
				int[] whitePoint = whitePointFinder.find(bufferedImage, nextCenter[0] - 120, nextCenter[1], nextCenter[0] + 120, nextCenter[1] + 180);
				if (whitePoint != null) {
                    //有中心白点时的坐标
					postion.setCenterx(whitePoint[0]);
					postion.setCentery(whitePoint[1]);
                } else {
                    //无中心白点时的坐标
                	//中心横纵坐标=左右横纵坐标和除以2
                    if (nextCenter[2] != Integer.MAX_VALUE && nextCenter[4] != Integer.MIN_VALUE) {
                        postion.setCenterx((nextCenter[2] + nextCenter[4]) / 2);
                        postion.setCentery((nextCenter[3] + nextCenter[5]) / 2);
                    } else {
                    	postion.setCenterx(nextCenter[0]);
                    	postion.setCentery(nextCenter[0] + 48);
                    }
                }			
			}else {
                System.err.println("find nextCenter, fail");
            }
		}
		return postion;
	}
	
	//多继承自获得位置
	

	@Override
	public int Distance(Postion postion) {
		
		System.out.println( "IS"+(int)( Math.sqrt(
				postion.getCenterx()-postion.getMx()*postion.getCenterx()-postion.getMx() )+
				( postion.getCentery()-postion.getMy() )*( postion.getCentery()-postion.getMy() ) 
				) );
        //距离计算公式
        int distance = (int) (Math.sqrt(
        		Math.pow( postion.getCenterx() - postion.getMx(), 2 )
        	  + Math.pow( postion.getCentery() - postion.getMy(), 2 )
        			  ));
        System.out.println("distance: " + distance);
        
       return distance;
	}
	

}
