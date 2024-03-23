#include "dialog.h"
#include "ui_dialog.h"

Dialog::Dialog(QWidget* parent) : QDialog(parent), ui(new Ui::Dialog) {
    ui->setupUi(this);
    index = 0;
    isStarted = false;
    loadImages("../../../image");
    qDebug() << "图片数量 = " << vector.size();
}

Dialog::~Dialog() {
    delete ui;
}

// 按钮点击槽函数
void Dialog::on_pushButton_clicked() {
    if (!isStarted) {
        // 摇奖开始
        isStarted = true;
        timerId = startTimer(50);
        ui->pushButton->setText("停止");
    } else {
        // 摇奖结束
        isStarted = false;
        killTimer(timerId);
        ui->pushButton->setText("开始");
    }
}

// 加载图片到容器
void Dialog::loadImages(const QString& path) {
    QDir dir(path);
    // 遍历当前目录所有图片
    QStringList list1 = dir.entryList(QDir::Files);
    for (int i = 0; i < list1.size(); i++) {
        QImage image(path + "/" + list1.at(i));
        vector << image;
    }
    // 递归遍历子目录中的图片
    QStringList list2 = dir.entryList(QDir::Dirs | QDir::NoDotAndDotDot);
    for (int i = 0; i < list2.size(); i++) {
        loadImages(path + "/" + list2.at(i));
    }
}

// 定时器事件
void Dialog::paintEvent(QPaintEvent*) {
    QPainter painter(this);
    QRect rect = ui->frame->frameRect();
    rect.translate(ui->frame->pos());
    painter.drawImage(rect, vector[index]);
}

// 绘图事件
void Dialog::timerEvent(QTimerEvent*) {
    index = QRandomGenerator::global()->generate() % vector.size();
    update();
}