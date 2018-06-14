package ru.ifmo.se.Karlsson;

import java.util.Stack;

public class Pudding {

    int portions;
    Type type;
    Stack<Portion> portionStack = new Stack<>();

    public Pudding(int portions, Type type, Size portionSize) {
        this.portions = portions;
        this.type = type;
        for (int i = 0; i < portions; i++) {
            portionStack.push(new Portion(portionSize));
        }
    }

    public Portion takePortion() throws NoPuddingException {
        if (portionStack.isEmpty()) throw new NoPuddingException();
        return portionStack.pop();
    }

    class Portion {

        Size size;

        public Portion(Size size) {
            this.size = size;
        }
    }

    /**
     * Тип пуддинга
     */
    static enum Type {
        Шоколадный, Ванильный, Ореховый
    }

    /**
     *
     * Размер порции
     */
    static enum Size {
        маленькие, средние, большие
    }
}
