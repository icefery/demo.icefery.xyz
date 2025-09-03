#ifndef DIALOG_H
#define DIALOG_H

#include "ui_dialog.h"
#include <QDialog>
#include <QTcpServer>
#include <QTcpSocket>
#include <QTimer>

QT_BEGIN_NAMESPACE
namespace Ui {
    class Dialog;
}
QT_END_NAMESPACE

class Dialog : public QDialog {
    Q_OBJECT

public:
    Dialog() {
        this->ui = new Ui::Dialog();
        this->ui->setupUi(this);
        connect(&tcpServer, SIGNAL(newConnection()), this, SLOT(onNewConnection()));
        connect(&timer, SIGNAL(timeout()), SLOT(onTimeout()));
    }

private slots:
    // 创建服务器按钮点击槽函数
    void on_createButton_clicked() {
        port = ui->portEdit->text().toShort();
        if (tcpServer.listen(QHostAddress::Any, port)) {
            qDebug() << "创建服务器成功";
            ui->createButton->setEnabled(false);
            ui->portEdit->setEnabled(false);
            // 开启定时器
            timer.start(3000);
        } else {
            qDebug() << "创建服务器失败";
        }
    }

    // 响应客户端连接请求槽函数
    void onNewConnection() {
        // 后去和客户端通信的套接字
        QTcpSocket* tcpSocket = tcpServer.nextPendingConnection();
        // 保存套接字到容器
        tcpClientList.append(tcpSocket);
        // 当客户端向服务器发送消息时通信套接字发送 readyRead() 信号
        connect(tcpSocket, SIGNAL(readyRead()), this, SLOT(onReadyRead()));
    }

    // 接收客户端消息槽函数
    void onReadyRead() {
        // 遍历容器获取哪个客户端给服务器发送了消息
        for (int i = 0; i < tcpClientList.size(); i++) {
            // 返回 0 表示没有消息
            if (tcpClientList.at(i)->bytesAvailable() != 0) {
                // 读取消息
                QByteArray buf = tcpClientList.at(i)->readAll();
                // 显示消息
                ui->listWidget->addItem(buf);
                ui->listWidget->scrollToBottom();
                // 转发消息给所有在线客户端
                sendMessage(buf);
            }
        }
    }

    // 通信断开检查槽函数
    void onTimeout() {
        // 遍历检查容器中保存的通信套接字是否已经断开连接，如果是则删除
        for (int i = 0; i < tcpClientList.size(); i++) {
            if (tcpClientList.at(i)->state() == QAbstractSocket::UnconnectedState) {
                tcpClientList.removeAt(i);
                i--;
            }
        }
    }

private:
    // 转发聊天消息到其它客户端
    void sendMessage(const QByteArray& buf) {
        for (int i = 0; i < tcpClientList.size(); i++) {
            tcpClientList.at(i)->write(buf);
        }
    }

private:
    Ui::Dialog* ui;
    QTcpServer tcpServer;
    quint16 port;
    QList<QTcpSocket*> tcpClientList;
    QTimer timer;
};

#endif // DIALOG_H