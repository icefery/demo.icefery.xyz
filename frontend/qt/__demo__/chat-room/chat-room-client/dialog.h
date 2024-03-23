#ifndef DIALOG_H
#define DIALOG_H

#include <QDialog>
#include <QTcpSocket>
#include <QHostAddress>
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
    void on_connectButton_clicked();
    void on_sendButton_clicked();
    void onConnected();
    void onDisconnected();
    void onReadyRead();
    // 网络异常槽函数
    void onErrorOccurred(QAbstractSocket::SocketError);
private:
    Ui::Dialog* ui;
    bool alive;
    QTcpSocket tcpSocket;
    QHostAddress host;
    quint16 port;
    QString nickname;
};

#endif // DIALOG_H