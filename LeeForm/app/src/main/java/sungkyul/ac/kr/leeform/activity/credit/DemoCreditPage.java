package sungkyul.ac.kr.leeform.activity.credit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import sungkyul.ac.kr.leeform.R;

public class DemoCreditPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_credit_page);

        String url = "https://web.nicepay.co.kr/smart/mainPay.jsp";

        WebView webView = (WebView) findViewById(R.id.webViewCreditPage);
        webView.setWebViewClient(new WebViewClient()); // 이걸 안해주면 새창이 뜸
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setBlockNetworkLoads(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.loadUrl(url);
    }
}
