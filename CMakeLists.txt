set(PROJECT_NAME sonaris)
cmake_minimum_required(VERSION 2.8.0)

project(${PROJECT_NAME})

set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake-extras/")

set(Boost_USE_STATIC_LIBS ON)
set(Boost_USE_MULTITHREADED ON)

find_package(SFML COMPONENTS graphics system window REQUIRED)
find_package(Boost COMPONENTS filesystem system python date_time REQUIRED)

add_definitions(-Wall -Wextra -g -pedantic -fPIC -std=c++0x)
set(CMAKE_BUILD_TYPE "Debug")

set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/bin)
set(LIBRARY_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/lib)

include_directories(
	"${CMAKE_SOURCE_DIR}/src/"
    ${Boost_INCLUDE_DIRS}
    ${SFML_INCLUDE_DIRS}
    src/
)

aux_source_directory(src/ sources)

add_executable(${PROJECT_NAME}
    ${sources}
    )

target_link_libraries(${PROJECT_NAME}
    ${Boost_LIBRARIES}
    ${SFML_LIBRARIES}
    )

