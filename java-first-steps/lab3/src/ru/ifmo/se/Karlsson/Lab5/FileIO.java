package ru.ifmo.se.Karlsson.Lab5;

import ru.ifmo.se.Karlsson.Human;
import ru.ifmo.se.Karlsson.Main;
import ru.ifmo.se.Karlsson.Lab6.CollectionManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.PriorityBlockingQueue;

@XmlRootElement(name = "humans")
@XmlAccessorType(XmlAccessType.FIELD)
public class FileIO {

    @XmlElement(name = "human")
    private PriorityBlockingQueue<Human> q;

    public FileIO() {
        q = CollectionManager.q;
    }

    public void setQ(PriorityBlockingQueue<Human> q) {
        this.q = q;
    }

    public PriorityBlockingQueue<Human> getQ() {
        return q;
    }


    public PriorityBlockingQueue<Human> load(String path) throws java.io.FileNotFoundException {
        FileReader fr = new FileReader(path);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(FileIO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            FileIO f = (FileIO)jaxbUnmarshaller.unmarshal(fr);
            for (Human h : f.q) {
                System.out.println(h.getName());
            }
            fr.close();
            return f.q;
        }
        catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Файл пуст или отсутствует.");
        }

        return new PriorityBlockingQueue<>();
    }

    public void save() {

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(new File(CollectionManager.file)));
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(FileIO.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(this, bw);

            bw.close();
            System.out.println("Сохранение");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
