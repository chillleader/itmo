package ru.ifmo.se.Karlsson;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Содержит реплики действий для конкретных персонажей.
 * Для каждого из сюжетных персонажей определены действия: Nag, SayNothing, CallCops, LookAt.
 */
public class Messages {
    //todo: каждому персонажу сопоставлены определённые фразы для определённых действий
    //todo: если на действие для конкретного персонажа нет фразы, он берёт обычную


    //Рандомные действия для рандомных героев. Кратные 2 - для мужского пола, не кратные - для женского.
    private static PhraseContainer randomActions = new PhraseContainer();
    private static void setRandomActions() {
        randomActions.add("0", " вошел в комнату. ");
        randomActions.add("1", " вошла в комнату. ");
        randomActions.add("2", " хлопнул в ладоши. ");
        randomActions.add("3", " хлопнула в ладоши. ");
        randomActions.add("4", " скушал печеньку. ");
        randomActions.add("5", " скушала печеньку. ");
        randomActions.add("6", " кивнул головой. ");
        randomActions.add("7", " кивнула головой. ");
        randomActions.add("8", " вышел из комнаты. ");
        randomActions.add("9", " вышла из комнаты. ");
    }

    //LookAt
    static PhraseContainer lookAtPos = new PhraseContainer();
    private static void setLookAtPos() {
        lookAtPos.add("Карлсон ", "Карлсон заметил, что у * довольный вид и поднял пухлый большой палец. ");
        lookAtPos.add("Малыш ", "Малыш тревожно посмотрел на *, однако * одобрительно кивнул головой. ");
        lookAtPos.add("Дядя Юлиус ", "Дядя Юлиус посмотрел на * и едва заметно улыбнулся. ");
        lookAtPos.add("Фрекен Бок ", "Фрекен Бок посмотрела на * и приветливо подняла взгляд. ");
        lookAtPos.defMale = "Он посмотрел на * и улыбнулся. ";
        lookAtPos.defFem = "Она посмотрела на * и улыбнулась. ";
    }
    static PhraseContainer lookAtNeg = new PhraseContainer();
    private static void setLookAtNeg() {
        lookAtNeg.add("Карлсон ", "Карлсон заметил, что у * недовольный вид и поднял пухлый указательный пальчик. ");
        lookAtNeg.add("Малыш ", "Малыш тревожно посмотрел на *, но, казалось, * ничего не слышал. ");
        lookAtNeg.add("Дядя Юлиус ", "Дядя Юлиус посмотрел на * и нахмурился. ");
        lookAtNeg.add("Фрекен Бок ", "Фрекен Бок посмотрела на * и моментально отвела взгляд. ");
        lookAtNeg.defMale = "Он посмотрел на * и нахмурился. ";
        lookAtNeg.defFem = "Она посмотрела на * и нахмурилась. ";
    }

    static PhraseContainer doNothingPos = new PhraseContainer();
    private static void setDoNothingPos() {
        doNothingPos.add("Карлсон ", "Карлсон улыбнулся, но промолчал. ");
        doNothingPos.add("Малыш ", "Малыш обрадовался, но ничего не сказал. ");
        doNothingPos.add("Дядя Юлиус ", "Дядя Юлиус довольно пробормотал себе что-то под нос. ");
        doNothingPos.add("Фрекен Бок ", "Фрекен Бок улыбнулась, но всё же не вымолвила ни слова. ");
        doNothingPos.defMale = "Он улыбнулся, но ничего не сказал. ";
        doNothingPos.defFem = "Она улыбнулась, но ничего не сказала. ";
    }

    static PhraseContainer doNothingNeg = new PhraseContainer();
    private static void setDoNothingNeg() {
        doNothingNeg.add("Карлсон ", "Карлсон нахмурился, но промолчал. ");
        doNothingNeg.add("Малыш ", "Малыш загрустил, но не сказал ничего. ");
        doNothingNeg.add("Дядя Юлиус ", "Дядя Юлиус пробурчал что-то себе под нос и нахмурился. ");
        doNothingNeg.add("Фрекен Бок ", "Фрекен Бок ещё плотнее сжала губы, но не вымолвила ни слова. ");
        doNothingNeg.defMale = "Он нахмурился, но ничего не сказал. ";
        doNothingNeg.defFem = "Она нахмурилась, но ничего не сказала. ";
    }

