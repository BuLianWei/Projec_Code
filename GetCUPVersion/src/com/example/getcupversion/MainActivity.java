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
	private String key="i_am_lenovo_sandbox";//��Կ
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
			// ��ȡCPU��Ϣ
			Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			// ����CPU���к�
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					// ���ҵ����к�������
					if (str.indexOf("Serial") > -1) {
						// ��ȡ���к�
						strCPU = str.substring(str.indexOf(":") + 1,
								str.length());
						// ȥ�ո�
						cpuAddress = strCPU.trim();
						break;
					}
				} else {
					// �ļ���β
					break;
				}
			}
		} catch (Exception ex) {
			// ����Ĭ��ֵ
			ex.printStackTrace();
		}
		return cpuAddress;
	}

	/* ���ļ�����д��sdcard�У��������� */
	public void writeDataToSD() {
		try {
			/* ��ȡFile����ȷ�������ļ�����Ϣ */
			// File file = new
			// File(Environment.getExternalStorageDirectory()+"/f.txt");
			File file = new File(Environment.getExternalStorageDirectory(),
					"license.txt");

			/* �ж�sd���ⲿ����״̬�Ƿ���Զ�д */
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {

				/* ���Ķ��� *//*  */
				FileOutputStream fos = new FileOutputStream(file);

				/* ��Ҫд������� */
//				String message = key;
				String message = AESUtil.encrypt(key, header+getCPUSerial());

				/* ���ַ���ת�����ֽ����� */
				byte[] buffer = message.getBytes();

				/* ��ʼд������ */
				fos.write(buffer);

				/* �ر�����ʹ�� */
				fos.close();
				Toast.makeText(MainActivity.this, "�ļ�д��ɹ�", 1000).show();
			}

		} catch (Exception ex) {
			Toast.makeText(MainActivity.this, "�ļ�д��ʧ��", 1000).show();
		}

	}
	
	public String readDataFromSD() {
		String result = "";
		try {

			/* ����File����ȷ����Ҫ��ȡ�ļ�����Ϣ */
			File file = new File(Environment.getExternalStorageDirectory(),
					"license.txt");

			/* FileInputSteam �������Ķ��� */
			FileInputStream fis = new FileInputStream(file);

			/* ׼��һ���ֽ������û�װ������ȡ������ */
			byte[] buffer = new byte[fis.available()];

			/* ��ʼ�����ļ��Ķ�ȡ */
			fis.read(buffer);

			/* �ر��� */
			fis.close();

			/* ���ֽ�����ת�����ַ����� ��ת������ĸ�ʽ */
			result = EncodingUtils.getString(buffer, "UTF-8");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	
}
