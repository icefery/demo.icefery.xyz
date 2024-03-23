#ifndef DIALOG_H
#define DIALOG_H

#include <QDialog>
#include <QTimer>
#include <QTime>
#include <QPainter>
#include <QImage>
#include <QDir>
#include <QVector>
#include <QRandomGenerator>

QT_BEGIN_NAMESPACE
namespace Ui { class Dialog; }
QT_END_NAMESPACE

class Dialog : public QDialog {
Q_OBJECT
public:
    Dialog(QWidget* parent = nullptr);
    ~Dialog();
private slots:
    // 按钮点击槽函数
    void on_pushButton_clicked();
private:
    // 加载图片到容器
    void loadImages(const QString&);
    // 定时器事件
    void timerEvent(QTimerEvent*);
    // 绘图事件
    void paintEvent(QPaintEvent*);
private:
    Ui::Dialog* ui;
    QVector<QImage> vector; // 保存图片的容器
    int index;              // 当前图片在容器中的索引
    int timerId;            // 定时器
    bool isStarted;         // 标记摇奖机状态
};

#endif // DIALOG_H