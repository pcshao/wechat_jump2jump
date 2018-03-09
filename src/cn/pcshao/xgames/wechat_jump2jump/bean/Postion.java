package cn.pcshao.xgames.wechat_jump2jump.bean;

/**
 * 2018/3/9
 * @author pcshao-mini pcshao.cn
 *
 */
public class Postion {
	
	private int mx;
	private int my;
	private int centerx;
	private int centery;
	private int topx;
	private int topy;
	private int leftx;
	private int lefty;
	private int rightx;
	private int righty;
	private int[] mypos ;
	
	public void setMyPos(int[] xy) {
		this.mypos=xy;
		this.mx=xy[0];
		this.my=xy[1];
	}
	
	public int[] getMyPos() {
		return mypos;
	}
	
	public Postion(){
		
	}
	
	public int getMx() {
		return mx;
	}
	public void setMx(int mx) {
		this.mx = mx;
	}
	public int getMy() {
		return my;
	}
	public void setMy(int my) {
		this.my = my;
	}
	public int getTopx() {
		return topx;
	}
	public void setTopx(int topx) {
		this.topx = topx;
	}
	public int getTopy() {
		return topy;
	}
	public void setTopy(int topy) {
		this.topy = topy;
	}
	public int getLeftx() {
		return leftx;
	}
	public void setLeftx(int leftx) {
		this.leftx = leftx;
	}
	public int getLefty() {
		return lefty;
	}
	public void setLefty(int lefty) {
		this.lefty = lefty;
	}
	public int getRightx() {
		return rightx;
	}
	public void setRightx(int rightx) {
		this.rightx = rightx;
	}
	public int getRighty() {
		return righty;
	}
	public void setRighty(int righty) {
		this.righty = righty;
	}

	public int getCenterx() {
		return centerx;
	}

	public void setCenterx(int centerx) {
		this.centerx = centerx;
	}

	public int getCentery() {
		return centery;
	}

	public void setCentery(int centery) {
		this.centery = centery;
	}
	

}
