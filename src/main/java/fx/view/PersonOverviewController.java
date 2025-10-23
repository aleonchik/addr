package fx.view;

import fx.Main;
import fx.model.Person;
import fx.util.DateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Project: addr
 * Package: fx.view
 * <p>
 * User: alexey
 * Date: вс 05 окт. 2025
 */
public class PersonOverviewController {
    @FXML private TableView<Person> personTable;
    @FXML private TableColumn<Person, String> firstNameColumn;
    @FXML private TableColumn<Person, String> lastNameColumn;

    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label streetLabel;
    @FXML private Label postalCodeLabel;
    @FXML private Label cityLabel;
    @FXML private Label birthdayLabel;
    // Ссылка на главное приложение
    private Main mainApp;

    /**
     * Конструктор
     * Конструктор вызывается раньше метода initialize()
     */
    public PersonOverviewController() {

    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как FXML-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы адресатов с двумя столбцами
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Очистка дополнительной информации об адресате
        showPersonDetail(null);

        // Слушаем изменение выбора и при изменении отображаем
        // дополнительнуя информацию об адресате
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetail(newValue)
        );
    }

    /**
     * Вызывается главным приложением, которое дает на себы ссылку
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Добавление в таблицу данных из наблюдаемого списка
        personTable.setItems(mainApp.getPersonData());
    }

    /**
     * Заполняет все текстовые поля, отображая подробности об адресате.
     * Если указанный адресат = null, то все текстовые поля очищаются
     *
     * @param person - адресат типа Person или null
     */
    private void showPersonDetail(Person person) {
        if (person != null) {
            // Заполняем метки информацией из объекта person
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Если Person = null, убираем весь текст
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке удаления
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Ничего не выбрано
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Не выбран адрес");
            alert.setContentText("Пожалуйста выберите запись в таблице");

            alert.showAndWait();
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке New
     * Открывает диалоговое окно с дополнительной информацией нового адресата
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);

        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке Edit...
     * Открывает диалоговое окно для изменения выбранного адресата
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetail(selectedPerson);
            }
        } else {
            // Ничего не выбрано
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Не выбран адресат");
            alert.setContentText("Выберите адресата в таблице");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleExit(ActionEvent actionEvent) {
        mainApp.exitFromApp();
    }
}
