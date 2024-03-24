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
#include <QtWidgets/QProgressBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QVBoxLayout>

QT_BEGIN_NAMESPACE

class Ui_Dialog
{
public:
    QVBoxLayout *verticalLayout_2;
    QSpacerItem *topVerticalSpacer;
    QHBoxLayout *horizontalLayout_2;
    QSpacerItem *leftHorizontalSpacer;
    QVBoxLayout *verticalLayout;
    QGridLayout *gridLayout;
    QLabel *hostLabel;
    QLineEdit *hostEdit;
    QLabel *portLabel;
    QLineEdit *portEdit;
    QLabel *usernameLabel;
    QLineEdit *usernameEdit;
    QLabel *passwordLabel;
    QLineEdit *passwordEdit;
    QLabel *remotePathLabel;
    QLineEdit *remotePathEdit;
    QLabel *localPathLabel;
    QLineEdit *localPathEdit;
    QHBoxLayout *horizontalLayout;
    QProgressBar *progressBar;
    QPushButton *clearButton;
    QPushButton *uploadButton;
    QPushButton *downloadButton;
    QSpacerItem *rightHorizontalSpacer;
    QSpacerItem *bottomVerticalSpacer;

    void setupUi(QDialog *Dialog)
    {
        if (Dialog->objectName().isEmpty())
            Dialog->setObjectName("Dialog");
        Dialog->resize(480, 320);
        QSizePolicy sizePolicy(QSizePolicy::Policy::Preferred, QSizePolicy::Policy::Preferred);
        sizePolicy.setHorizontalStretch(0);
        sizePolicy.setVerticalStretch(0);
        sizePolicy.setHeightForWidth(Dialog->sizePolicy().hasHeightForWidth());
        Dialog->setSizePolicy(sizePolicy);
        Dialog->setMinimumSize(QSize(480, 320));
        Dialog->setMaximumSize(QSize(480, 320));
        QFont font;
        font.setPointSize(14);
        Dialog->setFont(font);
        verticalLayout_2 = new QVBoxLayout(Dialog);
        verticalLayout_2->setObjectName("verticalLayout_2");
        topVerticalSpacer = new QSpacerItem(20, 16, QSizePolicy::Policy::Minimum, QSizePolicy::Policy::Expanding);

        verticalLayout_2->addItem(topVerticalSpacer);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2->setObjectName("horizontalLayout_2");
        leftHorizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Policy::Expanding, QSizePolicy::Policy::Minimum);

        horizontalLayout_2->addItem(leftHorizontalSpacer);

        verticalLayout = new QVBoxLayout();
        verticalLayout->setObjectName("verticalLayout");
        gridLayout = new QGridLayout();
        gridLayout->setObjectName("gridLayout");
        hostLabel = new QLabel(Dialog);
        hostLabel->setObjectName("hostLabel");
        hostLabel->setText(QString::fromUtf8("\344\270\273\346\234\272"));
        hostLabel->setAlignment(Qt::AlignRight|Qt::AlignTrailing|Qt::AlignVCenter);

        gridLayout->addWidget(hostLabel, 0, 0, 1, 1);

        hostEdit = new QLineEdit(Dialog);
        hostEdit->setObjectName("hostEdit");
        hostEdit->setMinimumSize(QSize(300, 0));
        hostEdit->setMaximumSize(QSize(16777215, 16777215));
        QFont font1;
        font1.setFamilies({QString::fromUtf8("Consolas")});
        font1.setPointSize(14);
        hostEdit->setFont(font1);
        hostEdit->setPlaceholderText(QString::fromUtf8(""));

        gridLayout->addWidget(hostEdit, 0, 1, 1, 1);

        portLabel = new QLabel(Dialog);
        portLabel->setObjectName("portLabel");
        portLabel->setText(QString::fromUtf8("\347\253\257\345\217\243"));
        portLabel->setAlignment(Qt::AlignRight|Qt::AlignTrailing|Qt::AlignVCenter);

        gridLayout->addWidget(portLabel, 1, 0, 1, 1);

        portEdit = new QLineEdit(Dialog);
        portEdit->setObjectName("portEdit");
        portEdit->setMinimumSize(QSize(300, 0));
        portEdit->setFont(font1);
        portEdit->setPlaceholderText(QString::fromUtf8(""));

        gridLayout->addWidget(portEdit, 1, 1, 1, 1);

        usernameLabel = new QLabel(Dialog);
        usernameLabel->setObjectName("usernameLabel");
        usernameLabel->setText(QString::fromUtf8("\347\224\250\346\210\267"));
        usernameLabel->setAlignment(Qt::AlignRight|Qt::AlignTrailing|Qt::AlignVCenter);

        gridLayout->addWidget(usernameLabel, 2, 0, 1, 1);

