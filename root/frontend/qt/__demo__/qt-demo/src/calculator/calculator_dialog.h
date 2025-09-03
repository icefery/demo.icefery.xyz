#ifndef CALCULATOR_DIALOG_H
#define CALCULATOR_DIALOG_H

#include <QDialog>
#include <QDoubleValidator>
#include <QHBoxLayout>
#include <QLabel>
#include <QLineEdit>
#include <QPushButton>

class CalculatorDialog : public QDialog {
    Q_OBJECT

public:
    // 构造函数
    CalculatorDialog() {
        // 1. 界面初始化
        this->setFixedSize(320, 240);
        this->setWindowTitle("计算器");
        // 左操作数
        this->editX = new QLineEdit(this);
        this->editX->setAlignment(Qt::AlignRight);             // 设置文本对齐
        this->editX->setValidator(new QDoubleValidator(this)); // 设置数字验证器
        // 右操作数
        this->editY = new QLineEdit(this);
        this->editY->setAlignment(Qt::AlignRight);
        this->editY->setValidator(new QDoubleValidator(this));
        // 显示结果
        this->editZ = new QLineEdit(this);
        this->editZ->setAlignment(Qt::AlignRight);
        this->editZ->setReadOnly(true); // 设置只读
        // "+"
        this->plusButton = new QLabel("+", this);
        // "="
        this->equalButton = new QPushButton("=", this);
        this->equalButton->setEnabled(false); // 设置禁用
        // 布局器(自动调整每个控件的大小和位置)(按水平方向以此将控件添加到布局容器中)
        this->layout = new QHBoxLayout(this);
        this->layout->addWidget(editX);
        this->layout->addWidget(plusButton);
        this->layout->addWidget(editY);
        this->layout->addWidget(equalButton);
        this->layout->addWidget(editZ);
        this->setLayout(this->layout);

        // 2. 信号和槽函数连接
        // 左右操作数文本改变时发送信号 textChanged()
        connect(editX, SIGNAL(textChanged(QString)), this, SLOT(enableButton()));
        connect(editY, SIGNAL(textChanged(QString)), this, SLOT(enableButton()));
        // 点击按钮发送信号 clicked()
        connect(equalButton, SIGNAL(clicked()), this, SLOT(calc()));
    }

private slots:
    // 启用等号按钮
    void enableButton() {
        bool xOk;
        bool yOk;
        editX->text().toDouble(&xOk);
        editY->text().toDouble(&yOk);
        // 当左右操作数都输入了有效数据，则使能等号按钮
        equalButton->setEnabled(xOk && yOk);
    }

    // 计算和显示结果
    void calc() {
        double result = editX->text().toDouble() + editY->text().toDouble();
        // 显示字符串形式结果
        QString str = QString::number(result);
        editZ->setText(str);
    }

private:
    QHBoxLayout* layout;      // 水平布局
    QLineEdit* editX;         // 左操作数
    QLineEdit* editY;         // 右操作数
    QLineEdit* editZ;         // 显示结果
    QLabel* plusButton;       // "+"
    QPushButton* equalButton; // "="
};

#endif // CALCULATOR_DIALOG_H