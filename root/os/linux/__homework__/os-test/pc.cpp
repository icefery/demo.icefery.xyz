#include <windows.h>

#include <iostream>

using namespace std;

// 生产者的个数 | 消费者的个数
const int PRODUCER_COUNT = 2;
const int CONSUMER_COUNT = 10;

// 缓冲区长度
const int BUFFER_SIZE = 10;

// 缓冲区是个循环队列
string buffer[BUFFER_SIZE];

// 产品号（产品编号从 P1 开始）
int product_id = 0;

// 待放置下标 | 待拿取下标
int in = 0;
int out = 0;

// 用于线程间的互斥 | 当缓冲区满时迫使生产者等待 | 当缓冲区空时迫使消费者等待
HANDLE mutex = CreateMutex(NULL, FALSE, NULL);
HANDLE nonfull = CreateSemaphore(NULL, BUFFER_SIZE - 1, BUFFER_SIZE - 1, NULL);
HANDLE nonempty = CreateSemaphore(NULL, 0, BUFFER_SIZE - 1, NULL);

// ======================================================================================================
// 打印当前缓冲区
// ======================================================================================================
void log_buffer() {
    cout << endl << "--------------------------------------------------------------------" << endl;
    for (int i = 0; i < BUFFER_SIZE; i++) {
        string status;
        if (i == in) {
            status = "等待生产者放置";
        } else if (i == out) {
            status = "等待消费者拿取";
        } else {
            status = "";
        }
        cout << "BUFFER[" << i << "]\t" << buffer[i] << "\t" << status << endl;
    }
    cout << "--------------------------------------------------------------------" << endl << endl;
}

// ======================================================================================================
// 生产者线程
// ======================================================================================================
DWORD WINAPI run_produce(LPVOID lpPara) {
    while (true) {
        // 1. Wait 资源信号量 nonfull
        WaitForSingleObject(nonfull, INFINITE);
        // 2. Wait 互斥信号量 mutex
        WaitForSingleObject(mutex, INFINITE);

        // 3. 生产产品
        product_id++;
        buffer[in] = "P" + to_string(product_id);
        std::cout << "生产 " << buffer[in] << " ...成功" << std::endl;
        // 4. 移动待放置下标 in
        in = (in + 1) % BUFFER_SIZE;

        log_buffer();
        Sleep(5000);

        // 5. Signal 互斥信号量 mutex
        ReleaseMutex(mutex);
        // 6. Signal 资源信号量 nonempty
        ReleaseSemaphore(nonempty, 1, NULL);
    }
    return 0;
}

// ======================================================================================================
// 消费者线程
// ======================================================================================================
DWORD WINAPI run_consume(LPVOID lpPara) {
    while (true) {
        // 1. Wait 资源信号量 nonempty
        WaitForSingleObject(nonempty, INFINITE);
        // 2. Wait 互斥信号量 mutex
        WaitForSingleObject(mutex, INFINITE);

        // 3. 消费产品
        std::cout << "消费 " << buffer[out] << " ...成功" << std::endl;
        buffer[out] = "空";
        // 4. 移动待拿取下标 out
        out = (out + 1) % BUFFER_SIZE;

        log_buffer();
        Sleep(250);

        // 5. Signal 互斥信号量
        ReleaseMutex(mutex);
        // 6. Signal 资源信号量
        ReleaseSemaphore(nonfull, 1, NULL);
    }
    return 0;
}

// ======================================================================================================
// Main 函数
// ======================================================================================================
int main() {
    // 初始化缓冲区
    for (int i = 0; i < BUFFER_SIZE; i++) {
        buffer[i] = "空";
    }

    // 线程 handle | 线程 ID
    HANDLE thread_handles[PRODUCER_COUNT + CONSUMER_COUNT];
    DWORD thread_ids[PRODUCER_COUNT + CONSUMER_COUNT];

    // 创建生产者线程
    for (int i = 0; i < PRODUCER_COUNT; i++) {
        thread_handles[i] = CreateThread(NULL, 0, run_produce, NULL, 0, &thread_ids[i]);
    }

    // 创建消费者线程
    for (int i = 0; i < CONSUMER_COUNT; i++) {
        thread_handles[PRODUCER_COUNT + i] =
            CreateThread(NULL, 0, run_consume, NULL, 0, &thread_ids[PRODUCER_COUNT + i]);
    }

    // 阻塞 Main 函数
    while (true) {
    }

    return 0;
}
