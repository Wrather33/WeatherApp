package com.example.weatherapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import kotlin.properties.ObservableProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class WeatherApp extends Application {
    public static Object node;
    public static String type = "°F";
    public static CurrentWeather currentWeather;
    public static Label Lfeels;
    public static Label Ltemp;
    public static TreeMap<String, Country> countries = new TreeMap<>();
    @Override
    public void start(Stage stage) throws IOException {
        Locale langEnglish  = new Locale.Builder().setLanguage("en").build();
        Label label = new Label("Weather app");
        Button button = new Button(type);
        Label choice = new Label();
        ObservableList<String> countrySet = FXCollections.observableArrayList(countries.keySet());
        ComboBox<String> comboBox = new ComboBox<>(countrySet);
        comboBox.setValue(Locale.getDefault().getDisplayCountry(langEnglish));
        ObservableList<City> cities = FXCollections.observableArrayList(countries.get(comboBox.getValue()).getCities());
        ComboBox<City> comboCities = new ComboBox<>(cities);
        GridPane gridPane = new GridPane();
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.ALWAYS);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints1, columnConstraints2);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().addAll(rowConstraints, rowConstraints1);
        gridPane.setGridLinesVisible(true);
        VBox vBox = new VBox(label, button, comboBox, comboCities, choice, gridPane);
        ChangeListener<String> gradus = (observable, oldValue, newValue) -> {
            if(currentWeather != null && Ltemp != null && Lfeels != null) {
                if (newValue.equals("°F")) {
                    Ltemp.setText(String.valueOf(currentWeather.getTemp()) + "°C");
                    Lfeels.setText(String.valueOf(currentWeather.getFeels()) + "°C");
                } else {
                    Ltemp.setText(String.valueOf(currentWeather.getFtemp()) + "°F");
                    Lfeels.setText(String.valueOf(currentWeather.getFlike()) + "°F");
                }
            }
        };
        button.textProperty().addListener(gradus);
        button.setOnAction(event -> {
                if (type.equals("°C")) {
                    type = "°F";
                    button.setText(type);
                } else {
                    type = "°C";
                    button.setText(type);
                }
            });
        vBox.setAlignment(Pos.TOP_CENTER);
        comboBox.setOnMouseClicked(event -> {
            node = event.getSource();
        });
        comboCities.setOnMouseClicked(event -> {
            node = event.getSource();
        });
        comboBox.setOnAction(event -> {
            if(node.equals(comboBox)) {
                comboCities.getItems().clear();
                comboCities.setItems(FXCollections.observableArrayList(countries.get(comboBox.getValue()).getCities()));
            }
        });
        comboCities.setOnAction(event -> {
            if(node.equals(comboCities)) {
                Document document = request(comboCities.getValue(), choice);
                if (document != null){
                    gridPane.getChildren().clear();
                    gridPane.setGridLinesVisible(true);
                    Element element = (Element) document.getElementsByTagName("current").item(0);
                    Element condition = (Element) element.getElementsByTagName("condition").item(0);
                    String weather = condition.getElementsByTagName("text").item(0).getTextContent();
                    double ftemp = Double.parseDouble(element.getElementsByTagName("temp_f").item(0).getTextContent());
                    double flike = Double.parseDouble(element.getElementsByTagName("feelslike_f").item(0).getTextContent());
                    double temp = Double.parseDouble(element.getElementsByTagName("temp_c").item(0).getTextContent());
                    double windSpeed = Double.parseDouble(element.getElementsByTagName("wind_kph").item(0).getTextContent())/3.6;
                    int windDegree = Integer.parseInt(element.getElementsByTagName("wind_degree").item(0).getTextContent());
                    String dir = element.getElementsByTagName("wind_dir").item(0).getTextContent();
                    int day = Integer.parseInt(element.getElementsByTagName("is_day").item(0).getTextContent());
                    double pressure = Double.parseDouble(element.getElementsByTagName("pressure_mb").item(0).getTextContent()) * 0.750062;
                    int humidity = Integer.parseInt(element.getElementsByTagName("humidity").item(0).getTextContent());
                    int cloud = Integer.parseInt(element.getElementsByTagName("cloud").item(0).getTextContent());
                    String icon = condition.getElementsByTagName("icon").item(0).getTextContent();
                    double feels = Double.parseDouble(element.getElementsByTagName("feelslike_c").item(0).getTextContent());
                    double gust = Double.parseDouble(element.getElementsByTagName("gust_kph").item(0).getTextContent())/3.6;
                    double precip_mm = Double.parseDouble(element.getElementsByTagName("precip_mm").item(0).getTextContent());
                    currentWeather = new CurrentWeather(icon,weather, temp, feels, windSpeed, gust, humidity,cloud, pressure,windDegree,
                            dir, day, precip_mm, ftemp, flike);
                    URL url = null;
                    try {
                        url = new URL("http:"+currentWeather.getIcon());
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    ImageView imageView = new ImageView();
                    try {
                        imageView.setImage(new Image(url.openStream()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Label Lweather = new Label("Weather: "+currentWeather.getWeather());
                    if(type.equals("°F")){
                        Ltemp = new Label("Temp: "+String.valueOf(currentWeather.getTemp())+"°C");
                        Lfeels = new Label("Feels: "+String.valueOf(currentWeather.getFeels())+"°C");
                    }
                    else {
                        Ltemp = new Label("Temp: "+String.valueOf(currentWeather.getFtemp())+"°F");
                        Lfeels = new Label("Feels: "+String.valueOf(currentWeather.getFlike())+"°F");
                    }
                    Label LwindSpeed = new Label("Wind speed: "+String.format("%.2f m/s", currentWeather.getWind()));
                    Label Lgust = new Label("Gust speed: "+String.format("%.2f m/s", currentWeather.getGust()));
                    Label Lhumidity = new Label("Humidity: "+String.valueOf(currentWeather.getHumidity()));
                    Label Lcloud = new Label("Cloudy: "+String.valueOf(currentWeather.getCloud()));
                    Label Lpres = new Label("Pressure: "+String.format("%.2f", currentWeather.getPressure()));
                    Label LwindDegree = new Label("Wind Degree: "+String.valueOf(currentWeather.getWindDegree()));
                    Label Ldir = new Label("Wind Direction: "+String.valueOf(currentWeather.getDirection()));
                    Label time = new Label();
                    if(currentWeather.getIsDay() == 1){
                        time.setText("time: day");
                    }
                    else {
                        time.setText("time: night");
                    }
                    Label Lprecip = new Label("Precip: "+currentWeather.getPrecipitation());
                    gridPane.add(Lweather, 0, 0);
                    gridPane.add(imageView, 1, 0);
                    VBox vBox1 = new VBox(Ltemp, Lfeels);
                    vBox1.setAlignment(Pos.CENTER);
                    gridPane.add(vBox1, 2, 0);
                    VBox vBox2 = new VBox(LwindSpeed, LwindDegree, Lgust, Ldir);
                    vBox2.setAlignment(Pos.CENTER);
                    gridPane.add(vBox2, 0, 1);
                    VBox vBox3 = new VBox(Lhumidity, Lcloud, Lpres, Lprecip);
                    vBox3.setAlignment(Pos.CENTER);
                    Element Country = (Element) document.getElementsByTagName("location").item(0);
                    VBox vBox4 = new VBox(new Label(comboBox.getValue()), new Label(comboCities.getValue().getCity()), time);
                    gridPane.add(vBox3, 1, 1);
                    gridPane.add(vBox4, 2, 1);

                }
            }
        });
        Scene scene = new Scene(vBox, 320, 240);
        stage.setTitle("Weather App");
        stage.setScene(scene);
        stage.show();
    }
    public static Document request(City city, Label label) {
        String name = city.getCity();
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(String.format("http://api.weatherapi.com/v1/current.xml?key=6266b3d6881d40e0a4c125839231610&q=%s,%s", city.getLatitude(), city.getLongitude()));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            name = "no data";
        }
        String finalName = name;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText(finalName);
            }
        });
        return document;

    }
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("https://raw.githubusercontent.com/dr5hn/countries-states-cities-database/master/xml/countries%2Bcities.xml");
        for (int i = 0; i < document.getElementsByTagName("country_city").getLength(); i++) {
            Element node = (Element) document.getElementsByTagName("country_city").item(i);
            List<City> list = new ArrayList<>();
            if(node.getElementsByTagName("cities").item(0).hasChildNodes()) {
                for (int j = 0; j < node.getElementsByTagName("cities").getLength(); j++) {
                    Element element = (Element) node.getElementsByTagName("cities").item(j);
                    list.add(new City(element.getElementsByTagName("name").item(0).getTextContent(), element.getElementsByTagName("latitude").item(0).getTextContent(),
                            element.getElementsByTagName("longitude").item(0).getTextContent()));
                }
            }
            else {
                Element element = (Element) node.getElementsByTagName("capital").item(0);
                list.add(new City(element.getTextContent(), node.getElementsByTagName("latitude").item(0).getTextContent(),
                        node.getElementsByTagName("longitude").item(0).getTextContent()));
            }
            if(list.size() > 1) {
                list.sort((Comparator.comparing(City::getCity)));
            }
            countries.put(node.getElementsByTagName("name").item(0).getTextContent(), new Country(node.getElementsByTagName("name").item(0).getTextContent(), list));

        }
        launch();
    }

}