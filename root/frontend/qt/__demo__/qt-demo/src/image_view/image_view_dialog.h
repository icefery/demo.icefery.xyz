#ifndef IMAGE_VIEW_DIALOG_H
#define IMAGE_VIEW_DIALOG_H

#include <QDialog>
#include <QPainter>
#include <ui_image_view_dialog.h>

QT_BEGIN_NAMESPACE
namespace Ui {
    class ImageViewDialog;
}
QT_END_NAMESPACE

class ImageViewDialog : public QDialog {
    Q_OBJECT

public:
    ImageViewDialog() {
        this->ui = new Ui::ImageViewDialog();
        this->ui->setupUi(this);
        this->index = 0;
    }

private slots:
    // 上一张按钮点击槽函数
    void on_prevButton_clicked() {
        if (--index < 0) {
            index = 3;
        }
        update();
    }
    // 下一张按钮点击槽函数
    void on_nextButton_clicked() {
        if (++index > 3) {
            index = 0;
        }
        update();
    }

protected:
    // 绘图事件
    void paintEvent(QPaintEvent* e) override {
        // 1. 创建画家对象
        QPainter painter(this);
        // 2. 获取绘图所在矩形区域
        QRect rect = ui->frame->frameRect();
        // 坐标值平移(让 rect 和 painter 使用相同的坐标系)
        rect.translate(ui->frame->pos());
        // 3. 构建要绘制的图像对象
        QImage image(":/image/花木兰/" + QString::number(index) + ".jpg");
        // 4. 使用 painter 将 image 绘制到 rect
        painter.drawImage(rect, image);
    }

private:
    Ui::ImageViewDialog* ui;
    int index;
};

#endif // IMAGE_VIEW_DIALOG_H