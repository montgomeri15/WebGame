import database.Constructor;
import database.DbManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Launcher extends Application {
    //DbManager db = new DbManager();
    //public static Connection connection;

    Label labelUser = new Label("Имя:");
    Label labelPass = new Label("Пароль:");
    Label labelSpace = new Label("     ");
    TextField textUser = new TextField();
    PasswordField textPass = new PasswordField();
    Button buttonStart = new Button("Start");
    Button buttonRegistration = new Button("Sign Up");
    Label labelErrorMessage = new Label("");

    //Некоторые картинки, которые мы вызываем в дальнейшем
    ImageView imgHPPl1 = new ImageView(new File("src/main/java/images/HP/DuoLonHP.png").toURI().toString());
    ImageView imgHPPl2 = new ImageView(new File("src/main/java/images/HP/SaikiHP.png").toURI().toString());
    ImageView imgPl1 = new ImageView(new File("src/main/java/images/DuoLon.gif").toURI().toString());
    ImageView imgPl2 = new ImageView(new File("src/main/java/images/Saiki.gif").toURI().toString());
    ImageView imgSpace = new ImageView(new File("src/main/java/images/space.png").toURI().toString());

    //Панели, которые нам потребуются в нескольких методах
    GridPane paneHPPl1;
    GridPane paneHPPl2;
    GridPane paneFightPl1;
    GridPane paneFightPl2;
    GridPane paneAll;

    //Для радиобаттонов
    String stringHead = "Голова";
    String stringBody = "Корпус";
    String stringLegs = "Ноги";

    double hp, damage, percentDamage, percentHp = 100;  //Для определения % снятия ХП

    @Override
    public void start(Stage primaryStage) throws Exception {

        /** Блок авторизации */
        GridPane paneFields = new GridPane();
        paneFields.setAlignment(Pos.CENTER);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> Platform.exit());  //Аналог setDefaultCloseOperation() в Swing
        paneFields.setHgap(10);
        paneFields.setVgap(10);
        paneFields.setPadding(new Insets(20, 20, 20, 20));
        paneFields.add(labelUser, 0, 0);
        paneFields.add(textUser, 1, 0);
        paneFields.add(labelPass, 0, 1);
        paneFields.add(textPass, 1, 1);

        GridPane paneButton = new GridPane();
        paneButton.setAlignment(Pos.CENTER);
        paneButton.add(buttonStart,0, 0);
        paneButton.add(labelSpace, 1, 0);
        paneButton.add(buttonRegistration, 2, 0);

        GridPane paneErrorMessage = new GridPane();
        paneErrorMessage.setAlignment(Pos.CENTER);
        paneErrorMessage.add(labelErrorMessage, 0, 0);

        GridPane paneAll = new GridPane();
        paneAll.setAlignment(Pos.CENTER);
        paneAll.add(paneFields, 0, 0);
        paneAll.add(paneButton, 0, 1);
        paneAll.add(paneErrorMessage, 0, 2);

        Scene scene = new Scene(paneAll, 290, 170);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        //connection = db.getConnection();

        buttonStart.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                String login = textUser.getText();
                String password = textPass.getText();

                /*List<Constructor> list = null;
                try {
                    list = db.readTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i<list.size(); i++){
                    String dbNames = list.get(i).getName().toString();
                    String dbPasswords = list.get(i).getPass().toString();*/

                if (login.equals("1") && password.equals("1")){
                    primaryStage.close();
                    fightFrame();
                } else{
                    textPass.setText(null);
                    labelErrorMessage.setTextFill(Color.web("#DC143C"));
                    labelErrorMessage.setText("Неверный логин или пароль");
                }
            }
            // }
        });
    }
    public void fightFrame() {

        /** Блок окна игры */
        Stage primaryStageFight = new Stage();

        String stringAttack = "Атака";
        String stringShield = "Защита";
        String stringSpace = " ";
        String stringSpaceHoriz = "                                  ";

        Label labelAttackPl1 = new Label(stringAttack);
        Label labelShieldPl1 = new Label(stringShield);
        Label labelAttackPl2 = new Label(stringAttack);
        Label labelShieldPl2 = new Label(stringShield);
        Label labelSpaceAttackPl1 = new Label(stringSpace);
        Label labelSpaceAttackPl2 = new Label(stringSpace);
        Label labelSpaceShieldPl1 = new Label(stringSpace);
        Label labelSpaceShieldPl2 = new Label(stringSpace);
        Label labelSpaceBetweenVert = new Label(stringSpace);
        Label labelSpaceBetweenHoriz = new Label(stringSpaceHoriz);

        //4 группы радибаттонов
        ToggleGroup radioGroupAttackPl1 = new ToggleGroup();
        RadioButton radioHeadAttackPl1 = new RadioButton(stringHead);
        RadioButton radioBodyAttackPl1 = new RadioButton(stringBody);
        RadioButton radioLegsAttackPl1 = new RadioButton(stringLegs);
        radioHeadAttackPl1.setToggleGroup(radioGroupAttackPl1);
        radioBodyAttackPl1.setToggleGroup(radioGroupAttackPl1);
        radioLegsAttackPl1.setToggleGroup(radioGroupAttackPl1);

        ToggleGroup radioGroupAttackPl2 = new ToggleGroup();
        RadioButton radioHeadAttackPl2 = new RadioButton(stringHead);
        RadioButton radioBodyAttackPl2 = new RadioButton(stringBody);
        RadioButton radioLegsAttackPl2 = new RadioButton(stringLegs);
        radioHeadAttackPl2.setToggleGroup(radioGroupAttackPl2);
        radioBodyAttackPl2.setToggleGroup(radioGroupAttackPl2);
        radioLegsAttackPl2.setToggleGroup(radioGroupAttackPl2);

        ToggleGroup radioGroupShieldPl1 = new ToggleGroup();
        RadioButton radioHeadShieldPl1 = new RadioButton(stringHead);
        RadioButton radioBodyShieldPl1 = new RadioButton(stringBody);
        RadioButton radioLegsShieldPl1 = new RadioButton(stringLegs);
        radioHeadShieldPl1.setToggleGroup(radioGroupShieldPl1);
        radioBodyShieldPl1.setToggleGroup(radioGroupShieldPl1);
        radioLegsShieldPl1.setToggleGroup(radioGroupShieldPl1);

        ToggleGroup radioGroupShieldPl2 = new ToggleGroup();
        RadioButton radioHeadShieldPl2 = new RadioButton(stringHead);
        RadioButton radioBodyShieldPl2 = new RadioButton(stringBody);
        RadioButton radioLegsShieldPl2 = new RadioButton(stringLegs);
        radioHeadShieldPl2.setToggleGroup(radioGroupShieldPl2);
        radioBodyShieldPl2.setToggleGroup(radioGroupShieldPl2);
        radioLegsShieldPl2.setToggleGroup(radioGroupShieldPl2);

        //4 панели для радибаттонов
        GridPane paneRadioAttackPl1 = new GridPane();
        paneRadioAttackPl1.setStyle("-fx-border-color: gray;");
        paneRadioAttackPl1.setPrefWidth(150);
        paneRadioAttackPl1.setPrefHeight(110);
        paneRadioAttackPl1.setAlignment(Pos.CENTER);
        paneRadioAttackPl1.add(labelAttackPl1, 0, 0);
        paneRadioAttackPl1.add(labelSpaceAttackPl1, 0, 1);
        paneRadioAttackPl1.add(radioHeadAttackPl1, 0, 2);
        paneRadioAttackPl1.add(radioBodyAttackPl1, 0, 3);
        paneRadioAttackPl1.add(radioLegsAttackPl1, 0, 4);

        GridPane paneRadioAttackPl2 = new GridPane();
        paneRadioAttackPl2.setStyle("-fx-border-color: gray;");
        paneRadioAttackPl2.setPrefWidth(150);
        paneRadioAttackPl2.setPrefHeight(110);
        paneRadioAttackPl2.setAlignment(Pos.CENTER);
        paneRadioAttackPl2.add(labelAttackPl2, 0, 0);
        paneRadioAttackPl2.add(labelSpaceAttackPl2, 0, 1);
        paneRadioAttackPl2.add(radioHeadAttackPl2, 0, 2);
        paneRadioAttackPl2.add(radioBodyAttackPl2, 0, 3);
        paneRadioAttackPl2.add(radioLegsAttackPl2, 0, 4);

        GridPane paneRadioShieldPl1 = new GridPane();
        paneRadioShieldPl1.setStyle("-fx-border-color: gray;");
        paneRadioShieldPl1.setPrefWidth(150);
        paneRadioShieldPl1.setPrefHeight(110);
        paneRadioShieldPl1.setAlignment(Pos.CENTER);
        paneRadioShieldPl1.add(labelShieldPl1, 0, 0);
        paneRadioShieldPl1.add(labelSpaceShieldPl1, 0, 1);
        paneRadioShieldPl1.add(radioHeadShieldPl1, 0, 2);
        paneRadioShieldPl1.add(radioBodyShieldPl1, 0, 3);
        paneRadioShieldPl1.add(radioLegsShieldPl1, 0, 4);

        GridPane paneRadioShieldPl2 = new GridPane();
        paneRadioShieldPl2.setStyle("-fx-border-color: gray;");
        paneRadioShieldPl2.setPrefWidth(150);
        paneRadioShieldPl2.setPrefHeight(110);
        paneRadioShieldPl2.setAlignment(Pos.CENTER);
        paneRadioShieldPl2.add(labelShieldPl2, 0, 0);
        paneRadioShieldPl2.add(labelSpaceShieldPl2, 0, 1);
        paneRadioShieldPl2.add(radioHeadShieldPl2, 0, 2);
        paneRadioShieldPl2.add(radioBodyShieldPl2, 0, 3);
        paneRadioShieldPl2.add(radioLegsShieldPl2, 0, 4);

        //Общая панель для радибаттонов
        GridPane paneAllRadioPanes = new GridPane();
        paneRadioShieldPl2.setAlignment(Pos.CENTER);
        paneAllRadioPanes.add(paneRadioAttackPl1, 0, 0);
        paneAllRadioPanes.add(labelSpaceBetweenHoriz, 1, 0);
        paneAllRadioPanes.add(paneRadioAttackPl2, 2, 0);
        paneAllRadioPanes.add(labelSpaceBetweenVert, 0, 1);
        paneAllRadioPanes.add(paneRadioShieldPl1, 0, 2);
        paneAllRadioPanes.add(paneRadioShieldPl2, 2, 2);

        //Панели иконок персонажей со здоровьем
        paneHPPl1 = new GridPane();
        paneHPPl1.setAlignment(Pos.CENTER);
        paneHPPl1.add(imgHPPl1, 0, 0);
        paneHPPl2 = new GridPane();
        paneHPPl2.setAlignment(Pos.CENTER);
        paneHPPl2.add(imgHPPl2, 0, 0);

        //Основная панель боя (сами персонажи)
        paneFightPl1 = new GridPane();
        paneFightPl1.setAlignment(Pos.CENTER);
        paneFightPl1.add(imgPl1, 0, 0);
        paneFightPl2 = new GridPane();
        paneFightPl2.setAlignment(Pos.CENTER);
        paneFightPl2.add(imgPl2, 0, 0);

        Button buttonGo = new Button("Go!");

        GridPane paneGo = new GridPane();
        paneGo.setAlignment(Pos.CENTER);
        paneGo.add(buttonGo, 0, 0);

        //Все панели вместе
        paneAll = new GridPane();
        paneAll.setAlignment(Pos.CENTER);
        paneAll.add(paneHPPl1, 0, 0);
        paneAll.add(paneHPPl2, 2, 0);
        paneAll.add(imgSpace, 1, 1);
        paneAll.add(paneFightPl1, 0, 2);
        paneAll.add(paneAllRadioPanes, 1, 2);
        paneAll.add(paneFightPl2, 2, 2);
        paneAll.add(paneGo, 1, 3);

        //Устанавливаем фон
        BackgroundImage myBI= new BackgroundImage(new Image(new File("src/main/java/images/background.jpg").toURI().toString()),  //Фон
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        paneAll.setBackground(new Background(myBI));

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();  //Определяем разрешение экрана
        int screenWidth = (int) primaryScreenBounds.getWidth();  //Присваиваем ширину
        int screenHeight = (int) primaryScreenBounds.getHeight();  //Присваиваем высоту
        primaryStageFight.setWidth(screenWidth);  //Задаем ширину
        primaryStageFight.setHeight(screenHeight);  //Задаем высоту

        Scene scene = new Scene(paneAll, screenWidth, screenHeight);  //Помещаем все на сцену и задаем ее размеры

        primaryStageFight.setTitle("Game");
        primaryStageFight.setScene(scene);
        primaryStageFight.show();  //Видимость

        //Кнопка-тест, которая уменьшает здоровье и меняет картинки шкалы
        buttonGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                percent();
                if (percentHp<=83.3 && percentHp>66.6) {
                    imgHPPl1 = new ImageView(new File("src/main/java/images/HP/imgHPLimePl1.png").toURI().toString());
                } else if (percentHp<=66.6 && percentHp>49.9){
                    imgHPPl1.setImage(Image.impl_fromPlatformImage(null));
                    imgHPPl1 = new ImageView(new File("src/main/java/images/HP/imgHPYellowPl1.png").toURI().toString());
                } else if (percentHp<=49.9 && percentHp>33.2){
                    imgHPPl1.setImage(Image.impl_fromPlatformImage(null));
                    imgHPPl1 = new ImageView(new File("src/main/java/images/HP/imgHPOrangePl1.png").toURI().toString());
                } else if (percentHp<=33.2 && percentHp>16.5){
                    imgHPPl1.setImage(Image.impl_fromPlatformImage(null));
                    imgHPPl1 = new ImageView(new File("src/main/java/images/HP/imgHPRedPl1.png").toURI().toString());
                } else if (percentHp<=16.5 && percentHp>=0.1){
                    imgHPPl1.setImage(Image.impl_fromPlatformImage(null));
                    imgHPPl1 = new ImageView(new File("src/main/java/images/HP/imgHPDeepRedPl1.png").toURI().toString());
                } else if (percentHp<0.1){
                    imgHPPl1.setImage(Image.impl_fromPlatformImage(null));
                    imgHPPl1 = new ImageView(new File("src/main/java/images/HP/imgHPBlackPl1.png").toURI().toString());
                    ImageView imgGameOver = new ImageView(new File("src/main/java/images/spaceGameOver.png").toURI().toString());
                    paneAll.add(imgGameOver, 1, 1);  //Добавляем картинку Game Over
                }
                paneHPPl1.add(imgHPPl1, 0, 0);  //Добавляем нужный цвет шкалы в соответствии с условием if
            }
        });
    }
    public void percent(){

        /** Блок определения процента урона */
        hp = (int) (1000 + Math.random() * 500);
        damage = (int) (40 + Math.random() * 90);
        System.out.println("Здоровье = "+hp);
        System.out.println("Дамаг по нам = "+damage);
        percentDamage = (damage*100)/hp;
        System.out.println("% дамага = "+percentDamage);
        percentHp = percentHp-percentDamage;
        System.out.println("% оставшегося HP = "+percentHp+"\n");
    }
}
