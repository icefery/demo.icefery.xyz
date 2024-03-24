#ifndef ERNIE_DIALOG_H
#define ERNIE_DIALOG_H

#include <QDialog>
#include <QDir>
#include <QImage>
#include <QPainter>
#include <QRandomGenerator>
#include <QTimer>
#include <QVector>
#include <complex>
#include <ui_ernie_dialog.h>

QT_BEGIN_NAMESPACE
namespace Ui {
    class ErnieDialog;
}
QT_END_NAMESPACE

class ErnieDialog : public QDialog {
    Q_OBJECT
public:
    ErnieDialog() {
        this->ui = new Ui::ErnieDialog();
        this->ui->setupUi(this);
        this->index = 0;
        this->timerId = 0;
        this->isStarted = false;
        this->loadImages(":/image/");
    }
private slots:
    // 按钮点击槽函数
    void on_pushButton_clicked() {
        if (!isStarted) {
            // 摇奖开始
            isStarted = true;
            timerId = startTimer(500);
            ui->pushButton->setText("停止");
        } else {
            // 摇奖结束
            isStarted = false;
            killTimer(timerId);
            ui->pushButton->setText("开始");
        }
    }

private:
    // 加载图片到容器
    void loadImages(const QString& path) {
        QDir dir(path);
        // 遍历当前目录所有图片
        QStringList list1 = dir.entryList(QDir::Files);
        for (int i = 0; i < list1.size(); i++) {
            qDebug() << path + "/" + list1.at(i);
            QImage image(path + "/" + list1.at(i));
            vector << image;
        }
        // // 递归遍历子目录中的图片
        QStringList list2 = dir.entryList(QDir::Dirs | QDir::NoDotAndDotDot);
        for (int i = 0; i < list2.size(); i++) {
            loadImages(path + "/" + list2.at(i));
        }
    }

    // 绘图事件
    void paintEvent(QPaintEvent* e) {
        QPainter painter(this);
        QRect rect = ui->frame->frameRect();
        rect.translate(ui->frame->pos());
        painter.drawImage(rect, vector[index]);
    }

    // 定时器事件
    void timerEvent(QTimerEvent* e) {
        index = QRandomGenerator::global()->bounded(0, vector.size());
        update();
    }

private:
    Ui::ErnieDialog* ui;
    QVector<QImage> vector; // 保存图片的容器
    int index;              // 当前图片在容器中的索引
    int timerId;            // 定时器
    bool isStarted;         // 标记摇奖机状态
};

#endif // ERNIE_DIALOG_H