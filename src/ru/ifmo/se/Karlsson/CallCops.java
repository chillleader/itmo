package ru.ifmo.se.Karlsson;

import ru.ifmo.se.Karlsson.Action;
import ru.ifmo.se.Karlsson.Human;
import ru.ifmo.se.Karlsson.PhoneCallManager;

public class CallCops implements Action {

    @Override
    public String toString() {
        return "CallCops";
    }

    @Override
    public void act(Human h) {
        PhoneCallManager.callPolice(h);
    }
}
