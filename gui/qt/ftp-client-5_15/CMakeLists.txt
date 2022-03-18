cmake_minimum_required(VERSION 3.5)

project(ftp-client-5_15 VERSION 0.1 LANGUAGES CXX)

set(CMAKE_INCLUDE_CURRENT_DIR ON)

set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(Qt5 COMPONENTS Widgets Network REQUIRED)

set(
    PROJECT_SOURCES
    main.cpp
    dialog.h
    dialog.ui
)

add_executable(ftp-client-5_15 ${PROJECT_SOURCES})

target_link_libraries(ftp-client-5_15 PRIVATE Qt5::Widgets Qt5::Network)