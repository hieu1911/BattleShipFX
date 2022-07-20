module com.example.battleshipfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.battleshipfx to javafx.fxml;
    exports com.example.battleshipfx;

    opens com.example.battleshipfx.objectManager;
    exports com.example.battleshipfx.objectManager;

    opens com.example.battleshipfx.helpz;
    exports com.example.battleshipfx.helpz;

    opens com.example.battleshipfx.object;
    exports com.example.battleshipfx.object;

    exports com.example.battleshipfx.controller;
    opens com.example.battleshipfx.controller to javafx.fxml;
}