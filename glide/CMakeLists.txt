cmake_minimum_required(VERSION 2.8.2)

include_directories(src/main/cpp/ src/main/cpp/utils src/main/cpp/giflib)

file(GLOB SRC src/main/cpp/*.c src/main/cpp/*.cpp
        src/main/cpp/giflib/*.c src/main/cpp/giflib/*.cpp)

add_library(glide SHARED ${SRC})

target_link_libraries(glide log jnigraphics)
