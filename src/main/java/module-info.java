module fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires jakarta.xml.bind;

    opens fx;
    opens fx.view;
    opens fx.model;
}
