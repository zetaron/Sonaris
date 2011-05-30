#ifndef ROOT_HPP
#define ROOT_HPP

#include <string>
#include <vector>

#include <boost/serialization/singleton.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/ptr_container/ptr_vector.hpp>

#include <SFML/Graphics.hpp>

#include "EventHandler.hpp"

class Root : public boost::serialization::singleton<Root> {
public:
    Root();
    ~Root();

    boost::shared_ptr<sf::RenderWindow> GetRenderWindowPtr();
    void Run(sf::VideoMode videoMode, std::string windowTitle);
    void Run(sf::VideoMode videoMode, std::string windowTitle, sf::Vector2i windowSize);
    void SetWindowTitle(std::string windowTitle);

    void HandleEvent(sf::Event event, float timeDelta);

    void AddEventHandler(EventHandler* eventHandler);

private:
    boost::shared_ptr<sf::RenderWindow> mRenderWindow;

    boost::ptr_vector<EventHandler> mEventHandler;
};

#endif