    static PhraseContainer policeCallNeu = new PhraseContainer();
    private static void setPoliceCallNeu() {
        policeCallNeu.defMale = ("Он звонил в участок и сообщил о происшествии, но его слова не вызвали никакого интереса. " +
                "У них за ночь случилось ещё * краж, сказали они, которыми необходимо заняться. " +
                "Они только поинтересовались, что же всё-таки пропало.");
        policeCallNeu.defFem = "Она звонила в участок и сообщила о происшествии, но её слова не вызвали никакого интереса. " +
                "У них за ночь случилось ещё * краж, сказали они, которыми необходимо заняться. " +
                "Они только поинтересовались, что же всё-таки пропало.";
    }

    static PhraseContainer policeCallPos = new PhraseContainer();
    private static void setPoliceCallPos() {
        policeCallPos.defMale = ("Он звонил в участок и сообщил о происшествии, и ему пообещали как можно скорее приехать и разобраться. " +
                "У них за ночь случилось всего * краж, сказали они, с которыми они легко справлялись. " +
                "Они также поинтересовались, что же всё-таки пропало.");
        policeCallPos.defFem = "Она звонила в участок и сообщила о происшествии, и ей пообещали как можно скорее приехать и разобраться. " +
                "У них за ночь случилось всего * краж, сказали они, с которыми они легко справлялись. " +
                "Они также поинтересовались, что же всё-таки пропало.";
    }

    static PhraseContainer policeCallNeg = new PhraseContainer();
    private static void setPoliceCallNeg() {
        policeCallNeg.defMale = ("Он звонил в участок и сообщил о происшествии, но там даже не взяли трубку. " +
                "Видимо, у них было полно работы, с которой они попросту не справлялись. ");
        policeCallNeg.defFem = "Она звонила в участок и сообщила о происшествии, но там даже не взяли трубку. " +
                "Видимо, у них было полно работы, с которой они попросту не справлялись. ";
    }

    static PhraseContainer nagPos = new PhraseContainer();
    private static void setNagPos() {
        nagPos.add("Дядя Юлиус ", "Он всё ворчал. Какая нынче плохая погода! ");
        nagPos.add("Карлсон ", "Карлсон почему-то ворчал. Наверное, у него просто кончилось варенье. ");
        nagPos.add("Фрекен Бок ", "Фрекен Бок как всегда ворчала на что-то неопределённое. ");
        nagPos.add("Малыш ", "Малыш жаловался на плохую погоду. ");
        nagPos.defMale = "Он ворчал. ";
        nagPos.defFem = "Она ворчала. ";
    }

    static PhraseContainer nagNeg = new PhraseContainer();
    private static void setNagNeg() {
        nagNeg.add("Дядя Юлиус ", "Он всё ворчал. Какая в городе плохая полиция! ");
        nagNeg.add("Карлсон ", "Карлсон почему-то сильно ворчал. ");
        nagNeg.add("Фрекен Бок ", "Фрекен Бок как всегда ворчала на всё подряд. ");
        nagNeg.add("Малыш ", "Малыш жаловался на плохое самочувствие. ");
        nagNeg.defMale = "Он ворчал. ";
        nagNeg.defFem = "Она ворчала. ";
    }

    static PhraseContainer takePudding = new PhraseContainer();
    private static void setTakePudding() {
        takePudding.add("Карлсон ", "Он проглотил свою порцию быстрее, чем Малыш успел съесть ложечку от своего, и сказал: ");
    }

    static PhraseContainer estimatePudding = new PhraseContainer();
    private static void setEstimatePudding() {
        estimatePudding.add("Карлсон ", "* пудинг, разлитый в * формочки, Карлсон тоже одобрил. Он проглотил всю свою порцию прежде, чем Малыш успел съесть ложечку от своего, и сказал: ");
    }

