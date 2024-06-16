module fuzzy.output {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens io.sabitovka.donntu to javafx.fxml;
    exports io.sabitovka.donntu;
    exports io.sabitovka.donntu.controllers;
    exports io.sabitovka.donntu.fuzzy;
    opens io.sabitovka.donntu.controllers to javafx.fxml;
}
