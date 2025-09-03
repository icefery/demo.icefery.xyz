## 收藏

#### [Homebrew：Mac os 使用 brew 工具时报错 No remote 'origin'](https://blog.csdn.net/Jo_Francis/article/details/124746363)

```shell
brew update

brew doctor

rm -rf "/opt/homebrew/Library/Taps/homebrew/homebrew-cask"
brew tap homebrew/cask

rm -rf "/opt/homebrew/Library/Taps/homebrew/homebrew-core"
brew tap homebrew/core

rm -rf "/opt/homebrew/Library/Taps/homebrew/homebrew-services"
brew tap homebrew/services
```
