#include "Root.hpp"

#include "Radar.hpp"

int main() {
    Root App = Root::get_mutable_instance();
//    App.AddEventHandler(new Radar());
    App.Run(sf::VideoMode(800,600,32), "NXJ Ultrasonic Radar");
    return 1;
}
