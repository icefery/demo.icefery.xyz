# Additional clean files
cmake_minimum_required(VERSION 3.16)

if("${CONFIG}" STREQUAL "" OR "${CONFIG}" STREQUAL "Release")
  file(REMOVE_RECURSE
  "CMakeFiles/ftp-client-5_15_autogen.dir/AutogenUsed.txt"
  "CMakeFiles/ftp-client-5_15_autogen.dir/ParseCache.txt"
  "ftp-client-5_15_autogen"
  )
endif()
