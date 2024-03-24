/********************************************************************************
** Form generated from reading UI file 'dialog.ui'
**
** Created by: Qt User Interface Compiler version 6.6.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_DIALOG_H
#define UI_DIALOG_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QDialog>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QVBoxLayout>

QT_BEGIN_NAMESPACE

class Ui_Dialog
{
public:
    QVBoxLayout *verticalLayout;
    QHBoxLayout *horizontalLayout;
    QSpacerItem *horizontalSpacer;
    QListWidget *listWidget;
    QSpacerItem *horizontalSpacer_2;
    QGridLayout *gridLayout;
    QLineEdit *hostEdit;
    QLineEdit *nicknameEdit;
    QLabel *nicknameLabel;
    QLabel *hostLabel;
    QLabel *portLabel;
    QLineEdit *portEdit;
    QPushButton *connectButton;
    QHBoxLayout *horizontalLayout_2;
    QLineEdit *messageEdit;
    QPushButton *sendButton;

    void setupUi(QDialog *Dialog)
    {
        if (Dialog->objectName().isEmpty())
            Dialog->setObjectName("Dialog");
        Dialog->resize(526, 491);
        QFont font;
        font.setFamilies({QString::fromUtf8("Inconsolata")});
        font.setPointSize(16);
        Dialog->setFont(font);
        verticalLayout = new QVBoxLayout(Dialog);
        verticalLayout->setObjectName("verticalLayout");
        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName("horizontalLayout");
        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Policy::Expanding, QSizePolicy::Policy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);

        listWidget = new QListWidget(Dialog);
        listWidget->setObjectName("listWidget");
        listWidget->setMinimumSize(QSize(480, 270));
        listWidget->setMaximumSize(QSize(480, 270));

        horizontalLayout->addWidget(listWidget);

        horizontalSpacer_2 = new QSpacerItem(40, 20, QSizePolicy::Policy::Expanding, QSizePolicy::Policy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_2);


        verticalLayout->addLayout(horizontalLayout);

        gridLayout = new QGridLayout();
        gridLayout->setObjectName("gridLayout");
        hostEdit = new QLineEdit(Dialog);
        hostEdit->setObjectName("hostEdit");

        gridLayout->addWidget(hostEdit, 0, 1, 1, 1);

        nicknameEdit = new QLineEdit(Dialog);
        nicknameEdit->setObjectName("nicknameEdit");

        gridLayout->addWidget(nicknameEdit, 2, 1, 1, 1);

        nicknameLabel = new QLabel(Dialog);
        nicknameLabel->setObjectName("nicknameLabel");

        gridLayout->addWidget(nicknameLabel, 2, 0, 1, 1);

        hostLabel = new QLabel(Dialog);
        hostLabel->setObjectName("hostLabel");

        gridLayout->addWidget(hostLabel, 0, 0, 1, 1);

        portLabel = new QLabel(Dialog);
        portLabel->setObjectName("portLabel");

        gridLayout->addWidget(portLabel, 1, 0, 1, 1);

        portEdit = new QLineEdit(Dialog);
        portEdit->setObjectName("portEdit");

        gridLayout->addWidget(portEdit, 1, 1, 1, 1);


        verticalLayout->addLayout(gridLayout);

        connectButton = new QPushButton(Dialog);
        connectButton->setObjectName("connectButton");

        verticalLayout->addWidget(connectButton);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2->setObjectName("horizontalLayout_2");
        messageEdit = new QLineEdit(Dialog);
        messageEdit->setObjectName("messageEdit");

        horizontalLayout_2->addWidget(messageEdit);

        sendButton = new QPushButton(Dialog);
        sendButton->setObjectName("sendButton");
        sendButton->setEnabled(false);

        horizontalLayout_2->addWidget(sendButton);


        verticalLayout->addLayout(horizontalLayout_2);


        retranslateUi(Dialog);

        QMetaObject::connectSlotsByName(Dialog);
    } // setupUi

    void retranslateUi(QDialog *Dialog)
    {
        Dialog->setWindowTitle(QCoreApplication::translate("Dialog", "\350\201\212\345\244\251\345\256\244\345\256\242\346\210\267\347\253\257", nullptr));
        hostEdit->setText(QCoreApplication::translate("Dialog", "127.0.0.1", nullptr));
        nicknameEdit->setText(QString());
        nicknameLabel->setText(QCoreApplication::translate("Dialog", "\350\201\212\345\244\251\345\256\244\346\230\265\347\247\260\357\274\232", nullptr));
        hostLabel->setText(QCoreApplication::translate("Dialog", "\346\234\215\345\212\241\345\231\250\345\234\260\345\235\200\357\274\232", nullptr));
        portLabel->setText(QCoreApplication::translate("Dialog", "\346\234\215\345\212\241\345\231\250\347\253\257\345\217\243\357\274\232", nullptr));
        portEdit->setText(QCoreApplication::translate("Dialog", "8080", nullptr));
        connectButton->setText(QCoreApplication::translate("Dialog", "\350\277\236\346\216\245\346\234\215\345\212\241\345\231\250", nullptr));
        sendButton->setText(QCoreApplication::translate("Dialog", "\345\217\221\351\200\201", nullptr));
    } // retranslateUi

};

namespace Ui {
    class Dialog: public Ui_Dialog {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_DIALOG_H
