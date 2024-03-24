/********************************************************************************
** Form generated from reading UI file 'move_label_dialog.ui'
**
** Created by: Qt User Interface Compiler version 6.6.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MOVE_LABEL_DIALOG_H
#define UI_MOVE_LABEL_DIALOG_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QDialog>
#include <QtWidgets/QLabel>

QT_BEGIN_NAMESPACE

class Ui_MoveLabelDialog {
public:
    QLabel* label;

    void setupUi(QDialog* Dialog) {
        if (Dialog->objectName().isEmpty())
            Dialog->setObjectName("Dialog");
        Dialog->resize(600, 400);
        label = new QLabel(Dialog);
        label->setObjectName("label");
        label->setGeometry(QRect(0, 0, 50, 50));
        label->setStyleSheet(QString::fromUtf8("background-color:blue;"));

        retranslateUi(Dialog);

        QMetaObject::connectSlotsByName(Dialog);
    } // setupUi

    void retranslateUi(QDialog* Dialog) {
        Dialog->setWindowTitle(QCoreApplication::translate("MoveLabelDialog", "\347\247\273\345\212\250\346\226\271\345\235\227", nullptr));
        label->setText(QString());
    } // retranslateUi
};

namespace Ui {
    class MoveLabelDialog : public Ui_MoveLabelDialog {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MOVE_LABEL_DIALOG_H
