#ifndef MOVE_LABEL_DIALOG_H
#define MOVE_LABEL_DIALOG_H

#include <QDialog>
#include <QMouseEvent>
#include <ui_move_label_dialog.h>

QT_BEGIN_NAMESPACE
namespace Ui {
    class MoveLabelDialog;
}
QT_END_NAMESPACE

class MoveLabelDialog : public QDialog {
    Q_OBJECT

public:
    MoveLabelDialog() {
        this->ui = new Ui::MoveLabelDialog();
        this->ui->setupUi(this);
    }

protected:
    void mousePressEvent(QMouseEvent* e) {
        if (e->button() == Qt::LeftButton) {
            // 获取 Label 所在矩形区域
            QRect rect = ui->label->frameRect();
            rect.translate(ui->label->pos());
            if (rect.contains(e->pos())) {
                draggable = true;
                point = ui->label->pos() - e->pos();
            }
        }
    }

    void mouseReleaseEvent(QMouseEvent* e) {
        if (e->button() == Qt::LeftButton) {
            draggable = false;
        }
    }

    void mouseMoveEvent(QMouseEvent* e) {
        if (draggable) {
            // 计算 Label 要移动的位置
            QPoint newPoint = e->pos() + point;
            QSize parentSize = size();
            QSize labelSize = ui->label->size();
            // x 范围 0 ~ parentWidht-labelWidth
            if (newPoint.x() < 0) {
                newPoint.setX(0);
            } else if (newPoint.x() > parentSize.width() - labelSize.width()) {
                newPoint.setX(parentSize.width() - labelSize.width());
            }
            // y 范围 0 ~ parentHeight-labelHeight
            if (newPoint.y() < 0) {
                newPoint.setY(0);
            } else if (newPoint.y() > parentSize.height() - labelSize.height()) {
                newPoint.setY(parentSize.height() - labelSize.height());
            }
            // 移动 Label 到新位置
            ui->label->move(newPoint);
        }
    }

    void keyPressEvent(QKeyEvent* e) {
        int x = ui->label->pos().x();
        int y = ui->label->pos().y();
        int step = 10;
        if (e->key() == Qt::Key_Up) {
            ui->label->move(x, y - step);
        } else if (e->key() == Qt::Key_Down) {
            ui->label->move(x, y + step);
        } else if (e->key() == Qt::Key_Left) {
            ui->label->move(x - step, y);
        } else {
            ui->label->move(x + step, y);
        }
    }

private:
    Ui::MoveLabelDialog* ui{};
    bool draggable{};
    QPoint point{};
};

#endif // MOVE_LABEL_DIALOG_H