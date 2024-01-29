URL="https://sub2.smallstrawberry.com/api/v1/client/subscribe?token=86da33704eb745eb4f5421664f82eca0"

function update_config() {
    curl -fsSL "http://127.0.0.1:25500/sub?target=clash" -G --data-urlencode "url=${URL}" --output /data/compose/config.yaml
}

case $1 in
update-config)
    update_config
    ;;
*)
    echo "USAGE: $0 <update-config>"
    ;;
esac
