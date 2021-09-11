package ru.ifmo.se.Karlsson;

@Deprecated
public class Greeting extends DirectAction {

    @Override
    public String toString() {
        return "Greeting";
    }

    Greeting() {
        malePhrase = "заметил, что у ";
        femPhrase = "заметила, что у ";
        posMale = "него хорошее настроене, и приветственно помахал руками. ";
        posFem = "неё хорошее настроениее, и приветственно помахал руками. ";
        negMale = "него недовольный вид, и поднял пухлый указательный пальчик. ";
        negFem = "неё недовольный вид, и поднял пухлый указательный пальчик. ";
    }

}
