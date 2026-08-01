# 안드로이드 시간표 위젯 앱 - 설치 및 빌드 가이드

## 📋 사전 요구사항

### 1. 필수 소프트웨어 설치
- **Android Studio** (최신 버전)
  - https://developer.android.com/studio
  - JDK 11 이상 포함

- **Android SDK**
  - API Level 34 (Android 14)
  - 빌드 도구 34.0.0 이상

### 2. 시스템 요구사항
- Windows, macOS, 또는 Linux
- 최소 8GB RAM
- 10GB 이상의 디스크 공간

---

## 🚀 설치 단계

### Step 1: Android Studio 설정

1. Android Studio 실행
2. **File → Open** → `android_widget_app` 폴더 선택
3. Gradle 동기화 완료 대기 (자동으로 시작)

### Step 2: 에뮬레이터 또는 기기 준비

#### 옵션 A: 에뮬레이터 사용
```bash
# Android Studio에서:
1. Tools → Device Manager
2. Create Device 클릭
3. API Level 34 선택
4. 에뮬레이터 시작
```

#### 옵션 B: 실제 안드로이드 기기 사용
```bash
# 기기 설정:
1. 설정 → 개발자 옵션 → USB 디버깅 활성화
2. USB 케이블로 PC와 연결
3. 신뢰 허용
```

### Step 3: 앱 빌드 및 실행

#### 방법 1: Android Studio에서 실행
```
1. Run → Run 'app' 클릭
2. 또는 Shift + F10 (Windows) / Control + R (Mac)
```

#### 방법 2: 터미널에서 빌드
```bash
# 프로젝트 디렉토리로 이동
cd /path/to/android_widget_app

# Debug APK 빌드
./gradlew assembleDebug

# 기기에 설치
./gradlew installDebug

# 앱 실행
adb shell am start -n com.example.timetablewidget/.MainActivity
```

---

## 🔧 빌드 옵션

### Debug 빌드 (개발용)
```bash
./gradlew assembleDebug
# 결과: app/build/outputs/apk/debug/app-debug.apk
```

### Release 빌드 (배포용)
```bash
./gradlew assembleRelease
# 결과: app/build/outputs/apk/release/app-release.apk
```

### 클린 빌드
```bash
./gradlew clean build
```

---

## 📱 앱 사용 방법

### 1. 앱 실행
- 홈 화면에서 "시간표 위젯" 앱 실행

### 2. 권한 허용
- "위젯 시작" 버튼 클릭
- "다른 앱 위에 표시" 권한 허용 (설정 화면 자동 이동)

### 3. 위젯 시작
- 권한 허용 후 다시 앱으로 돌아가기
- "위젯 시작" 버튼 클릭
- 화면 위에 시간표 위젯 표시됨

### 4. 위젯 조작
- **드래그**: 위젯을 원하는 위치로 이동
- **중지**: 앱에서 "위젯 중지" 버튼 클릭

---

## 🔍 문제 해결

### 1. "다른 앱 위에 표시" 권한 없음
```
해결책:
1. 설정 → 앱 → 시간표 위젯
2. 권한 → 다른 앱 위에 표시 → 허용
```

### 2. 앱이 실행되지 않음
```
해결책:
1. Android Studio에서 Logcat 확인
2. ./gradlew clean build 실행
3. 에뮬레이터 재시작
```

### 3. WebView 콘텐츠 로드 안 됨
```
해결책:
1. 인터넷 권한 확인 (AndroidManifest.xml)
2. 기기의 인터넷 연결 확인
3. WebView 업데이트 (Google Play Store)
```

### 4. 위젯이 떠있지 않음
```
해결책:
1. 배터리 절약 모드 확인
2. 앱 강제 종료 확인
3. 기기 재부팅
```

---

## 📦 APK 배포

### Google Play Store 배포
1. Release APK 생성
2. Google Play Console 계정 생성
3. 앱 등록 및 APK 업로드
4. 심사 대기 (일반적으로 1-3시간)

### 직접 배포 (APK 파일)
```bash
# Release APK 생성
./gradlew assembleRelease

# APK 파일 위치
app/build/outputs/apk/release/app-release.apk

# 기기에 설치
adb install app/build/outputs/apk/release/app-release.apk
```

---

## 🛠️ 개발 팁

### 로그 확인
```bash
# Logcat 실시간 모니터링
adb logcat | grep TimetableWidget

# 또는 Android Studio의 Logcat 탭 사용
```

### 기기 재시작
```bash
adb reboot
```

### 앱 데이터 초기화
```bash
adb shell pm clear com.example.timetablewidget
```

### WebView 디버깅
```
1. Chrome 열기
2. chrome://inspect 접속
3. 기기의 WebView 선택
4. DevTools로 디버깅
```

---

## 📝 라이선스
MIT License

---

## 💬 지원
문제가 발생하면 로그를 확인하고 위의 문제 해결 섹션을 참고하세요.
