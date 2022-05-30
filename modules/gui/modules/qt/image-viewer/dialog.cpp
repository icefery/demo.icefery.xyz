#include "dialog.h"
#include "ui_dialog.h"

Dialog::Dialog(QWidget* parent) : QDialog(parent), ui(new Ui::Dialog) {
    ui->setupUi(this);
    index = 0;
}

Dialog::~Dialog() {
    delete ui;
}

// 上一张按钮点击槽函数
void Dialog::on_prevButton_clicked() {
    if (--index < 0) {
        index = 3;
    }
    update();
}

// 下一张按钮点击槽函数
void Dialog::on_nextButton_clicked() {
    if (++index > 3) {
        index = 0;
    }
    update();
}

// 绘图事件
void Dialog::paintEvent(QPaintEvent*) {
    // 1. 创建画家对象
    QPainter painter(this);
    // 2. 获取绘图所在矩形区域
    QRect rect = ui->frame->frameRect();
    // 坐标值平移(让 rect 和 painter 使用相同的坐标系)
    rect.translate(ui->frame->pos());
    // 3. 构建要绘制的图像对象
    QImage image(":/image/" + QString::number(index) + ".jpg");
    // 4. 使用 painter 将 image 绘制到 rect
    painter.drawImage(rect, image);
}