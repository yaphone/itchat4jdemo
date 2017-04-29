# itchat4j -- 示例程序

## 说明

此项目为[itchat4j](https://github.com/yaphone/itchat4j)的示例项目，可直接作为工程导入Eclipse，目前项目结构为：

![项目结构](http://oj5vdtyuu.bkt.clouddn.com/itchat4jdemo%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84.png)

在该示例项目中，demo1包中为简单示例项目，收到文本信息直接返回原消息给发送者，图片、语音、小视频文件根据路径保存，demo2包中为图灵机器人示例，demo3包请忽略。(是我专门为Windows平台做的一个小示例，打包后可直接在windows平台下运行)

## 项目介绍

### Windows平台可直接运行jar包

为了更直观地演示，我为Windows平台做了一个可运行的版本，如果你的操作系统是Windows，可直接[下载示例项目的release版本](https://github.com/yaphone/itchat4jdemo/releases/tag/V0.0.1)，然后在你的`D`盘根目录下（必须是`D`盘根目录）新建`itchat4j`目录，然后在此目录下新建`login`、`pic`、`viedo`、`voice`四个子目录，就像这样：

![目录结构](http://oj5vdtyuu.bkt.clouddn.com/%E7%9B%AE%E5%BD%95%E7%BB%93%E6%9E%84.png)

然后配置好你的Java环境变量，通过命令`java -jar 路径/windows_itchat4j.jar`来执行程序，之后，打开`D:/login`文件夹，扫描二维码即可登陆，登陆成功后，可接收文本消息，收到图片、语音、小视频消息后会保存在对应目录：

![Windows控制台](http://oj5vdtyuu.bkt.clouddn.com/Windows%E6%8E%A7%E5%88%B6%E5%8F%B0.png)

![文件保存](http://oj5vdtyuu.bkt.clouddn.com/windows%E6%94%B6%E5%88%B0%E6%96%87%E4%BB%B6.png)



## 入门教程

接下来，通过两个小Demo来演示一下如何使用itchat4j来扩展你的个人微信号。

### Demo1: SimpleDemo

这个小Demo将会将收到的文本消息发送给发件人，如果是图片、语音或者小视频消息，将会保存在我们指定的路径下。

首先需要新建一个类来实现`IMsgHandlerFace`这个接口，这个类要做的就是我们需要完成的逻辑，该接口有四个方法需要实现，`textMsgHandle`用于处理文本信息，`picMsgHandle`用于处理图片信息，`viedoMsgHandle`用于处理小视频信息，`voiceMsgHandle`用于处理语音信息，代码如下：

```java
public class MsgHandler implements IMsgHandlerFace {

	@Override
	public String picMsgHandle(JSONObject arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String textMsgHandle(JSONObject arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String viedoMsgHandle(JSONObject arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String voiceMsgHandle(JSONObject arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
```

由于没有关联源码，所以接口中的参数都变成了`arg0`这种，建议关联一下源码，源码可在[release](https://github.com/yaphone/itchat4j/releases)中下载，当然不关联也不会有啥影响，`arg0`其实是我们需要处理的消息体，为了更直观，建议把`arg0`修改为`msg`，msg是fastjson的JSONObject类型，这个其实不用关心，我们只需要知道如何来获取需要的消息就可以了，下面的Demo中有示例。然后我们来写处理逻辑。

在`textMsgHandler`中，通过`msg.getString("Text")`就可以获取收到的文本信息，然后作进一步处理，比如接入图灵机器人、消息自动回复等，我们需要在这个方法中返回一个字符串，即是需要回复给好友的消息，在SimpleDemo这个示例中，我们直接回复收到的原文本消息。

在`picMsgHandle`、`voiceMsgHandle`、`viedoMsgHandle`这三个方法中，我们需要将这些消息下载下来，然后再作进一步处理，所以需要为每种类型的消息提供一个保存路径，然后调用`DownloadTools.getDownloadFn`方法可以将这三种类型的消息下载下来。`DownloadTools.getDownloadFn`方法提供下载图片、语音、小视频的功能，需要三个参数，第一个参数为我们收到的msg，第二个参数为`MsgType`，也就是消息类型，图片、语音、小视频分别对应`MsgType.PIC`、`MsgType.VOICE`、`MsgType.VOICE`，然后第三个参数就是保存这些消息的路径了。

就不多说了，让代码和注释君自述吧，有不明白的地方，可以在Issue中提出来。

```java
/**
 * 简单示例程序，收到文本信息自动回复原信息，收到图片、语音、小视频后根据路径自动保存
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月28日 下午10:50:36
 * @version 1.0
 *
 */
public class SimpleDemo implements IMsgHandlerFace {

	@Override
	public String textMsgHandle(JSONObject msg) {
		String text = msg.getString("Text");
		return text;
	}

	@Override
	public String picMsgHandle(JSONObject msg) {
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg"; // 这里使用收到图片的时间作为文件名
		String picPath = "D://itchat4j/pic" + File.separator + fileName; // 保存图片的路径
		DownloadTools.getDownloadFn(msg, MsgType.PIC, picPath); // 调用此方法来保存图片
		return "图片保存成功";
	}

	@Override
	public String voiceMsgHandle(JSONObject msg) {
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp3"; // 这里使用收到语音的时间作为文件名
		String voicePath = "D://itchat4j/voice" + File.separator + fileName; // 保存语音的路径
		DownloadTools.getDownloadFn(msg, MsgType.VOICE, voicePath); // 调用此方法来保存语音
		return "声音保存成功";
	}

	@Override
	public String viedoMsgHandle(JSONObject msg) {
		System.out.println(msg);
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp4"; // 这里使用收到小视频的时间作为文件名
		String viedoPath = "D://itchat4j/viedo" + File.separator + fileName;// 保存小视频的路径
		DownloadTools.getDownloadFn(msg, MsgType.VIEDO, viedoPath);// 调用此方法来保存小视频
		return "视频保存成功";
	}

}
```

之后我们需要将实现`IMsgHandlerFace`接口的类【注入】到`Wechat`中来启动服务，`Wechat`是服务的主入口，其构造函数接受两个参数，一个是我们刚才实现`IMsgHandlerFace`接口的类，另一个是保存登陆二维码图片的路径。之后在`Wechat`对象上调用`start()`方法来启动服务，会在我们刚才传入的路径下生成一个`QR.jpg`文件，即是登陆二维码，通过手机微信扫描后即可登陆，服务启动，处理逻辑开始工作。这里有一点需要注意：*二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片*。

额，文字还是太苍白，让代码和注释君自述吧。

```Java
/**
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月28日 上午12:44:10
 * @version 1.0
 *
 */
public class Mytest {
	public static void main(String[] args) {
		String qrPath = "D://itchat4j//login"; // 保存登陆二维码图片的路径
		IMsgHandlerFace msgHandler = new SimpleDemo(); // 实现IMsgHandlerFace接口的类
		Wechat wechat = new Wechat(msgHandler, qrPath); // 【注入】
		wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
	}
}
```

### Demo2 图灵机器人

> 图灵机器人大脑具备强大的中文语义分析能力，可准确理解中文含义并作出回应，是最擅长聊中文的机器人大脑，赋予软硬件产品自然流畅的人机对话能力。(引自百度百科)

这个示例中我们接入图灵机器人的API，将收到的好友的文本信息发送给图灵机器人，并将机器人回复的消息发送给好友，接下来还是把舞台交代码和注释君吧。

```Java
/**
 * 图灵机器人示例
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月24日 上午12:13:26
 * @version 1.0
 *
 */
public class TulingRobot implements IMsgHandlerFace {

	MyHttpClient myHttpClient = new MyHttpClient();
	String apiKey = "597b34bea4ec4c85a775c469c84b6817"; // 这里是我申请的图灵机器人API接口，每天只能5000次调用，建议自己去申请一个，免费的:)
	Logger logger = Logger.getLogger("TulingRobot");

	@Override
	public String textMsgHandle(JSONObject msg) {
		String result = "";
		String text = msg.getString("Text");
		String url = "http://www.tuling123.com/openapi/api";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("key", apiKey);
		paramMap.put("info", text);
		paramMap.put("userid", "123456");
		String paramStr = JSON.toJSONString(paramMap);
		try {
			HttpEntity entity = myHttpClient.doPost(url, paramStr);
			result = EntityUtils.toString(entity, "UTF-8");
			JSONObject obj = JSON.parseObject(result);
			if (obj.getString("code").equals("100000")) {
				result = obj.getString("text");
			} else {
				result = "处理有误";
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return result;
	}

	@Override
	public String picMsgHandle(JSONObject msg) {

		return "收到图片";
	}

	@Override
	public String voiceMsgHandle(JSONObject msg) {

		return "收到语音";
	}

	@Override
	public String viedoMsgHandle(JSONObject msg) {

		return "收到视频";
	}

	public static void main(String[] args) {
		IMsgHandlerFace msgHandler = new TulingRobot();
		Wechat wechat = new Wechat(msgHandler, "/home/itchat4j/demo/itchat4j/login");
		wechat.start();
	}

}
```



## 问题和建议

本项目长期更新、维护，功能不断扩展与完善中，欢迎star。

项目使用过程中遇到问题，欢迎随时反馈。

任何问题或者建议都可以在Issue中提出来，也可以加入QQ群讨论：636365179

