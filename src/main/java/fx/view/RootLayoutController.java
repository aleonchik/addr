package fx.view;

import fx.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Project: addr
 * Package: fx.view
 *
 * Контроллер для корневого макета. Корневой макет представляет базовый
 * макет приложения, содержащий строку меню и место, где будут размещены
 * остальные элементы JavaFX
 *
 * <p>
 * User: alexey
 * Date: вс 19 окт. 2025
 */
public class RootLayoutController {
    @FXML private MenuItem itemNew;
    @FXML private MenuItem itemOpen;
    @FXML private MenuItem itemSave;
    @FXML private MenuItem itemSaveAs;

    // Ссылка на главное приложение
    private Main mainApp;

    /**
     * Вызывается главным приложением, чтобы оставить ссылку на сомого себя
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Создает пустую адресную книгу
     */
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать адресную книгу для загрузки
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        // Задает фильтр пасширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * Сохраняет файл адресатов который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "Сохранить как ..."
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();

        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Открывает FileChooser, чтобы пользователь имел фозможность
     * выбрать файл, куда будут сохранеты данные
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();
        // Задаем фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Убедимся в корректности расширения
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }

            mainApp.savePersonDataToFile(file);
        }
    }

    /**
     * Открывает диалоговое окно О программе
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("О программе");
        alert.setContentText("Автор: Marko Jakob\nhttps://code.makery.ch/ru/library/javafx-tutorial/");
        alert.showAndWait();
    }

    /**
     * Закрывает приложение
     */
    @FXML
    private void handleExit() {
        mainApp.exitFromApp();
    }
}
