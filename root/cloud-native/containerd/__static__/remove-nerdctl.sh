LIST=(
    bin/
    bin/buildctl
    bin/buildg
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
    lib/
    lib/systemd/
    lib/systemd/system/
    lib/systemd/system/buildkit.service
    lib/systemd/system/containerd.service
    lib/systemd/system/stargz-snapshotter.service
    libexec/
    libexec/cni/
    libexec/cni/bandwidth
    libexec/cni/bridge
    libexec/cni/dhcp
    libexec/cni/dummy
    libexec/cni/firewall
    libexec/cni/host-device
    libexec/cni/host-local
    libexec/cni/ipvlan
    libexec/cni/loopback
    libexec/cni/macvlan
    libexec/cni/portmap
    libexec/cni/ptp
    libexec/cni/sbr
    libexec/cni/static
    libexec/cni/tap
    libexec/cni/tuning
    libexec/cni/vlan
    libexec/cni/vrf
    share/
    share/doc/
    share/doc/nerdctl/
    share/doc/nerdctl/README.md
    share/doc/nerdctl/docs/
    share/doc/nerdctl/docs/build.md
    share/doc/nerdctl/docs/builder-debug.md
    share/doc/nerdctl/docs/cni.md
    share/doc/nerdctl/docs/command-reference.md
    share/doc/nerdctl/docs/compose.md
    share/doc/nerdctl/docs/config.md
    share/doc/nerdctl/docs/cosign.md
    share/doc/nerdctl/docs/dir.md
    share/doc/nerdctl/docs/experimental.md
    share/doc/nerdctl/docs/faq.md
    share/doc/nerdctl/docs/freebsd.md
    share/doc/nerdctl/docs/gpu.md
    share/doc/nerdctl/docs/ipfs.md
    share/doc/nerdctl/docs/multi-platform.md
    share/doc/nerdctl/docs/notation.md
    share/doc/nerdctl/docs/nydus.md
    share/doc/nerdctl/docs/ocicrypt.md
    share/doc/nerdctl/docs/overlaybd.md
    share/doc/nerdctl/docs/registry.md
    share/doc/nerdctl/docs/rootless.md
    share/doc/nerdctl/docs/soci.md
    share/doc/nerdctl/docs/stargz.md
    share/doc/nerdctl-full/
    share/doc/nerdctl-full/README.md
    share/doc/nerdctl-full/SHA256SUMS
)

for f in "${LIST[@]}"; do
    f="/usr/local/$f"
    if [[ -f $f ]]; then
        echo "$f is file"
        rm "$f"
    fi
done

for f in "${LIST[@]}"; do
    f="/usr/local/$f"
    if [[ -d $f && $(ls -A "$f") == "" ]]; then
        echo "$f is empty directory"
        rmdir "$f"
    fi
done
