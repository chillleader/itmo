package ru.ifmo.se.Karlsson;

public class Police implements Actable{

    /**
     * Показатель "хорошести" полиции, где 1 соответствует нормальному значению. Чем больше, тем больше преступлений
     * полиция может раскрыть единовременно.
     */
    static double quality = 1;

    /**
     * Количество преступлений за ночь
     */
    static int numberOfCrimes = 0;
    /**
     * Максимальное количество преступлений, которое может обработать нормальная полиция (с качеством 1)
     */
    static int maxCrimes = 100;

    public static void setMaxCrimes(int maxCrimes) {
        Police.maxCrimes = maxCrimes;
    }

    public static void setNumberOfCrimes(int numberOfCrimes) {
        Police.numberOfCrimes = numberOfCrimes;
    }

    public static void setQuality(double quality) {
        Police.quality = quality;
    }

    public static int getMaxCrimes() {
        return maxCrimes;
    }

    public static int getNumberOfCrimes() {
        return numberOfCrimes;
    }

    public double getQuality() { return quality; }

    /**
     * Обрабатывает поступивший звонок и отвечает в зависимости от загруженности и качества полиции
     * @return Positive - вызов принят, neutral - вы там держитесь, negative - занято
     */
    public static State call(boolean inc) {
        if (maxCrimes * quality < numberOfCrimes) return State.negative;
        else if (maxCrimes * quality < 2 * numberOfCrimes) {
            if (inc) numberOfCrimes++;
            return State.neutral;
        }
        else {
            if (inc) numberOfCrimes++;
            return State.positive;
        }
    }

    /**
     * Решает одно преступление, уменьшая их суммарное количество
     */
    public void act() {
        if (numberOfCrimes != 0) numberOfCrimes--;
    }

    @Override
    public String toString() {
        return "Police{quality = " + quality + ", maxCrimes = " + maxCrimes + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Police p = (Police)o;
        if (Double.compare(quality, p.getQuality()) != 0) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = new Integer(maxCrimes).hashCode();
        temp = Double.doubleToLongBits(quality);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}