    static PhraseContainer noPudding = new PhraseContainer();
    public static void setNoPudding() {
        estimatePudding.add("Карлсон ", "Карлсон хотел взять ещё порцию, но от пудинга ничего не осталось. ");
        estimatePudding.add("Малыш ", "Малыш увидел, что пудинг кончился, и приуныл. ");
        estimatePudding.add("Дядя Юлиус ", "Дядя Юлиус потянулся было за пудингом, но от него ничего не осталось.");
        estimatePudding.add("Фрекен Бок ", "А это значило, что Фрекен Бок останется без сладкого, потому что она сделала только четыре порции. ");
    }

    static PhraseContainer dialog = new PhraseContainer();
    private static void setDialog() {
        dialog.add("0", "\n - Да, пудинг хорош, спору нет, но я знаю, что в два раза вкуснее.");
        dialog.add("1", "\n - Что? - заинтересовался Малыш.");
        dialog.add("2", "\n - Два таких пудинга, - объявил Карлсон и взял себе новую порцию. ");

    }

    @NotNull
    static String getDirect(Human actor, Human target, Action action) {

        StringBuffer s = new StringBuffer();

        if (action.toString().equals("LookAt")) {
            if (target.size < 50) {
                s = lookAtNeg.getPhrase(actor.name, actor.isMale);
            }
            else s = lookAtPos.getPhrase(actor.name, actor.isMale);

            if (actor.name.equals("Малыш ")) {
                s.replace(s.indexOf("*"), s.indexOf("*") + 1, target.referName);
                s.replace(s.indexOf("*"), s.indexOf("*") + 1, target.isMale ? "он" : "она");
            }
            else s.replace(s.indexOf("*"), s.indexOf("*") + 1, target.isMale ? "него" : "неё");
        }
        return s.toString();
    }

    @NotNull
    static String getPhrase(Human actor, Action action) {

        StringBuffer s = new StringBuffer();

        if (action.toString().equals("SayNothing")) {
            if (actor.size < 50) {
                s = doNothingNeg.getPhrase(actor.name, actor.isMale);
            }
            else s = doNothingPos.getPhrase(actor.name, actor.isMale);
        }

        else if (action.toString().equals("CallCops")) {
            if (Police.call(true) == State.neutral) {
                s = policeCallNeu.getPhrase("", actor.isMale);
                s.replace(s.indexOf("*"), s.indexOf("*") + 1, new Integer(Police.getNumberOfCrimes()).toString());
            }
            else if (Police.call(false) == State.negative) {
                s = policeCallNeg.getPhrase("", actor.isMale);
            }
            else {
                s = policeCallPos.getPhrase("", actor.isMale);
                s.replace(s.indexOf("*"), s.indexOf("*") + 1, new Integer(Police.getNumberOfCrimes()).toString());
            }
        }

        else if (action.toString().equals("Nag")) {
            if (Police.quality < 1) {
                s = nagNeg.getPhrase(actor.name, actor.isMale);
            }
            else s = nagPos.getPhrase(actor.name, actor.isMale);
        }

        if (action.toString().equals("EstimatePudding")) {
            s = estimatePudding.getPhrase(actor.name, actor.isMale);
            s.replace(s.indexOf("*"), s.indexOf("*") + 1, Human.pudding.type.toString());
            s.replace(s.indexOf("*"), s.indexOf("*") + 1, Human.pudding.portionStack.peek().size.toString());
        }

        return s.toString();
    }

    static void makeDialog() {
        for (Integer i = 0; i < 3; i++) {
            System.out.print(dialog.getPhrase(i.toString(), true));
        }
    }

    static void setMessages() {
        setLookAtNeg();
        setLookAtPos();
        setDoNothingNeg();
        setDoNothingPos();
        setPoliceCallNeu();
        setPoliceCallNeg();
        setPoliceCallPos();
        setNagNeg();
        setNagPos();
        setTakePudding();
        setEstimatePudding();
        setDialog();
        setRandomActions();
    }

    @NotNull
    static String getRandomAction(Human h) {
        Random rand = new Random();
        Integer randN = rand.nextInt(10);
        if (randN % 2 == 0 && !h.isMale) {
            randN++;
        }
        if (randN % 2 == 1 && h.isMale) {
            randN--;
        }
        return h.name + randomActions.getPhrase(randN.toString(), h.isMale);
    }
}
