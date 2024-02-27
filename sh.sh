COMMAND_LIST=(
    format
    pre-commit
    help
)

function _sh() {
    COMPREPLY=()
    local cur=${COMP_WORDS[COMP_CWORD]}
    local cmd=${COMP_WORDS[COMP_CWORD - 1]}
    case $cmd in
    './sh.sh')
        COMPREPLY=($(compgen -W "${COMMAND_LIST[*]}" -- "${cur}"))
        ;;
    esac
}

function format() {
    prettier --write .
    shfmt --write --simplify --indent=4 --binary-next-line --space-redirects .
    git ls-files -z '*.sh' | while IFS= read -r -d '' file; do
        chmod +x "$file"
        git update-index --chmod=+x "$file"
    done
    git ls-files --exclude-standard -z -o | xargs -0 dos2unix --remove-bom --safe
}

function pre_commit() {
    cat <<- 'EOF' | tee .git/hooks/pre-commit > /dev/null
#!/usr/bin/env bash
bash sh.sh format
EOF
    chmod +x .git/hooks/pre-commit
}

function help() {
    echo "USAGE: $0 <format | pre-commit | help>"
}

complete -F _sh './sh.sh'

case $1 in
'format')
    format
    ;;
'pre-commit')
    pre_commit
    ;;
*)
    help
    ;;
esac
