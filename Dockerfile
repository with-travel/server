FROM redis:7.2.4-alpine3.19

# Redis의 기본 포트인 6379를 열어줍니다.
EXPOSE 6379

# Redis 서버를 실행합니다.
CMD ["redis-server"]