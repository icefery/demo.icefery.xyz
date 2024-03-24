# Additional clean files
cmake_minimum_required(VERSION 3.16)

if("${CONFIG}" STREQUAL "" OR "${CONFIG}" STREQUAL "Release")
  file(REMOVE_RECURSE
  "CMakeFiles/chat-room-client_autogen.dir/AutogenUsed.txt"
  "CMakeFiles/chat-room-client_autogen.dir/ParseCache.txt"
  "chat-room-client_autogen"
  )
endif()
