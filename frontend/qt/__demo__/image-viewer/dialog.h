#ifndef DIALOG_H
#define DIALOG_H

#include <QDialog>
#include <QPainter>

QT_BEGIN_NAMESPACE
namespace Ui { class Dialog; }
QT_END_NAMESPACE

class Dialog : public QDialog {
Q_OBJECT
public:
    Dialog(QWidget* parent = nullptr);
    ~Dialog();
private slots:
    // 上一张按钮点击槽函数
    void on_prevButton_clicked();
    // 下一张按钮点击槽函数
    void on_nextButton_clicked();
private:
    // 绘图事件
    void paintEvent(QPaintEvent*);
private:
    Ui::Dialog* ui;
    int index;
};

#endif // DIALOG_H