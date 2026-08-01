package com.example.timetablewidget

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

class FloatingWidgetService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: FrameLayout
    private lateinit var webView: WebView
    private var lastX = 0
    private var lastY = 0
    private var lastTouchX = 0f
    private var lastTouchY = 0f

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // 플로팅 뷰 생성
        floatingView = FrameLayout(this)
        floatingView.setBackgroundColor(android.graphics.Color.TRANSPARENT)

        // WebView 생성
        webView = WebView(this)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
        }
        webView.webViewClient = WebViewClient()

        // WebView를 FrameLayout에 추가
        floatingView.addView(webView, FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ))

        // 웹 콘텐츠 로드
        loadWebContent()

        // 터치 리스너 설정
        floatingView.setOnTouchListener(FloatingTouchListener())

        // WindowManager 파라미터 설정
        val params = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            gravity = Gravity.TOP or Gravity.START
            width = 400  // 너비
            height = 600 // 높이
            x = 0
            y = 0
        }

        // 플로팅 뷰를 윈도우에 추가
        windowManager.addView(floatingView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::floatingView in this && ::windowManager in this) {
            windowManager.removeView(floatingView)
        }
    }

    /**
     * 웹 콘텐츠 로드
     */
    private fun loadWebContent() {
        // 옵션 1: 배포된 웹사이트 로드
        // webView.loadUrl("https://your-deployed-website.com")

        // 옵션 2: 로컬 HTML 파일 로드
        val htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }
                    body {
                        background: linear-gradient(135deg, #2D1B4E 0%, #1A0F2E 100%);
                        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                        height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        overflow: hidden;
                    }
                    .widget-card {
                        background: rgba(10, 10, 10, 0.9);
                        border: 2px solid #FF006E;
                        border-radius: 24px;
                        padding: 24px;
                        width: 95%;
                        max-width: 360px;
                        box-shadow: 0 0 40px rgba(255, 0, 110, 0.3);
                        backdrop-filter: blur(10px);
                    }
                    .class-info {
                        display: flex;
                        justify-content: space-between;
                        margin-bottom: 16px;
                    }
                    .class-item {
                        flex: 1;
                    }
                    .class-item:first-child {
                        text-align: left;
                    }
                    .class-item:last-child {
                        text-align: right;
                    }
                    .label {
                        font-size: 12px;
                        color: #999;
                        margin-bottom: 4px;
                    }
                    .class-name {
                        font-size: 18px;
                        font-weight: bold;
                        color: #fff;
                        margin-bottom: 4px;
                    }
                    .class-time {
                        font-size: 14px;
                        color: #FFA500;
                        font-weight: 600;
                    }
                    .progress-bar {
                        display: flex;
                        gap: 4px;
                        margin: 16px 0;
                        justify-content: center;
                    }
                    .progress-segment {
                        height: 8px;
                        flex: 1;
                        border-radius: 4px;
                        animation: pulse 2s ease-in-out infinite;
                    }
                    @keyframes pulse {
                        0%, 100% { opacity: 1; }
                        50% { opacity: 0.6; }
                    }
                    .time-remaining {
                        text-align: center;
                        color: #ccc;
                        font-size: 12px;
                        margin-bottom: 16px;
                    }
                    .time-value {
                        font-size: 14px;
                        font-weight: 600;
                    }
                </style>
            </head>
            <body>
                <div class="widget-card">
                    <div class="class-info">
                        <div class="class-item">
                            <div class="label">지금</div>
                            <div class="class-name">미적 A</div>
                            <div class="class-time">10:10AM</div>
                        </div>
                        <div class="class-item">
                            <div class="label">다음</div>
                            <div class="class-name">공업 일반 B</div>
                            <div class="class-time">11:10AM</div>
                        </div>
                    </div>
                    
                    <div class="progress-bar">
                        <div class="progress-segment" style="background: #FF0000;"></div>
                        <div class="progress-segment" style="background: #FFA500;"></div>
                        <div class="progress-segment" style="background: #FFFF00;"></div>
                        <div class="progress-segment" style="background: #FFFF00;"></div>
                        <div class="progress-segment" style="background: #00FF00;"></div>
                        <div class="progress-segment" style="background: #0000FF;"></div>
                        <div class="progress-segment" style="background: #8B00FF;"></div>
                        <div class="progress-segment" style="background: #FF00FF;"></div>
                    </div>
                    
                    <div class="time-remaining">
                        종료까지 <span class="time-value" id="timeRemaining">28분</span> 남음
                    </div>
                </div>

                <script>
                    // 실시간 시간 업데이트
                    function updateTime() {
                        const now = new Date();
                        const minutes = now.getMinutes();
                        const seconds = now.getSeconds();
                        document.getElementById('timeRemaining').textContent = 
                            minutes + '분 ' + seconds + '초';
                    }
                    
                    setInterval(updateTime, 1000);
                    updateTime();
                </script>
            </body>
            </html>
        """.trimIndent()

        webView.loadData(htmlContent, "text/html; charset=utf-8", "utf-8")
    }

    /**
     * 터치 리스너 - 드래그 기능
     */
    private inner class FloatingTouchListener : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            event?.let {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastTouchX = it.rawX
                        lastTouchY = it.rawY
                        lastX = (v?.layoutParams as WindowManager.LayoutParams).x
                        lastY = (v?.layoutParams as WindowManager.LayoutParams).y
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val deltaX = (it.rawX - lastTouchX).toInt()
                        val deltaY = (it.rawY - lastTouchY).toInt()

                        val params = v?.layoutParams as WindowManager.LayoutParams
                        params.x = lastX + deltaX
                        params.y = lastY + deltaY

                        windowManager.updateViewLayout(v, params)
                        return true
                    }
                }
            }
            return false
        }
    }
}
