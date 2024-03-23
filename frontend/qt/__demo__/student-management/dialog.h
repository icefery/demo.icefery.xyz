#ifndef DIALOG_H
#define DIALOG_H

#include <QDialog>
#include <QSqlDatabase>
#include <QSqlQuery>
#include <QSqlQueryModel>
#include <QSqlError>
#include <QMessageBox>

QT_BEGIN_NAMESPACE
namespace Ui { class Dialog; }
QT_END_NAMESPACE

class Dialog : public QDialog {
Q_OBJECT
public:
    Dialog(QWidget* parent = nullptr);
    ~Dialog();
private slots:
    void on_insertButton_clicked();
    void on_deleteButton_clicked();
    void on_updateButton_clicked();
    void on_sortButton_clicked();
private:
    void createDB();
    void createTable();
    void queryTable();
private:
    Ui::Dialog* ui;
    QSqlDatabase db;
};

#endif // DIALOG_H