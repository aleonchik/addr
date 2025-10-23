package fx.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

/**
 * Project: addr
 * Package: fx.model
 *
 * Вспомогательный класс для обертывания списка адресатов.
 * Используется для сохраниения списка адресатов в XML
 *
 * <p>
 * User: alexey
 * Date: вс 19 окт. 2025
 */

@XmlRootElement(name = "persons")
public class PersonListWrapper {
    private List<Person> persons;

    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
