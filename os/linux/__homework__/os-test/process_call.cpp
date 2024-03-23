#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

// Process Control Block
struct PCB {
    // 进程名
    char name[10];
    // 进程优先级（数值越低, 优先级越高）
    int priority;
    // 进程状态
    const char* state;
    // 进程需要的时间
    int needed_time;
    // 进程运行时间
    int runtime;
    // 进程链表后继指针
    PCB* next;
};

// 就绪队列链表第一个进程 | 当前进程
PCB* first = NULL;
PCB* current = NULL;

// ======================================================================================================
// 按优先级对进程排序（数值越低, 优先级越高）
// ======================================================================================================
void sort() {
    if (first == NULL || current->priority < first->priority) {
        // 链表为空 || 当前进程优先级 > 第一个进程优先级
        current->next = first;
        first = current;
    } else {
        PCB* pi = first;
        PCB* pj = pi->next;
        bool lowest = true;
        while (pj != NULL) {
            if (current->priority < pj->priority) {
                current->next = pj;
                pi->next = current;
                pj = NULL;
                lowest = false;
            } else {
                pi = pi->next;
                pj = pj->next;
            }
        }
        // 当前进程优先级 < 表中所有进程优先级
        if (lowest) {
            pi->next = current;
        }
    }
}

// ======================================================================================================
// 输入进程信息
// ======================================================================================================
void input_process() {
    int num;
    cout << "请输入进程数量: ";
    cin >> num;

    for (int i = 0; i < num; i++) {
        current = (PCB*)malloc(sizeof(PCB));

        // 进程名
        cout << "process[" << to_string(i) << "] name = ";
        cin >> current->name;

        // 进程优先级
        cout << "process[" << to_string(i) << "] priority = ";
        cin >> current->priority;

        // 进程需要的时间
        cout << "process[" << to_string(i) << "] needed_time = ";
        cin >> current->needed_time;

        cout << endl;

        // 进程运行时间 | 进程状态 | 进程后继指针
        current->runtime = 0;
        current->state = "READY";
        current->next = NULL;

        // 排序
        sort();
    }
}

// ======================================================================================================
// 计算进程个数
// ======================================================================================================
int get_process_count() {
    int count = 0;
    PCB* pi = first;
    while (pi != NULL) {
        count++;
        pi = pi->next;
    }
    return count;
}

// ======================================================================================================
// 打印进程
// ======================================================================================================
void log_process() {
    cout << "------------------------------------------------------------------------------------" << endl;
    printf("%-10s \t %-10s \t %-10s \t %-10s \t %-10s \n", "进程名", "进程状态", "优先级", "需要的时间", "运行时间");
    printf("%-10s \t %-10s \t %-10d \t %-10d \t %-10d \n", current->name, current->state, current->priority, current->needed_time, current->runtime);
    PCB* pi = first;
    while (pi != NULL) {
        printf("%-10s \t %-10s \t %-10d \t %-10d \t %-10d \n", pi->name, pi->state, pi->priority, pi->needed_time,  pi->runtime);
        pi = pi->next;
    }
    cout << "------------------------------------------------------------------------------------" << endl;
}

// ======================================================================================================
// 当前进程运行时间推进
// ======================================================================================================
void run_process() {
    log_process();
    // 当前进程运行（运行时间 +1）
    current->runtime++;
    if (current->runtime == current->needed_time) {
        // 进程已完成
        cout << current->name << " 时间片结束 ==> 已完成" << endl;
        free(current);
    } else {
        // 进程未完成
        // 1. 置就绪状态 | 降低优先级
        current->priority++;
        current->state = "READY";
        // 2. 重新排序
        sort();
        cout << current->name << " 时间片结束 ==> 置就绪状态" << endl;
    }
}

// ======================================================================================================
// Main 函数
// ======================================================================================================
int main() {
    // 输入进程 | 计算链表中进程个数
    input_process();
    int len = get_process_count();

    // 刷出缓冲区的字符, 避免自动读取
    getchar();

    if (len != 0) {
        while (first != NULL) {
            // 当前进程指向第一个进程并置为运行状态
            current = first;
            current->state = "RUNNING";

            cout << current->name << " 优先级最高 ==> 置运行状态" << endl;

            // 移动 first 指针
            first = current->next;
            current->next = NULL;

            // 轮转时间片
            run_process();

            cout << endl << "..." << endl << endl;
            getchar();
        }
    }

    return 0;
}
