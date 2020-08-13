FROM mongo

RUN mkdir -p /data/key \
  && openssl rand -base64 756 > /data/key/replica-set.key

RUN chown mongodb:mongodb /data/key/replica-set.key \
  && chmod 400 /data/key/replica-set.key

