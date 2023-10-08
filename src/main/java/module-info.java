module com.example.fourier {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens com.example.fourier.model to javafx.base;
    opens com.example.fourier to javafx.fxml;
    exports com.example.fourier;
}
