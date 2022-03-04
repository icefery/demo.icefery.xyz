#include <cstdio>
#include <iostream>

using namespace std;

// 文件索引
struct Index {
    int logical_no[32];
    int physical_no[32];
    string state[32];
};

// 文件目录
struct Catalog {
    string filename[32];
    int filesize[32];
    Index* index[32];
};

// 存放输出的文件
FILE* output_file = NULL;

// 空闲空间 | 表示块的位图
int free_size = 32;
int bitmap[4][8];

// 文件目录
Catalog* catalog = new Catalog();

// 已分配的文件数量
int allocated_file_count = 0;


// =======================================================================================================
// 为文件分配空间
// =======================================================================================================
void alloc(string& filename, int filesize) {
    if (filesize < free_size) {
        // 1. 文件名 | 文件大小 | 初始化文件索引
        catalog->filename[allocated_file_count] = filename;
        catalog->filesize[allocated_file_count] = filesize;
        catalog->index[allocated_file_count] = new Index();
        for (int i = 0; i < 32; i++) {
            catalog->index[allocated_file_count]->logical_no[i] = i;
            catalog->index[allocated_file_count]->physical_no[i] = 0;
            catalog->index[allocated_file_count]->state[i] = "未分配";
        }

        // 2. 分配空间
        int k = 0;
        for (int i = 0; i < 4 && k < filesize; i++) {
            for (int j = 0; j < 8 && k < filesize; j++) {
                if (bitmap[i][j] == 0) {
                    catalog->index[allocated_file_count]->physical_no[k] = i * 8 + j;
                    catalog->index[allocated_file_count]->state[k] = "已分配";
                    k++;
                    bitmap[i][j] = 1;
                }
            }
        }

        // 3. 分配完成: 空闲空间响应减少、已分配的文件数量对应增加
        free_size -= filesize;
        allocated_file_count++;

        // 4. 记录已分配文件状态
        fprintf(output_file, "----------------------------------------------------------------------------------------\n");
        fprintf(output_file, "文 件 名\t文件大小\t索引地址\t逻辑块号\t物理块号\t分配状态 \n");
        fprintf(output_file, "----------------------------------------------------------------------------------------\n");
        for (int i = 0; i < allocated_file_count; i++) {
            fprintf(output_file, "%-10s\t%-10d\t%-10x \n", catalog->filename[i].c_str(), catalog->filesize[i], catalog->index[i]);
            for (int j = 0; j < catalog->filesize[i]; j++) {
                fprintf(
                    output_file,
                    "%-42s\t%-10d\t%-10d\t%-10s \n",
                    " ", catalog->index[i]->logical_no[j], catalog->index[i]->physical_no[j], catalog->index[i]->state[j].c_str()
                );
            }
            fprintf(output_file, "----------------------------------------------------------------------------------------\n");
        }

        // 5. 记录块的状态
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                fprintf(output_file, "%d ", bitmap[i][j]);
            }
            fprintf(output_file, "\n");
        }

        fprintf(output_file, "\n\n");
        cout << "为 " << filename << " 分配空间成功" << endl << endl;
    } else {
        fprintf(output_file, "没有空闲空间为 %s 分配 \n\n\n", filename.c_str());
        cout << "没有空闲内存为 " << filename << " 分配! " << endl << endl;
    }
}


// =======================================================================================================
// Main 函数
// =======================================================================================================
int main() {
    // 1. 以 Write 模式打开文件
    output_file = fopen("d:/output_file.txt", "w");

    string filename;
    int filesize = 0;
    char loop = 'Y';
    while (loop == 'Y' && allocated_file_count < 32) {
        // 2. 输入文件信息
        cout << "请输入文件名: ";
        cin >> filename;
        cout << "请输入文件大小: ";
        cin >> filesize;
        cout << endl;

        // 3. 记录输入的文件信息
        fprintf(output_file, "filename=%s filesize=%d \n", filename.c_str(), filesize);

        // 4. 分配空间
        alloc(filename, filesize);

        cout << "是否继续输入（Y/N）: ";
        cin >> loop;
    }

    // 5. 关闭文件
    fclose(output_file);
    return 0;
}