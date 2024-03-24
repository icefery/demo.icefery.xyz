#ifndef DIALOG_H
#define DIALOG_H

#include "ui_dialog.h"
#include <QDebug>
#include <QDialog>
#include <QFile>
#include <QNetworkReply>
#include <QNetworkRequest>
#include <QUrl>

QT_BEGIN_NAMESPACE
namespace Ui {
    class Dialog;
}
QT_END_NAMESPACE

class Dialog : public QDialog {
    Q_OBJECT

private:
    Ui::Dialog* ui;
    QNetworkAccessManager* manager;
    QNetworkReply* downloadReply;
    QNetworkReply* uploadReply;
    QString downloadLocalPath;

public:
    Dialog() {
        this->ui = new Ui::Dialog();
        this->ui->setupUi(this);
        this->manager = new QNetworkAccessManager(this);
        // 默认值
        this->ui->hostEdit->setText("192.192.192.6");
        this->ui->portEdit->setText("21");
        this->ui->usernameEdit->setText("icefery");
        this->ui->passwordEdit->setText("icefery");
        this->ui->remotePathEdit->setText("/home/icefery/a.txt");
        this->ui->localPathEdit->setText("D:\\a.txt");
        this->ui->progressBar->setValue(0);
    }

private slots:
    // 上传按钮点击信号槽函数
    void on_uploadButton_clicked() {
        qDebug() << "on_uploadButton_clicked()";
        // 清空进度条
        on_clearButton_clicked();
        // 参数
        QString host = ui->hostEdit->text();
        int port = ui->portEdit->text().toInt();
        QString username = ui->usernameEdit->text();
        QString password = ui->passwordEdit->text();
        QString remotePath = ui->remotePathEdit->text();
        QString localPath = ui->localPathEdit->text();
        // 上传
        QUrl url;
        url.setScheme("ftp");
        url.setHost(host);
        url.setPort(port);
        url.setUserName(username);
        url.setPassword(password);
        url.setPath(remotePath);
        QFile file(localPath);
        if (file.open(QIODevice::ReadOnly)) {
            QNetworkRequest request(url);
            QByteArray data = file.readAll();
            uploadReply = manager->put(request, data);
            connect(uploadReply, &QNetworkReply::finished, this, &Dialog::onUploadFinished);
            connect(uploadReply, &QNetworkReply::uploadProgress, this, &Dialog::onUploadProgress);
            connect(uploadReply, &QNetworkReply::errorOccurred, this, &Dialog::onErrorOccurred);
            file.close();
        }
    }

    // 下载按钮点击槽函数
    void on_downloadButton_clicked() {
        qDebug() << "on_downloadButton_clicked()";
        // 清空进度条
        on_clearButton_clicked();
        // 参数
        QString host = ui->hostEdit->text();
        int port = ui->portEdit->text().toInt();
        QString username = ui->usernameEdit->text();
        QString password = ui->passwordEdit->text();
        QString remotePath = ui->remotePathEdit->text();
        QString localPath = ui->localPathEdit->text();
        // 保存下载路径
        downloadLocalPath = localPath;
        // 下载
        QUrl url;
        url.setScheme("ftp");
        url.setHost(host);
        url.setPort(port);
        url.setUserName(username);
        url.setPassword(password);
        url.setPath(remotePath);
        QNetworkRequest request(url);
        downloadReply = manager->get(request);
        connect(downloadReply, &QNetworkReply::finished, this, &Dialog::onDownloadFinished);
        connect(downloadReply, &QNetworkReply::downloadProgress, this, &Dialog::onDownloadProgress);
        connect(downloadReply, &QNetworkReply::errorOccurred, this, &Dialog::onErrorOccurred);
    }

    // 清空进度点击槽函数
    void on_clearButton_clicked() {
        ui->progressBar->setValue(0);
    }

    // 下载完成槽函数
    void onDownloadFinished() {
        qDebug() << "onDownloadFinished()";
        QFile file(downloadLocalPath);
        if (file.open(QIODevice::WriteOnly)) {
            QByteArray data = downloadReply->readAll();
            file.write(data);
            file.close();
        }
        downloadReply->deleteLater();
    }

    // 上传完成槽函数
    void onUploadFinished() {
        qDebug() << "onUploadFinished()";
        uploadReply->deleteLater();
    }

    // 下载进度槽函数
    void onDownloadProgress(qint64 bytesReceived, qint64 bytesTotal) {
        int percent = bytesTotal == 0 ? 0 : (bytesReceived / bytesTotal) * 100;
        qDebug() << QString("onDownloadProgress() bytesReceived=%1 bytesTotal=%2 percent=%3").arg(bytesReceived).arg(bytesTotal).arg(percent);
        ui->progressBar->setValue(percent);
    }

    // 上传进度槽函数
    void onUploadProgress(qint64 bytesSent, qint64 bytesTotal) {
        int percent = bytesTotal == 0 ? 0 : (bytesSent / bytesTotal) * 100;
        qDebug() << QString("onUploadProgress() bytesSent=%1 bytesTotal=%2 percent=%3").arg(bytesSent).arg(bytesTotal).arg(percent);
        ui->progressBar->setValue(percent);
    }

    // 错误槽函数
    void onErrorOccurred(QNetworkReply::NetworkError error) {
        qDebug() << "onErrorOccurred()" << qt_error_string(error);
    }
};

#endif // DIALOG_H