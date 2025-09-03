export ZSH=~/.oh-my-zsh

ZSH_THEME="ys"
CASE_SENSITIVE=true
HIST_STAMPS="%Y-%m-%d %H:%M:%S"

fpath+=~/.zfunc
autoload -Uz compinit && compinit

plugins=(sudo)

source "${ZSH}/oh-my-zsh.sh"

[[ -f ~/.p10k.zsh ]] && source ~/.p10k.zsh

[[ -f /opt/env.sh ]] && source /opt/env.sh
