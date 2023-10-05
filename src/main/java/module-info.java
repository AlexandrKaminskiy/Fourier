module com.example.fourier {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fourier to javafx.fxml;
    exports com.example.fourier;
}
