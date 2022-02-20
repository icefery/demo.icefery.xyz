#include <QApplication>
#include <QDialog>
#include <QSlider>
#include <QSpinBox>

int main(int argc, char** argv) {
    QApplication app(argc, argv);

    QDialog parent;
    parent.resize(320, 240);

    //创建水平滑块
    QSlider slider(Qt::Horizontal, &parent);
    slider.move(20, 100);
    slider.setRange(0, 200);

    // 创建选值框
    QSpinBox spinBox(&parent);
    spinBox.move(220, 100);
    spinBox.setRange(0, 200);

    // 滑动滑块让选值框随之变化
    QObject::connect(&slider, SIGNAL(valueChanged(int)), &spinBox, SLOT(setValue(int)));
    // 选值框数值改变让滑块随之滑动
    QObject::connect(&spinBox, SIGNAL(valueChanged(int)), &slider, SLOT(setValue(int)));

    parent.show();

    return app.exec();
}