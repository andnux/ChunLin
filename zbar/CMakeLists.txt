cmake_minimum_required(VERSION 2.8.2)

include_directories(
        libs/include
        src/main/cpp
        src/main/cpp/zbar
        src/main/cpp/zbar/decoder
        src/main/cpp/zbar/processor
        src/main/cpp/zbar/qrcode
        src/main/cpp/zbar/window
        src/main/cpp/zbar/video)

file(GLOB CPP src/main/cpp/*.c src/main/cpp/*.cpp)

add_library(zbar SHARED
        src/main/cpp/zbar/img_scanner.c
        src/main/cpp/zbar/decoder.c
        src/main/cpp/zbar/image.c
        src/main/cpp/zbar/symbol.c
        src/main/cpp/zbar/convert.c
        src/main/cpp/zbar/config.c
        src/main/cpp/zbar/scanner.c
        src/main/cpp/zbar/error.c
        src/main/cpp/zbar/refcnt.c
        src/main/cpp/zbar/video.c
        src/main/cpp/zbar/video/null.c
        src/main/cpp/zbar/decoder/code128.c
        src/main/cpp/zbar/decoder/code39.c
        src/main/cpp/zbar/decoder/code93.c
        src/main/cpp/zbar/decoder/codabar.c
        src/main/cpp/zbar/decoder/databar.c
        src/main/cpp/zbar/decoder/ean.c
        src/main/cpp/zbar/decoder/i25.c
        src/main/cpp/zbar/decoder/qr_finder.c
        src/main/cpp/zbar/qrcode/bch15_5.c
        src/main/cpp/zbar/qrcode/binarize.c
        src/main/cpp/zbar/qrcode/isaac.c
        src/main/cpp/zbar/qrcode/qrdec.c
        src/main/cpp/zbar/qrcode/qrdectxt.c
        src/main/cpp/zbar/qrcode/rs.c
        src/main/cpp/zbar/qrcode/util.c
        ${CPP})

set(ABI ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI})

target_link_libraries(zbar log jnigraphics ${ABI}/libiconv.a)
