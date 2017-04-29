package itchat4jtest.demo.demo1;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;

/**
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月28日 上午12:44:10
 * @version 1.0
 *
 */
public class Mytest {
	public static void main(String[] args) {
		String qrPath = "E://itchat4j";
		IMsgHandlerFace msgHandler = new SimpleDemo();
		Wechat wechat = new Wechat(msgHandler, qrPath);
		wechat.start();
	}

}
