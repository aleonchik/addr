package fx;

import fx.model.Person;
import fx.model.PersonListWrapper;
import fx.view.PersonEditDialogController;
import fx.view.PersonOverviewController;
import fx.view.RootLayoutController;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.prefs.Preferences;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Конструктор
     */
    public Main() {
        //  В качестве образца добавляем некоторые данные

        /*personData.add(new Person("Алексей", "Леончик", "Уссурийск",
                "Краснознаменная 155 - 6", 692503, LocalDate.of(1971, 4, 16)));
        personData.add(new Person("Ольга", "Чистова", "Уссурийск",
                "Краснознаменная 155 - 6", 692503, LocalDate.of(1960, 5, 17)));*/

        /*personData.add(new Person("Алексей", "Леончик", "Уссурийск",
                "Краснознаменная 155 - 6", 692503, "16.04.1971"));
        personData.add(new Person("Ольга", "Чистова", "Уссурийск",
                "Краснознаменная 155 - 6", 692503, "17.05.1960"));*/
    }

    /**
     * Возвращает данные в виде наблюдаемого списка адресатов
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showPersonalView();
    }

    /**
     * Инициализирует корневой макет и пытается загрузить последний открытый
     * файл с адресатами
     */
    public void initRootLayout() {
        try {
            //Загружаем корневой макет из FXML файла
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/RootLayout.fxml"));
            // redundant (BorderPane)
            // rootLayout = (BorderPane) loader.load();
            rootLayout = loader.load();

            // Иконка приложения
            InputStream iconStream = getClass().getResourceAsStream("/img/pencl.png");

            if (iconStream != null) {
                Image image = new Image(iconStream);
                primaryStage.getIcons().add(image);
            }

            // Отображаем сцену содержащую корневой макет
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Даем контроллеру доступ к главному приложению
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Пытается загрузить последний файл с адресатами
        File file = getPersonFilePath();

        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Показывает в корневом макете сведения об адрессатах
     */
    public void showPersonalView() {
        try {
            // Загружаем сведения об адресатах
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/PersonOverview.fxml"));
            // redundant (AnchorPane)
            // AnchorPane personOverview  = (AnchorPane) loader.load();
            AnchorPane personOverview  = loader.load();
            // Помещаем сведения об алресатах в центр корневого макета
            rootLayout.setCenter(personOverview);
            // Даем контроллеру доступ к головному приложению
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает главную сцену
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Открывает диалоговое окно для изменения деталей указанного адресата
     * Если пользователь кликнул ОК, то изменения созраняются в предоставленном
     * объекте адресата и возвращается значение true
     *
     * @param person - объект адреса, который надо изменить
     * @return true, если пользователь кликнул ОК, в противном случае false
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Загружаем fxml-файл и создаем новую сцену для всплывающего диалогового окна
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/PersonEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Создаем диалоговое окно
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование данных");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаем адресата в контроллер
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Отображаем диалоговое окно и ждем, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void exitFromApp() {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Возвращает preference файла адресатов, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной
     * операционной системы. Если preference не был найден, то возвращается null
     *
     * @return file
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);

        if(filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Задает путь к текущему заданному файлу. Этот путь сохраняется в реестре,
     * специфичном для конкретной операционной системы
     *
     * @param file - файл или null, чтобы удалить путь
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);

        if (file != null) {
            prefs.put("filePath", file.getPath());
            // обновляем заглавие сцены
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");
            // обновляем заглавие сцены
            primaryStage.setTitle("AddressApp");
        }
    }

    /**
     * Загружает информацию об адресатах из указанного файла.
     * Текущая информация об адресатах будет заменена
     *
     * @param file - файл с данными
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            // Чтение XML из файла и демаршализация
            // TODO: не работает unMarshal
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
            personData.clear();
            personData.addAll(wrapper.getPersons());
//            personData.add(new Person("Имя", "Фамилия", "Город", "Улица", 123456,
//                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            // Сохраним путь к файлу
            setPersonFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не могу загрузить данные");
            alert.setContentText("Не могу заагрузить данные из файла:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    /**
     * Сохраняет текущую информацию об адресатах в указанном файле
     *
     * @param file - файлс данными
     */
    public void savePersonDataToFile(File file) {
        // TODO не сохраняется дата в XML
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Оьертываем наши данные об адресатах
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);
            // Маршаллируем и сохраняем XML в файл
            m.marshal(wrapper, file);
            // Сохраняеям путь к файлу
            setPersonFilePath(file);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не могу записать данные");
            alert.setContentText("Не могу записать данные в файл:\n" + file.getPath());
            alert.showAndWait();
        }
    }
}