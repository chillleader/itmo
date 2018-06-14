package ru.ifmo.se.Karlsson;

public abstract class DirectAction implements Action{

    String malePhrase = "";
    String femPhrase = "";
    String posMale = "";
    String negMale = "";

    @Override
    public String toString() {
        return "DirectAction{" + "malePhrase='" + malePhrase + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectAction that = (DirectAction) o;

        if (!malePhrase.equals(that.malePhrase)) return false;
        if (!femPhrase.equals(that.femPhrase)) return false;
        if (!posMale.equals(that.posMale)) return false;
        if (!negMale.equals(that.negMale)) return false;
        if (!posFem.equals(that.posFem)) return false;
        return negFem.equals(that.negFem);
    }

    @Override
    public int hashCode() {
        int result = malePhrase.hashCode();
        result = 31 * result + femPhrase.hashCode();
        result = 31 * result + posMale.hashCode();
        result = 31 * result + negMale.hashCode();
        result = 31 * result + posFem.hashCode();
        result = 31 * result + negFem.hashCode();
        return result;
    }

    String posFem = "";
    String negFem = "";

    /**
     * Применяет действие на указанного персонажа. Дополняет метод act().
     * @param actor Персонаж, выполняющий действие
     * @param target Персонаж, на которого направлено действие
     */
    public void apply(Human actor, Human target) {
        System.out.print(actor.name);
        act(actor);
        double stf = target.getSize();
        if (stf < 50D) {
            if (target.isMale) System.out.print(negMale);
            else System.out.print(negFem);
        }
        else {
            if (target.isMale) System.out.print(posMale);
            else System.out.print(posFem);
        }
        target.act();
    }

    public void act(Human h) {
        if (h.isMale) System.out.print(malePhrase);
        else System.out.print(femPhrase);
    }

}
