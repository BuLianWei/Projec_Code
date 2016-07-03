package com.example.remeber.ui.activity;




import org.apache.http.conn.routing.RouteInfo.LayerType;

import com.example.remeber.R;
import com.example.util.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.RenderScript.Priority;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.ProgressBar;
public class ScanActivity extends BaseActivity {
	
	private WebView webView;
	private ProgressBar pBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanning);
		Intent intent=getIntent();
		webView=(WebView) findViewById(R.id.webView1);
		pBar = (ProgressBar) findViewById(R.id.progressBar1);
		WebSettings settings=webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDefaultTextEncodingName("UTF-8");
		settings.setLoadWithOverviewMode(true);
		settings.setUseWideViewPort(true);
		webView.loadUrl(intent.getStringExtra("result"));
		webView.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int progress) {

				if (progress == 100) {
					pBar.setVisibility(View.GONE);
				} else {
					pBar.setProgress(progress);
					pBar.setVisibility(View.VISIBLE);
				}

			}
		});
	}

}
