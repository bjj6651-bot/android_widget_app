package com.example.timetablewidget

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var statusTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)

        // 위젯 시작 버튼
        startButton.setOnClickListener {
            if (canDrawOverlays()) {
                startFloatingWidget()
            } else {
                requestOverlayPermission()
            }
        }

        // 위젯 중지 버튼
        stopButton.setOnClickListener {
            stopFloatingWidget()
        }

        updateStatus()
    }

    /**
     * 오버레이 권한 확인
     */
    private fun canDrawOverlays(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(this)
        } else {
            true
        }
    }

    /**
     * 오버레이 권한 요청
     */
    private fun requestOverlayPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
        statusTextView.text = "권한을 허용해주세요"
    }

    /**
     * Floating Widget 시작
     */
    private fun startFloatingWidget() {
        val intent = Intent(this, FloatingWidgetService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        statusTextView.text = "위젯이 시작되었습니다"
        updateStatus()
    }

    /**
     * Floating Widget 중지
     */
    private fun stopFloatingWidget() {
        val intent = Intent(this, FloatingWidgetService::class.java)
        stopService(intent)
        statusTextView.text = "위젯이 중지되었습니다"
        updateStatus()
    }

    /**
     * 상태 업데이트
     */
    private fun updateStatus() {
        val isRunning = isFloatingWidgetRunning()
        startButton.isEnabled = !isRunning
        stopButton.isEnabled = isRunning
    }

    /**
     * 위젯 실행 여부 확인
     */
    private fun isFloatingWidgetRunning(): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as android.app.ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.service.className == FloatingWidgetService::class.java.name) {
                return true
            }
        }
        return false
    }
}
