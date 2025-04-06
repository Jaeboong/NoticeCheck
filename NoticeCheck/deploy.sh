#!/bin/bash

# 환경 변수 설정
export SPRING_PROFILES_ACTIVE=prod
export DB_USERNAME=notice123
export DB_PASSWORD=notice123

# 애플리케이션 중지
PID=$(pgrep -f noticecheck.jar)
if [ ! -z "$PID" ]; then
    echo "Stopping application..."
    kill $PID
    sleep 5
fi

# JAR 파일 백업
if [ -f noticecheck.jar ]; then
    echo "Backing up current version..."
    mv noticecheck.jar noticecheck.jar.backup
fi

# 새 버전 빌드
echo "Building new version..."
./gradlew clean bootJar

# 로그 디렉토리 생성
sudo mkdir -p /var/log/noticecheck
sudo chown $USER:$USER /var/log/noticecheck

# 애플리케이션 시작
echo "Starting application..."
nohup java -jar build/libs/noticecheck.jar > /var/log/noticecheck/console.log 2>&1 &

echo "Deployment completed!" 