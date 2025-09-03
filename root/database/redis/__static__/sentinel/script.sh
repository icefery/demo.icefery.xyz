case $1 in
start)
    redis-server redis_6379.conf
    redis-server redis_6380.conf
    redis-server redis_6381.conf
    redis-sentinel sentinel_26379.conf
    redis-sentinel sentinel_26380.conf
    redis-sentinel sentinel_26381.conf
    ;;
stop)
    ps -ef | grep redis | grep -v grep | awk '{print $2}' | xargs kill -9
    ;;
*)
    echo "USAGE: $0 <start|stop>"
    ;;
esac
