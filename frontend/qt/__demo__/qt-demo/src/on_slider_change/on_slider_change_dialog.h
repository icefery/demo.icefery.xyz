#ifndef ON_SLIDER_CHANGE_DIALOG_H
#define ON_SLIDER_CHANGE_DIALOG_H

#include <QDialog>
#include <QHBoxLayout>
#include <QSlider>
#include <QSpinBox>

class OnSliderChangeDialog : public QDialog {
    Q_OBJECT

public:
    OnSliderChangeDialog() {
        // 调整窗体大小
        this->setWindowTitle("滑块联动");
        this->setFixedSize(320, 240);

        // 创建水平布局管理器
        this->layout = new QHBoxLayout(this);

        // 创建水平滑块
        slider = new QSlider(Qt::Horizontal, this);
        slider->setRange(0, 200);
        layout->addWidget(slider);

        // 创建选值框
        spinBox = new QSpinBox(this);
        spinBox->setRange(0, 200);
        layout->addWidget(spinBox);

        // 滑动滑块让选值框随之变化
        QObject::connect(slider, SIGNAL(valueChanged(int)), spinBox, SLOT(setValue(int)));
        // 选值框数值改变让滑块随之滑动
        QObject::connect(spinBox, SIGNAL(valueChanged(int)), slider, SLOT(setValue(int)));
    }

private:
    QHBoxLayout* layout;
    QSlider* slider;
    QSpinBox* spinBox;
};

#endif // ON_SLIDER_CHANGE_DIALOG_H