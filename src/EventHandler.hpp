#ifndef EVENTHANDLER_HPP
#define EVENTHANDLER_HPP

#include <SFML/Window/Event.hpp>
#include <SFML/Graphics/RenderTarget.hpp>

class EventHandler {
public:
    EventHandler();
    ~EventHandler();

    virtual void Update(const float timeDelta);
    virtual void Draw(sf::RenderTarget* renderTarget) const;
    virtual void Event(sf::Event event);
};

#endif