        usernameEdit = new QLineEdit(Dialog);
        usernameEdit->setObjectName("usernameEdit");
        usernameEdit->setMinimumSize(QSize(300, 0));
        usernameEdit->setFont(font1);
        usernameEdit->setPlaceholderText(QString::fromUtf8(""));

        gridLayout->addWidget(usernameEdit, 2, 1, 1, 1);

        passwordLabel = new QLabel(Dialog);
        passwordLabel->setObjectName("passwordLabel");
        passwordLabel->setText(QString::fromUtf8("\345\257\206\347\240\201"));
        passwordLabel->setAlignment(Qt::AlignRight|Qt::AlignTrailing|Qt::AlignVCenter);

        gridLayout->addWidget(passwordLabel, 3, 0, 1, 1);

        passwordEdit = new QLineEdit(Dialog);
        passwordEdit->setObjectName("passwordEdit");
        passwordEdit->setMinimumSize(QSize(300, 0));
        passwordEdit->setFont(font1);
        passwordEdit->setPlaceholderText(QString::fromUtf8(""));

        gridLayout->addWidget(passwordEdit, 3, 1, 1, 1);

        remotePathLabel = new QLabel(Dialog);
        remotePathLabel->setObjectName("remotePathLabel");
        remotePathLabel->setText(QString::fromUtf8("\350\277\234\347\250\213\350\267\257\345\276\204"));
        remotePathLabel->setAlignment(Qt::AlignRight|Qt::AlignTrailing|Qt::AlignVCenter);

        gridLayout->addWidget(remotePathLabel, 4, 0, 1, 1);

        remotePathEdit = new QLineEdit(Dialog);
        remotePathEdit->setObjectName("remotePathEdit");
        remotePathEdit->setMinimumSize(QSize(300, 0));
        remotePathEdit->setFont(font1);
        remotePathEdit->setPlaceholderText(QString::fromUtf8(""));

        gridLayout->addWidget(remotePathEdit, 4, 1, 1, 1);

        localPathLabel = new QLabel(Dialog);
        localPathLabel->setObjectName("localPathLabel");
        localPathLabel->setText(QString::fromUtf8("\346\234\254\345\234\260\350\267\257\345\276\204"));
        localPathLabel->setAlignment(Qt::AlignRight|Qt::AlignTrailing|Qt::AlignVCenter);

        gridLayout->addWidget(localPathLabel, 5, 0, 1, 1);

        localPathEdit = new QLineEdit(Dialog);
        localPathEdit->setObjectName("localPathEdit");
        localPathEdit->setMinimumSize(QSize(300, 0));
        localPathEdit->setFont(font1);
        localPathEdit->setPlaceholderText(QString::fromUtf8(""));

        gridLayout->addWidget(localPathEdit, 5, 1, 1, 1);


        verticalLayout->addLayout(gridLayout);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName("horizontalLayout");
        progressBar = new QProgressBar(Dialog);
        progressBar->setObjectName("progressBar");
        progressBar->setFont(font);
        progressBar->setValue(0);
        progressBar->setAlignment(Qt::AlignCenter);
        progressBar->setTextVisible(true);
        progressBar->setFormat(QString::fromUtf8("%p%"));

        horizontalLayout->addWidget(progressBar);

        clearButton = new QPushButton(Dialog);
        clearButton->setObjectName("clearButton");

        horizontalLayout->addWidget(clearButton);

        uploadButton = new QPushButton(Dialog);
        uploadButton->setObjectName("uploadButton");
        uploadButton->setText(QString::fromUtf8("\344\270\212\344\274\240"));

        horizontalLayout->addWidget(uploadButton);

        downloadButton = new QPushButton(Dialog);
        downloadButton->setObjectName("downloadButton");
        downloadButton->setText(QString::fromUtf8("\344\270\213\350\275\275"));

        horizontalLayout->addWidget(downloadButton);


        verticalLayout->addLayout(horizontalLayout);


        horizontalLayout_2->addLayout(verticalLayout);

        rightHorizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Policy::Expanding, QSizePolicy::Policy::Minimum);

        horizontalLayout_2->addItem(rightHorizontalSpacer);


        verticalLayout_2->addLayout(horizontalLayout_2);

        bottomVerticalSpacer = new QSpacerItem(20, 17, QSizePolicy::Policy::Minimum, QSizePolicy::Policy::Expanding);

        verticalLayout_2->addItem(bottomVerticalSpacer);


        retranslateUi(Dialog);

        QMetaObject::connectSlotsByName(Dialog);
    } // setupUi

    void retranslateUi(QDialog *Dialog)
    {
        Dialog->setWindowTitle(QCoreApplication::translate("Dialog", "Dialog", nullptr));
        clearButton->setText(QCoreApplication::translate("Dialog", "\346\270\205\347\251\272", nullptr));
    } // retranslateUi

};

namespace Ui {
    class Dialog: public Ui_Dialog {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_DIALOG_H
