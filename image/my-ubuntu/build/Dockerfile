FROM ubuntu:22.04
COPY docker-init.sh /docker-init.sh
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN bash /docker-init.sh
ENTRYPOINT ["/docker-entrypoint.sh"]