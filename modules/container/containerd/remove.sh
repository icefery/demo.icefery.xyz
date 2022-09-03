LIST=(
  bin/buildctl
  bin/buildg
  bin/buildg.sh
  bin/buildkitd
  bin/bypass4netns
  bin/bypass4netnsd
  bin/containerd
  bin/containerd-fuse-overlayfs-grpc
  bin/containerd-rootless-setuptool.sh
  bin/containerd-rootless.sh
  bin/containerd-shim-runc-v2
  bin/containerd-stargz-grpc
  bin/ctd-decoder
  bin/ctr
  bin/ctr-enc
  bin/ctr-remote
  bin/fuse-overlayfs
  bin/ipfs
  bin/nerdctl
  bin/rootlessctl
  bin/rootlesskit
  bin/runc
  bin/slirp4netns
  bin/tini

  lib/systemd/system/buildkit.service
  lib/systemd/system/containerd.service
  lib/systemd/system/stargz-snapshotter.service

  libexec/cni/

  share/doc/nerdctl/

  share/doc/nerdctl-full/
)


for item in ${LIST[@]}; do
  rm -rf "/usr/local/${item}"
done