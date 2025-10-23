package fx.model;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Project: addr
 * Package: fx.model
 * <p>
 * User: alexey
 * Date: вс 05 окт. 2025
 */
public class Person {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> birthday;

    public Person() {
        this("", "", "", "", 0, LocalDate.now());
    }

    /**
     * Конструктор по умолчанию
     */
    public Person(StringProperty firstName, StringProperty lastName,
                  StringProperty street, IntegerProperty postalCode,
                  StringProperty city, ObjectProperty<LocalDate> birthday) {
//        this(null, null);
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.birthday = birthday;
    }

    public Person(String firstName, String lastName, String cityName, String streetName,
                  Integer postalCode, LocalDate birthday) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.city = new SimpleStringProperty(cityName);
        this.street = new SimpleStringProperty(streetName);
        this.postalCode = new SimpleIntegerProperty(postalCode);
        this.birthday = new SimpleObjectProperty<LocalDate>(birthday);
    }

    /**
     * Конструктор с некоторыми начальными данными
     *
//     * @param firstName Имя
//     * @param lastName Фамилия
     */
//    public Person(String firstName, String lastName) {
//        this.firstName = new SimpleStringProperty(firstName);
//        this.lastName = new SimpleStringProperty(lastName);

        // Какие-то фиктивные начальные данные для удобства тестирования
//        this.street = new SimpleStringProperty("Краснознаменная 155 -6");
//        this.postalCode = new SimpleIntegerProperty(692503);
//        this.city = new SimpleStringProperty("Уссурийск");
//        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1971, 4, 16));
//    }

    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String firstName) { this.firstName.set(firstName); }
    public StringProperty firstNameProperty() { return firstName; }

    public String getLastName() { return lastName.get(); }
    public void setLastName(String lastName) { this.lastName.set(lastName); }
    public StringProperty lastNameProperty() { return lastName; }

    public String getStreet() { return street.get(); }
    public void setStreet(String street) { this.street.set(street); }
    public StringProperty streetProperty() { return street; }

    public int getPostalCode() { return postalCode.get(); }
    public void setPostalCode(int postalCode) { this.postalCode.set(postalCode); }
    public IntegerProperty postalCodeProperty() { return postalCode; }

    public String getCity() { return city.get(); }
    public void setCity(String city) { this.city.set(city); }
    public StringProperty cityProperty() { return city; }

    public LocalDate getBirthday() { return birthday.get(); }
    public void setBirthday(LocalDate birthday) { this.birthday.set(birthday); }
    public ObjectProperty<LocalDate> birthdayProperty() { return birthday; }
}
