# 안드로이드 시간표 Floating Widget 앱

## 개요
이 프로젝트는 **Kotlin**으로 작성된 안드로이드 앱으로, 항상 화면 위에 떠있는 시간표 위젯을 제공합니다.

## 기능
- ✅ 항상 화면 위에 떠있는 오버레이 위젯
- ✅ 드래그 가능한 위젯
- ✅ 실시간 시간표 업데이트
- ✅ 현재/다음 수업 표시
- ✅ 남은 시간 표시

## 프로젝트 구조

```
android_widget_app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/timetablewidget/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── FloatingWidgetService.kt
│   │   │   │   └── WidgetReceiver.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   └── widget_layout.xml
│   │   │   │   ├── values/
│   │   │   │   │   └── strings.xml
│   │   │   │   └── AndroidManifest.xml
│   │   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

## 필요한 권한
```xml
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
```

## 빌드 및 실행

### 1. Android Studio에서 프로젝트 열기
```bash
# Android Studio 설치 필요
# 프로젝트 폴더를 Android Studio에서 열기
```

### 2. Gradle 빌드
```bash
./gradlew build
```

### 3. 에뮬레이터 또는 기기에 설치
```bash
./gradlew installDebug
```

## 주요 파일 설명

### MainActivity.kt
- 앱의 메인 액티비티
- 위젯 시작/중지 버튼
- 권한 요청 처리

### FloatingWidgetService.kt
- 오버레이 위젯 서비스
- WebView로 웹 콘텐츠 로드
- 터치 이벤트 처리 (드래그)
- 위젯 위치 관리

### WidgetReceiver.kt
- 부트 완료 이벤트 수신
- 앱 시작 시 자동으로 위젯 시작

## 웹 콘텐츠 로드
- 기존 웹 프로젝트의 URL을 로드
- 또는 로컬 HTML 파일 사용 가능

## 설정 파일

### build.gradle.kts (Project)
```kotlin
plugins {
    id("com.android.application") version "8.1.0" apply false
    kotlin("android") version "1.9.0" apply false
}
```

### build.gradle.kts (Module: app)
```kotlin
plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.example.timetablewidget"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.9.0")
}
```

## 사용 방법

1. **앱 설치**
   - Google Play Store에서 설치 (배포 후)
   - 또는 APK 직접 설치

2. **앱 실행**
   - 앱을 열고 "위젯 시작" 버튼 클릭

3. **권한 허용**
   - "다른 앱 위에 표시" 권한 허용

4. **위젯 사용**
   - 화면 어디서나 위젯 보임
   - 드래그하여 위치 변경 가능
   - 홈 버튼 눌러도 위젯 유지

## 주의사항
- 배터리 소비 증가 가능
- 일부 앱과 호환성 문제 가능
- 시스템 권한 필요

## 라이선스
MIT License
