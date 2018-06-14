package ru.ifmo.se.Karlsson;

public class Karlsson extends Human {

    public Karlsson(double satisfaction) {
        super();
        isMale = true;
        this.size = satisfaction;
        name = "Карлсон ";
        referName = "Карлсона ";
        dirActions = new DirectAction[] {new LookAt()};
    }

    public void estimatePudding() {
        Action estimateAction = new Action() { //Анонимный класс
            public void act(Human h) {
                System.out.print(Messages.getPhrase(h, this));
            }

            @Override
            public String toString() {
                return "EstimatePudding";
            }
        };
        estimateAction.act(this);
    }

    class Dialog implements Action {

        @Override
        public void act(Human h) {
            Messages.makeDialog();
        }
    }

    public void talk(Human h) {

        TakePudding tp = new TakePudding();
        tp.act(this);
        tp.act(h);
        Dialog d = new Dialog();
        d.act(this);
    }
}
