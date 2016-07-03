package com.example.getcupversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView mTextView;
	private String header="I_am_Lenovo_SandBox:";
	private String key="i_am_lenovo_sandbox";//密钥
	int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.textView1);
		
	}

	public void get(View v) {
		
		if (i%2==0) {
			writeDataToSD();
		}else {
			try {
				mTextView.setText(AESUtil.decrypt(key, readDataFromSD()));
			} catch (Exception e) {
				e.printStackTrace();
			};
			
		}
		i++;
		
	}

	public static String getCPUSerial() {
		String str = "", strCPU = "", cpuAddress = "0000000000000000";
		try {
			// 读取CPU信息
			Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			// 查找CPU序列号
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					// 查找到序列号所在行
					if (str.indexOf("Serial") > -1) {
						// 提取序列号
						strCPU = str.substring(str.indexOf(":") + 1,
								str.length());
						// 去空格
						cpuAddress = strCPU.trim();
						break;
					}
				} else {
					// 文件结尾
					break;
				}
			}
		} catch (Exception ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return cpuAddress;
	}

	/* 将文件数据写入sdcard中，保存数据 */
	public void writeDataToSD() {
		try {
			/* 获取File对象，确定数据文件的信息 */
			// File file = new
			// File(Environment.getExternalStorageDirectory()+"/f.txt");
			File file = new File(Environment.getExternalStorageDirectory(),
					"license.txt");

			/* 判断sd的外部设置状态是否可以读写 */
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {

				/* 流的对象 *//*  */
				FileOutputStream fos = new FileOutputStream(file);

				/* 需要写入的数据 */
//				String message = key;
				String message = AESUtil.encrypt(key, header+getCPUSerial());

				/* 将字符串转换成字节数组 */
				byte[] buffer = message.getBytes();

				/* 开始写入数据 */
				fos.write(buffer);

				/* 关闭流的使用 */
				fos.close();
				Toast.makeText(MainActivity.this, "文件写入成功", 1000).show();
			}

		} catch (Exception ex) {
			Toast.makeText(MainActivity.this, "文件写入失败", 1000).show();
		}

	}
	
	public String readDataFromSD() {
		String result = "";
		try {

			/* 创建File对象，确定需要读取文件的信息 */
			File file = new File(Environment.getExternalStorageDirectory(),
					"license.txt");

			/* FileInputSteam 输入流的对象， */
			FileInputStream fis = new FileInputStream(file);

			/* 准备一个字节数组用户装即将读取的数据 */
			byte[] buffer = new byte[fis.available()];

			/* 开始进行文件的读取 */
			fis.read(buffer);

			/* 关闭流 */
			fis.close();

			/* 将字节数组转换成字符创， 并转换编码的格式 */
			result = EncodingUtils.getString(buffer, "UTF-8");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	
}
