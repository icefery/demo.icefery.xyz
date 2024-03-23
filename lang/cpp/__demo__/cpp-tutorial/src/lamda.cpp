#include <iostream>

int main() {
    auto f1 = [](int x, int y) { return x + y; };
    std::cout << "f1=" << f1(1, 2) << std::endl;

    auto a = 1;
    auto b = 2;

    auto f2 = [a, &b]() { return a + b; }; // 按值捕获局部变量 a 按引用捕获局部变量 b
    auto f3 = [=]() { return a + b; };     // 按值捕获全部局部变量
    auto f4 = [&]() { return a + b; };     // 按引用捕获全部局部变量
    std::cout << "f2=" << f2() << "\t"
              << "f3=" << f3() << "\t"
              << "f4=" << f4() << std::endl;

    a = 2;
    b = 4;

    std::cout << "f2=" << f2() << "\t"
              << "f3=" << f3() << "\t"
              << "f4=" << f4() << std::endl;

    return 0;
}