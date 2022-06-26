#include "dialog.h"
#include "ui_dialog.h"

Dialog::Dialog(QWidget* parent) : QDialog(parent), ui(new Ui::Dialog) {
    ui->setupUi(this);
    createDB();
    createTable();
    queryTable();
}

Dialog::~Dialog() {
    delete ui;
}

void Dialog::on_insertButton_clicked() {
    int id = ui->idEdit->text().toInt();
    if (id == 0) {
        QMessageBox::critical(this, "Error", "学号输入错误");
        return;
    }
    QString name = ui->nameEdit->text();
    if (name == "") {
        QMessageBox::critical(this, "Error", "姓名输入错误");
        return;
    }
    double score = ui->scoreEdit->text().toDouble();
    if (score < 0 || score > 100) {
        QMessageBox::critical(this, "Error", "成绩输入错误");
        return;
    }
    QString sql = QString("INSERT INTO student(id, name, score) VALUES(%1, '%2', %3)").arg(id).arg(name).arg(score);
    QSqlQuery query(db);
    if (!query.exec(sql)) {
        qDebug() << sql << query.lastError();
    } else {
        qDebug() << "插入数据成功";
        queryTable();
    }
}

void Dialog::on_deleteButton_clicked() {
    int id = ui->idEdit->text().toInt();
    QString sql = "DELETE FROM student WHERE id = :id";
    QSqlQuery query(db);
    query.prepare(sql);
    query.bindValue(":id", id);
    if (!query.exec()) {
        qDebug() << sql << query.lastError();
    } else {
        qDebug() << "删除数据成功";
        queryTable();
    }
}

void Dialog::on_updateButton_clicked() {
    int id = ui->idEdit->text().toInt();
    if (id == 0) {
        QMessageBox::critical(this, "Error", "学号输入错误");
        return;
    }
    double score = ui->scoreEdit->text().toDouble();
    if (score < 0 || score > 100) {
        QMessageBox::critical(this, "Error", "成绩输入错误");
        return;
    }
    QString sql = "UPDATE student SET score = :score WHERE id = :id";
    QSqlQuery query(db);
    query.prepare(sql);
    query.bindValue(":id", id);
    query.bindValue(":score", score);
    if (!query.exec()) {
        qDebug() << sql << query.lastError();
    } else {
        qDebug() << "更新数据成功";
        queryTable();
    }
}

void Dialog::on_sortButton_clicked() {
    QString sortColumn = ui->sortColumnComboBox->currentText();
    QString sortType = ui->sortTypeComboBox->currentText();
    QString sql = QString("SELECT * FROM student ORDER BY %1 %2").arg(sortColumn).arg(sortType);
    QSqlQueryModel* model = new QSqlQueryModel();
    model->setQuery(sql, db);
    ui->tableView->setModel(model);
}

void Dialog::createDB() {
    // 添加数据库驱动
    db = QSqlDatabase::addDatabase("QSQLITE");
    // 设置库名(文件名)
    db.setDatabaseName("student.db");
    // 打开数据库
    if (!db.open()) {
        qDebug() << "创建/打开数据库失败";
    } else {
        qDebug() << "创建/打开数据库成功";
    }
}

void Dialog::createTable() {
    QString sql = QString(
        "CREATE TABLE student ("
        "id    INT NOT NULL PRIMARY KEY,"
        "name  TEXT NOT NULL,"
        "score REAL NOT NULL"
        ")"
    );
    QSqlQuery query(db);
    if (!query.exec(sql)) {
        qDebug() << sql << query.lastError();
    } else {
        qDebug() << "建表成功";
    }
}

void Dialog::queryTable() {
    QString sql = "SELECT * FROM student";
    QSqlQueryModel* model = new QSqlQueryModel();
    model->setQuery(sql, db);
    ui->tableView->setModel(model);
}