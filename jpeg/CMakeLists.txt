cmake_minimum_required(VERSION 2.8.2)

include_directories(src/main/cpp/jpeg)

file(GLOB SRC src/main/cpp/jpeg/*.c src/main/cpp/*.c)

add_library(jpeg SHARED ${SRC})

target_link_libraries(jpeg log jnigraphics)
