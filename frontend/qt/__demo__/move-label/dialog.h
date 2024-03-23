#ifndef DIALOG_H
#define DIALOG_H

#include <QDialog>
#include <QMouseEvent>
#include <QKeyEvent>

QT_BEGIN_NAMESPACE
namespace Ui { class Dialog; }
QT_END_NAMESPACE

class Dialog : public QDialog {
Q_OBJECT
public:
    Dialog(QWidget* parent = nullptr);
    ~Dialog();
private:
    void mousePressEvent(QMouseEvent*);
    void mouseReleaseEvent(QMouseEvent*);
    void mouseMoveEvent(QMouseEvent*);
    void keyPressEvent(QKeyEvent* e);
private:
    Ui::Dialog* ui;
    bool draggable;
    QPoint point;
};

#endif // DIALOG_H