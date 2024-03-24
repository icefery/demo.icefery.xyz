/********************************************************************************
** Form generated from reading UI file 'image_view_dialog.ui'
**
** Created by: Qt User Interface Compiler version 6.6.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_IMAGE_VIEW_DIALOG_H
#define UI_IMAGE_VIEW_DIALOG_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QDialog>
#include <QtWidgets/QFrame>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QVBoxLayout>

QT_BEGIN_NAMESPACE

class Ui_ImageViewDialog {
public:
    QVBoxLayout* verticalLayout;
    QHBoxLayout* horizontalLayout;
    QSpacerItem* horizontalSpacer;
    QFrame* frame;
    QSpacerItem* horizontalSpacer_2;
    QSpacerItem* verticalSpacer;
    QHBoxLayout* horizontalLayout_2;
    QSpacerItem* horizontalSpacer_3;
    QPushButton* prevButton;
    QPushButton* nextButton;
    QSpacerItem* horizontalSpacer_4;

    void setupUi(QDialog* Dialog) {
        if (Dialog->objectName().isEmpty())
            Dialog->setObjectName("Dialog");
        Dialog->resize(538, 355);
        QFont font;
        font.setPointSize(16);
        Dialog->setFont(font);
        verticalLayout = new QVBoxLayout(Dialog);
        verticalLayout->setObjectName("verticalLayout");
        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName("horizontalLayout");
        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Policy::Expanding, QSizePolicy::Policy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);

        frame = new QFrame(Dialog);
        frame->setObjectName("frame");
        frame->setMinimumSize(QSize(480, 270));
        frame->setMaximumSize(QSize(480, 270));
        frame->setFrameShape(QFrame::StyledPanel);
        frame->setFrameShadow(QFrame::Raised);

        horizontalLayout->addWidget(frame);

        horizontalSpacer_2 = new QSpacerItem(40, 20, QSizePolicy::Policy::Expanding, QSizePolicy::Policy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_2);

        verticalLayout->addLayout(horizontalLayout);

        verticalSpacer = new QSpacerItem(20, 0, QSizePolicy::Policy::Minimum, QSizePolicy::Policy::Expanding);

        verticalLayout->addItem(verticalSpacer);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2->setObjectName("horizontalLayout_2");
        horizontalSpacer_3 = new QSpacerItem(40, 20, QSizePolicy::Policy::Expanding, QSizePolicy::Policy::Minimum);

        horizontalLayout_2->addItem(horizontalSpacer_3);

        prevButton = new QPushButton(Dialog);
        prevButton->setObjectName("prevButton");

        horizontalLayout_2->addWidget(prevButton);

        nextButton = new QPushButton(Dialog);
        nextButton->setObjectName("nextButton");

        horizontalLayout_2->addWidget(nextButton);

        horizontalSpacer_4 = new QSpacerItem(40, 20, QSizePolicy::Policy::Expanding, QSizePolicy::Policy::Minimum);

        horizontalLayout_2->addItem(horizontalSpacer_4);

        verticalLayout->addLayout(horizontalLayout_2);

        retranslateUi(Dialog);

        QMetaObject::connectSlotsByName(Dialog);
    } // setupUi

    void retranslateUi(QDialog* Dialog) {
        Dialog->setWindowTitle(QCoreApplication::translate("ImageViewDialog", "Dialog", nullptr));
        prevButton->setText(QCoreApplication::translate("ImageViewDialog", "\344\270\212\344\270\200\345\274\240", nullptr));
        nextButton->setText(QCoreApplication::translate("ImageViewDialog", "\344\270\213\344\270\200\345\274\240", nullptr));
    } // retranslateUi
};

namespace Ui {
    class ImageViewDialog : public Ui_ImageViewDialog {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_IMAGE_VIEW_DIALOG_H
