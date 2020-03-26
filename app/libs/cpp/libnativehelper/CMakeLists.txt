project(libnativehelper)

set(LIBNATIVEHELPER_SOURCES
        src/JniConstants.cpp
        src/JNIHelp.cpp
        )

add_library(libnativehelper ${LIBNATIVEHELPER_SOURCES})

set(CMAKE_CXX_FLAGS "-fPIC")
set_target_properties(libnativehelper PROPERTIES CMAKE_SHARED_LINKER_FLAGS "-fPIC")

find_library(log-lib liblog)
target_link_libraries(
        libnativehelper
        ${log-lib}
)

target_include_directories(libnativehelper PUBLIC
        src/include
        src/include_jni
        src/header_only_include
        src/platform_include
        )
