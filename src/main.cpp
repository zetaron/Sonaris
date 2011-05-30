#include "Root.hpp"

//#include "Radar.hpp"

int main() {
    Root& app = Root::get_mutable_instance();
//    App.AddEventHandler(new Radar());
    app.Run(sf::VideoMode(800,600,32), "NXJ Ultrasonic Radar");
    return 1;
}
