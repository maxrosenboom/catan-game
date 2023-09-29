module com.example.aaaa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.aaaa to javafx.fxml;
    exports com.example.aaaa;
}