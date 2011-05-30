#include "Root.hpp"

#include <boost/foreach.hpp>

Root::Root() {
}

Root::~Root() {
}

void Root::Run(sf::VideoMode videoMode, std::string windowTitle) {
    Run(videoMode, windowTitle, sf::Vector2i(800,600));
}

void Root::Run(sf::VideoMode videoMode, std::string windowTitle, sf::Vector2i windowSize) {
    mRenderWindow = boost::shared_ptr<sf::RenderWindow>(new sf::RenderWindow);
    mRenderWindow->Create(videoMode, windowTitle);
    mRenderWindow->SetPosition(sf::VideoMode::GetDesktopMode().Width / 2 - windowSize.x / 2, sf::VideoMode::GetDesktopMode().Height / 2 - windowSize.y / 2);
    mRenderWindow->EnableVerticalSync(true);
    mRenderWindow->SetActive(true);

    sf::Clock Clock;
    while(GetRenderWindowPtr()->IsOpened()) {
        float timeDelta = Clock.GetElapsedTime();
        Clock.Reset();

        sf::Event event;
        while(GetRenderWindowPtr()->GetEvent(event))
            HandleEvent(event, timeDelta);
        GetRenderWindowPtr()->Display();
    }
}

void Root::SetWindowTitle(std::string windowTitle) {
    GetRenderWindowPtr()->SetTitle(windowTitle);
}

void Root::HandleEvent(sf::Event event, float timeDelta) {
    BOOST_FOREACH(EventHandler tmp, mEventHandler) {
        tmp.Event(event);
        tmp.Update(timeDelta);
    }
}

void Root::AddEventHandler(EventHandler *eventHandler) {
    mEventHandler.push_back(eventHandler);
}
