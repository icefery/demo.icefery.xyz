#include "calculator/calculator_dialog.h"
#include "move_label/move_label_dialog.h"
#include "on_slider_change/on_slider_change_dialog.h"

#include <QApplication>

int main(int argc, char* argv[]) {
    QApplication a(argc, argv);

    MoveLabelDialog moveLabelDialog;
    moveLabelDialog.show();

    CalculatorDialog calculatorDialog;
    calculatorDialog.show();

    OnSliderChangeDialog onSliderChangeDialog;
    onSliderChangeDialog.show();

    return QApplication::exec();
}