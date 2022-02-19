#include "dialog.h"
#include "ui_dialog.h"

Dialog::Dialog(QWidget* parent) : QDialog(parent), ui(new Ui::Dialog) {
    ui->setupUi(this);
}

Dialog::~Dialog() {
    delete ui;
}

void Dialog::mousePressEvent(QMouseEvent* e) {
    if (e->button() == Qt::LeftButton) {
        // 后去 Label 所在矩形区域
        QRect rect = ui->label->frameRect();
        rect.translate(ui->label->pos());
        if (rect.contains(e->pos())) {
            draggable = true;
            point = ui->label->pos() - e->pos();
        }
    }
}

void Dialog::mouseReleaseEvent(QMouseEvent* e) {
    if (e->button() == Qt::LeftButton) {
        draggable = false;
    }
}

void Dialog::mouseMoveEvent(QMouseEvent* e) {
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

void Dialog::keyPressEvent(QKeyEvent* e) {
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