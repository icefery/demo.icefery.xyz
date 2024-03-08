export ZSH="$HOME/.oh-my-zsh"

ZSH_THEME="ys"

plugins=(sudo)

source $ZSH/oh-my-zsh.sh

alias ll="ls -lh -A -G -D '%Y-%m-%d %H:%M:%S'"
