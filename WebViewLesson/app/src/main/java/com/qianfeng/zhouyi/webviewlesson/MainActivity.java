package com.qianfeng.zhouyi.webviewlesson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.webviewTest)
    private WebView webView;

    @ViewInject(R.id.edtUrl)
    private EditText edtUrl;

    @ViewInject(R.id.imbGo)
    private ImageButton imbGo;

    @ViewInject(R.id.progresbar)
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        initWebView();


    }

    protected void initWebView(){
        //webView.loadUrl("file:///sdcard/face.jpg");//加载本地图片
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//允许使用js

        //让网页或本地图片自适应屏幕大小
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        //设置webviewclient，这样的话，网页就会在app里加载，而不会调用浏览器加载
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.contains("image.baidu.com")){
                // Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
//                }
                //view.loadUrl(url);
                edtUrl.setText(url);
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);//显示当前加载的进度
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                    imbGo.setVisibility(View.GONE);
                }
            }
        });





        edtUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strText = s.toString();
                if (!strText.equals("")) {
                    imbGo.setVisibility(View.VISIBLE);//如果输入的网址不为空，显示右侧的按钮
                } else {
                    imbGo.setVisibility(View.GONE);//如果输入的网址为空，不显示右侧的按钮
                }
            }
        });

        //把安卓原生的java代码映射到js中的一个对象中，
        // 映射到js的这个对象放在js的Window对象下面，使用Window.jsObj即可调用
        webView.addJavascriptInterface(new JSTest(),"jsObj");

        webView.loadUrl("file:///android_asset/test.html");//加载网页
        edtUrl.setText("file:///android_asset/test.html");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//按下所有键时回调
        //如果按下了返回键，判断网页是否能返回上一页，如果可以，就返回上一页，如果不行，就执行默认操作
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack()){
                webView.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onOpenUrl(View view) {
        webView.loadUrl(edtUrl.getText().toString());
        imbGo.setVisibility(View.GONE);
    }


    //安卓中提供给js调用的所有方法都写在这里，每个方法必须加上@JavascriptInterface这个注解
    class JSTest{
        //安卓原生java代码返回一个字符串给js
        @JavascriptInterface
        public String sayHello(){
            return "hello from android";
        }

        //在js中点击一个按钮，控制安卓中imagebutton显示出来
        @JavascriptInterface
        public void showAndroidButton(){
            //imbGo.setVisibility(View.VISIBLE);

            //如果在js中调用的java原生代码中需要操作ui，必须在主线程中执行
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imbGo.setVisibility(View.VISIBLE);
                }
            });

        }

        //使用安卓中java原生代码调用js中的代码，也必须在主线程中执行
        @JavascriptInterface
        public void excuteHtmlFun(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:showFromHtml('1514')");
                }
            });
        }

    }

}
