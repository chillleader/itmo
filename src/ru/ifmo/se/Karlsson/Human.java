package ru.ifmo.se.Karlsson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@XmlRootElement
public class Human implements Actable, Comparable<Human>, Serializable {

    String name;
    transient String referName;
    boolean isMale;
    double size = 60;
    Color color = Color.red;
    double x = 10;
    double y = 10;
    String dateTimeString;

    public transient java.time.LocalDateTime dateTime;
    transient Action[] actions = null;
    transient DirectAction[] dirActions = null;
    transient static Pudding pudding;

    public Human () {
        dateTime = LocalDateTime.now();
    }

    /**
     * Определяет характер героя и его "обидчивость", варьируется от 0 до 1,
     * где 0 - пассивность, а 1 - вспыльчивость
     */
    transient double temper = 0.5;

    public double getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
    public String getReferName() { return referName; }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }
    @XmlElement
    public void setReferName(String referName) {
        this.referName = referName;
    }
    @XmlElement
    public void setMale(boolean male) {
        isMale = male;
    }
    @XmlElement
    public void setSize(double size) {
        this.size = size;
    }
    @XmlElement
    public void setX(double x) {
        this.x = x;
    }
    @XmlElement
    public void setY(double y) {
        this.y = y;
    }
    @XmlElement
    public void setColor(Color color) { this.color = color; }
    @XmlElement
    public void setDateTimeString(String s) {
        this.dateTimeString = s;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dateTime = LocalDateTime.parse(s, formatter);
    }

    public boolean isMale() {
        return isMale;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
    public String getDateTimeString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
    public void updateDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dateTime = LocalDateTime.parse(dateTimeString, formatter);
    }
    //public LocalDateTime getDateTime() { return dateTime; }
    //public void setDateTime(LocalDateTime dt) { dateTime = dt; }

    /**
     * Выполнить все действия, доступные персонажу
     */
    public void act() {
        if (actions == null) throw new NoActionsException();
        for (Action i : actions) {
            i.act(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Human human = (Human) o;

        if (isMale != human.isMale) return false;
        if (Double.compare(human.size, size) != 0) return false;
        if (Double.compare(human.temper, temper) != 0) return false;
        if (name != null ? !name.equals(human.name) : human.name != null) return false;
        return referName != null ? referName.equals(human.referName) : human.referName == null;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (referName != null ? referName.hashCode() : 0);
        result = 31 * result + (isMale ? 1 : 0);
        temp = Double.doubleToLongBits(size);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(temper);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Выполнить все направленные действия, доступные персонажу
     * @param target Указывает, на кого направить действия
     */
    void actDirect(Human target) {
        if (dirActions == null) {
            throw new NoActionsException();
        }
        else for (DirectAction i : dirActions) {
            i.apply(this, target);
        }
    }


    static class TakePudding implements Action {

        @Override
        public void act(Human h) {
            try {
                pudding.takePortion();
            }
            catch (NoPuddingException e) {
                System.out.print("А это значило, что " + h.name + "останется без сладкого, потому что " +
                        (h.isMale ? "он " : "она ") + "сделал" + (h.isMale ? " " : "а ") + "только четыре порции. ");
            }


        }

    }

    @Override
    public int compareTo(Human h) {
        return name.compareTo(h.name);
    }
}