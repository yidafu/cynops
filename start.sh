# https://grafana.com/docs/loki/latest/setup/install/docker/

docker run --name loki -d -v $(pwd):/mnt/config -p 3100:3100 grafana/loki:2.9.4 -config.file=/mnt/config/loki-config.yaml

docker run --name promtail -d -v $(pwd):/mnt/config -v /var/log:/var/log --link loki grafana/promtail:2.9.4 -config.file=/mnt/config/promtail-config.yaml
