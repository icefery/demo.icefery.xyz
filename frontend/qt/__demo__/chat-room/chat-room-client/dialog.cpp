#include "dialog.h"
#include "ui_dialog.h"

Dialog::Dialog(QWidget* parent) : QDialog(parent), ui(new Ui::Dialog) {
    ui->setupUi(this);
    alive = false;
    connect(&tcpSocket, SIGNAL(connected()), this, SLOT(onConnected()));
    connect(&tcpSocket, SIGNAL(disconnected()), this, SLOT(onDisconnected()));
    connect(&tcpSocket, SIGNAL(readyRead()), this, SLOT(onReadyRead()));
    connect(&tcpSocket, SIGNAL(errorOccurred(QAbstractSocket::SocketError)), this, SLOT(onErrorOccurred(QAbstractSocket::SocketError)));
}

Dialog::~Dialog() {
    delete ui;
}

void Dialog::on_connectButton_clicked() {
    // 如果当前是离线状态，则建立和服务器的连接，否则端口和服务器的连接
    if (!alive) {
        host.setAddress(ui->hostEdit->text());
        port = ui->portEdit->text().toShort();
        nickname = ui->nicknameEdit->text();
        tcpSocket.connectToHost(host, port);
    } else {
        QString message = "系统: " + nickname + "离开了聊天室";
        tcpSocket.write(message.toUtf8());
        tcpSocket.disconnectFromHost();
    }
}


void Dialog::on_sendButton_clicked() {
    QString message = ui->messageEdit->text();
    if (message == "") {
        return;
    }
    message = nickname + ": " + message;
    tcpSocket.write(message.toUtf8());
    ui->messageEdit->clear();
}

void Dialog::onConnected() {
    alive = true;
    ui->sendButton->setEnabled(true);
    ui->hostEdit->setEnabled(false);
    ui->portEdit->setEnabled(false);
    ui->nicknameEdit->setEnabled(false);
    ui->connectButton->setText("离开聊天室");
    // 发送系统提示消息
    QString message = "系统: " + nickname + "进入了聊天室";
    tcpSocket.write(message.toUtf8());
}

void Dialog::onDisconnected() {
    alive = false;
    ui->sendButton->setEnabled(false);
    ui->hostEdit->setEnabled(true);
    ui->portEdit->setEnabled(true);
    ui->nicknameEdit->setEnabled(true);
    ui->connectButton->setText("连接服务器");
}

void Dialog::onReadyRead() {
    if (tcpSocket.bytesAvailable() != 0) {
        QByteArray buf = tcpSocket.readAll();
        ui->listWidget->addItem(buf);
        ui->listWidget->scrollToBottom();
    }
}

// 网络异常槽函数
void Dialog::onErrorOccurred(QAbstractSocket::SocketError) {
    QMessageBox::critical(this, "Error", tcpSocket.errorString());
}