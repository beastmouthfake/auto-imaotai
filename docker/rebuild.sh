# 需要执行 chmod 777 rebuild.sh 授予权限
docker rm -f auto-imaotai
docker rmi auto-imaotai
docker build -t auto-imaotai:latest --no-cache .
docker-compose up -d