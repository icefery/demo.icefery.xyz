cmake_minimum_required(VERSION 3.25)

project(cpp_tutorial)

set(CMAKE_CXX_STANDARD 23)

file(GLOB_RECURSE cpp_absolute_paths "${CMAKE_CURRENT_LIST_DIR}/src/*.cpp")

foreach (cpp_absolute_path ${cpp_absolute_paths})
    file(RELATIVE_PATH cpp_relative_path "${CMAKE_CURRENT_LIST_DIR}" ${cpp_absolute_path})

    string(REPLACE "src/" "" executable "${cpp_relative_path}")
    string(REPLACE "/" "_" executable "${executable}")
    string(REPLACE ".cpp" "" executable "${executable}")

    add_executable("${executable}" "${cpp_relative_path}")

    message("${cpp_absolute_path} => ${cpp_relative_path} => ${executable}")
endforeach ()
