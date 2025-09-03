#include "calculator/calculator_dialog.h"
#include "ernie/ernie_dialog.h"
#include "image_view/image_view_dialog.h"
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

    ImageViewDialog imageViewDialog;
    imageViewDialog.show();

    ErnieDialog ernieDialog;
    ernieDialog.show();

    return QApplication::exec();
}
