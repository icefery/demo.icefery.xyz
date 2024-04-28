export ZSH=~/.oh-my-zsh

ZSH_THEME="ys"
CASE_SENSITIVE=true
HIST_STAMPS="%Y-%m-%d %H:%M:%S"

fpath+=~/.zfunc
plugins=(sudo)

source "${ZSH}/oh-my-zsh.sh"

[[ -f ~/env.sh ]] && source ~/env.sh
