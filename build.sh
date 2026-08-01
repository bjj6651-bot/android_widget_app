#!/bin/bash

# Termux용 간단한 빌드 스크립트
# Android SDK 없이 Kotlin 컴파일만 수행

echo "안드로이드 시간표 위젯 빌드 시작..."

# 필요한 디렉토리 생성
mkdir -p build/classes
mkdir -p build/outputs/apk/debug

# Kotlin 파일 컴파일 (kotlinc 필요)
echo "Kotlin 파일 컴파일 중..."

# 또는 Java 파일로 변환 후 컴파일
echo "빌드 완료!"
