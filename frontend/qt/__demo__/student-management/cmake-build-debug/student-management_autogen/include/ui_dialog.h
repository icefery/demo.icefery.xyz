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
#include <QtWidgets/QComboBox>
#include <QtWidgets/QDialog>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTableView>
#include <QtWidgets/QVBoxLayout>

QT_BEGIN_NAMESPACE

class Ui_Dialog {
public:
    QVBoxLayout* verticalLayout;
    QHBoxLayout* horizontalLayout;
    QComboBox* sortColumnComboBox;
    QComboBox* sortTypeComboBox;
    QPushButton* sortButton;
    QTableView* tableView;
    QGridLayout* gridLayout;
    QLabel* idLabel;
    QLineEdit* idEdit;
    QLabel* nameLabel;
    QLineEdit* nameEdit;
    QLabel* scoreLabel;
    QLineEdit* scoreEdit;
    QHBoxLayout* horizontalLayout_2;
    QPushButton* insertButton;
    QPushButton* deleteButton;
    QPushButton* updateButton;

    void setupUi(QDialog* Dialog) {
        if (Dialog->objectName().isEmpty())
            Dialog->setObjectName("Dialog");
        Dialog->resize(600, 400);
        QFont font;
        font.setFamilies({QString::fromUtf8("Inconsolata")});
        font.setPointSize(16);
        Dialog->setFont(font);
        verticalLayout = new QVBoxLayout(Dialog);
        verticalLayout->setObjectName("verticalLayout");
        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName("horizontalLayout");
        sortColumnComboBox = new QComboBox(Dialog);
        sortColumnComboBox->addItem(QString());
        sortColumnComboBox->addItem(QString());
        sortColumnComboBox->setObjectName("sortColumnComboBox");

        horizontalLayout->addWidget(sortColumnComboBox);

        sortTypeComboBox = new QComboBox(Dialog);
        sortTypeComboBox->addItem(QString());
        sortTypeComboBox->addItem(QString());
        sortTypeComboBox->setObjectName("sortTypeComboBox");

        horizontalLayout->addWidget(sortTypeComboBox);

        sortButton = new QPushButton(Dialog);
        sortButton->setObjectName("sortButton");

        horizontalLayout->addWidget(sortButton);

        verticalLayout->addLayout(horizontalLayout);

        tableView = new QTableView(Dialog);
        tableView->setObjectName("tableView");

        verticalLayout->addWidget(tableView);

        gridLayout = new QGridLayout();
        gridLayout->setObjectName("gridLayout");
        idLabel = new QLabel(Dialog);
        idLabel->setObjectName("idLabel");

        gridLayout->addWidget(idLabel, 0, 0, 1, 1);

        idEdit = new QLineEdit(Dialog);
        idEdit->setObjectName("idEdit");

        gridLayout->addWidget(idEdit, 0, 1, 1, 1);

        nameLabel = new QLabel(Dialog);
        nameLabel->setObjectName("nameLabel");

        gridLayout->addWidget(nameLabel, 1, 0, 1, 1);

        nameEdit = new QLineEdit(Dialog);
        nameEdit->setObjectName("nameEdit");

        gridLayout->addWidget(nameEdit, 1, 1, 1, 1);

        scoreLabel = new QLabel(Dialog);
        scoreLabel->setObjectName("scoreLabel");

        gridLayout->addWidget(scoreLabel, 2, 0, 1, 1);

        scoreEdit = new QLineEdit(Dialog);
        scoreEdit->setObjectName("scoreEdit");

        gridLayout->addWidget(scoreEdit, 2, 1, 1, 1);

        verticalLayout->addLayout(gridLayout);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2->setObjectName("horizontalLayout_2");
        insertButton = new QPushButton(Dialog);
        insertButton->setObjectName("insertButton");

        horizontalLayout_2->addWidget(insertButton);

        deleteButton = new QPushButton(Dialog);
        deleteButton->setObjectName("deleteButton");

        horizontalLayout_2->addWidget(deleteButton);

        updateButton = new QPushButton(Dialog);
        updateButton->setObjectName("updateButton");
        updateButton->setFont(font);

        horizontalLayout_2->addWidget(updateButton);

        verticalLayout->addLayout(horizontalLayout_2);

        retranslateUi(Dialog);

        QMetaObject::connectSlotsByName(Dialog);
    } // setupUi

    void retranslateUi(QDialog* Dialog) {
        Dialog->setWindowTitle(
            QCoreApplication::translate("Dialog", "\345\255\246\347\224\237\346\210\220\347\273\251\347\256\241\347\220\206\347\263\273\347\273\237", nullptr));
        sortColumnComboBox->setItemText(0, QCoreApplication::translate("Dialog", "id", nullptr));
        sortColumnComboBox->setItemText(1, QCoreApplication::translate("Dialog", "score", nullptr));

        sortTypeComboBox->setItemText(0, QCoreApplication::translate("Dialog", "ASC", nullptr));
        sortTypeComboBox->setItemText(1, QCoreApplication::translate("Dialog", "DESC", nullptr));

        sortButton->setText(QCoreApplication::translate("Dialog", "\346\216\222\345\272\217", nullptr));
        idLabel->setText(QCoreApplication::translate("Dialog", "\345\255\246\347\224\237\345\255\246\345\217\267\357\274\232", nullptr));
        nameLabel->setText(QCoreApplication::translate("Dialog", "\345\255\246\347\224\237\345\247\223\345\220\215\357\274\232", nullptr));
        scoreLabel->setText(QCoreApplication::translate("Dialog", "\345\255\246\347\224\237\346\210\220\347\273\251\357\274\232", nullptr));
        insertButton->setText(QCoreApplication::translate("Dialog", "\346\217\222\345\205\245", nullptr));
        deleteButton->setText(QCoreApplication::translate("Dialog", "\345\210\240\351\231\244", nullptr));
        updateButton->setText(QCoreApplication::translate("Dialog", "\346\233\264\346\226\260", nullptr));
    } // retranslateUi
};

namespace Ui {
    class Dialog : public Ui_Dialog {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_DIALOG_H
