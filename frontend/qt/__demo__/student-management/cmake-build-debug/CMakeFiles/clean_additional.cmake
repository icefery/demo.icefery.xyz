# Additional clean files
cmake_minimum_required(VERSION 3.16)

if("${CONFIG}" STREQUAL "" OR "${CONFIG}" STREQUAL "Debug")
  file(REMOVE_RECURSE
  "CMakeFiles/student-management_autogen.dir/AutogenUsed.txt"
  "CMakeFiles/student-management_autogen.dir/ParseCache.txt"
  "student-management_autogen"
  )
endif()
