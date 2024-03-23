#ifndef DIALOG_H
#define DIALOG_H

#include <QDialog>
#include <QTcpServer>
#include <QTcpSocket>
#include <QTimer>

QT_BEGIN_NAMESPACE
namespace Ui { class Dialog; }
QT_END_NAMESPACE

class Dialog : public QDialog {
Q_OBJECT
public:
    Dialog(QWidget* parent = nullptr);
    ~Dialog();
private slots:
    // 创建服务器按钮点击槽函数
    void on_createButton_clicked();
    // 响应客户端连接请求槽函数
    void onNewConnection();
    // 接收欧客户端消息槽函数
    void onReadyRead();
    // 通信断开检查槽函数
    void onTimeout();
private:
    // 转发聊天消息到其它客户端
    void sendMessage(const QByteArray& buf);
private:
    Ui::Dialog* ui;
    QTcpServer tcpServer;
    quint16 port;
    QList<QTcpSocket*> tcpClientList;
    QTimer timer;
};

#endif // DIALOG_H