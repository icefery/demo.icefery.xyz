COMMAND_LIST=(
    format
    pre-commit
    help
)

function format_cpp() {
    if [[ -n $1 ]]; then
        clang-format -i $1
    else
        fd -H -e h -e cpp -X clang-format -i
    fi
}

function format_dos2unix() {
    if [[ -n $1 ]]; then
        dos2unix --remove-bom --safe $1
    else
        fd -H -t f -X dos2unix --remove-bom --safe
    fi
}

function format_git() {
    git ls-files -z '*.sh' | while IFS= read -r -d '' file; do
        chmod +x "$file"
        git update-index --chmod=+x "$file"
    done
}

function format_java() {
    if [[ -n $1 ]]; then
        prettier -w $1
    else
        fd -H -e java -X prettier -w
    fi
}

function format_js() {
    if [[ -n $1 ]]; then
        prettier -w $1
    else
        fd -H '\.(json|js|jsx|ts|tsx)$' -X prettier -w
    fi
}

function format_md() {
    if [[ -n $1 ]]; then
        prettier -w $1
    else
        fd -H -e md -e mdx -X prettier -w
    fi
}

function format_sh() {
    if [[ -n $1 ]]; then
        shfmt --write --simplify --indent 4 --space-redirects $1
    else
        fd -H -e sh -X shfmt --write --simplify --indent 4 --space-redirects
    fi
}

function format_toml() {
    if [[ -n $1 ]]; then
        taplo format $1
    else
        fd -H -e toml -X taplo format
    fi
}

function format_xml() {
    if [[ -n $1 ]]; then
        prettier -w $1
    else
        fd -H -e xml -X prettier -w
    fi
}

function format_yaml() {
    if [[ -n $1 ]]; then
        prettier -w $1
    else
        fd -H -e yaml -e yml -X prettier -w
    fi
}

function format() {
    # format_cpp
    format_dos2unix
    format_git
    format_java
    format_js
    format_md
    format_sh
    format_toml
    format_xml
    format_yaml
}

function pre_commit() {
    tee .git/hooks/pre-commit > /dev/null <<- "EOF"
#!/usr/bin/env bash
bash sh.sh format
EOF
    chmod +x .git/hooks/pre-commit
}

function help() {
    echo "USAGE: $0 <format | pre-commit | help>"
}

case $1 in
format)
    format
    ;;
pre-commit)
    pre_commit
    ;;
*)
    help
    ;;
esac
