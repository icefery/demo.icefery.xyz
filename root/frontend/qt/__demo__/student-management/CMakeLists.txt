cmake_minimum_required(VERSION 3.5)
project(student-management)

set(CMAKE_PREFIX_PATH "${QTDIR};${CMAKE_PREFIX_PATH}")
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)
set(CMAKE_INCLUDE_CURRENT_DIR ON)
set(PROJECT_SOURCES
    src/main.cpp
    src/dialog.h
    src/dialog.ui
)

add_executable(
    "${PROJECT_NAME}" MACOSX_BUNDLE
    "${PROJECT_SOURCES}"
)

find_package(
    Qt6 COMPONENTS
    Core
    Gui
    Sql
    Widgets
    REQUIRED
)

target_link_libraries(
    "${PROJECT_NAME}"
    Qt::Core
    Qt::Gui
    Qt::Sql
    Qt::Widgets
)

