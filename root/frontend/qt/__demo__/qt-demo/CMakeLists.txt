cmake_minimum_required(VERSION 3.5)
project(move-label)

set(CMAKE_PREFIX_PATH "${QTDIR};${CMAKE_PREFIX_PATH}")
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)
set(CMAKE_INCLUDE_CURRENT_DIR ON)
set(PROJECT_SOURCES
    src/main.cpp
    src/calculator/calculator_dialog.h
    src/on_slider_change/on_slider_change_dialog.h
    src/move_label/move_label_dialog.h
    src/move_label/move_label_dialog.ui
)

add_executable(
    "${PROJECT_NAME}" MACOSX_BUNDLE
    ${PROJECT_SOURCES}
)

find_package(
    Qt6 COMPONENTS
    Core
    Gui
    Widgets
    REQUIRED
)

target_link_libraries(
    "${PROJECT_NAME}"
    Qt::Core
    Qt::Gui
    Qt::Widgets
)

