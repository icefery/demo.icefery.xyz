URL="https://sub2.smallstrawberry.com/api/v1/client/subscribe?token=86da33704eb745eb4f5421664f82eca0"

function update() {
    curl "http://127.0.0.1:25500/sub?target=clash" -G -L --data-urlencode "url=${URL}" --output /opt/module/clash/config.yaml
}

function test() {
    http_proxy="http://127.0.0.1:7890" https_proxy="${http_proxy}" curl -i google.com
}

case $1 in
update)
    update
    ;;
test)
    test
    ;;
*)
    echo "USAGE: $0 <update | test>"
    ;;
esac
