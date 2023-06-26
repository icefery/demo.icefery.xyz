#include <iostream>

[[deprecated("函数已过时")]] int f(int x) { return x; }

int main() {
    std::cout << f(1) << std::endl;
    return 0;
}
