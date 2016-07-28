package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.*;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class NewBalanceController implements Initializable {

    @FXML
    private GridPane mainGridPane;

    @FXML
    private Label modelIDLabel;

    @FXML
    private TextField nettoField;

    @FXML
    private TextField bruttoField;

    @FXML
    private ComboBox afaBox;

    @FXML
    private TextField afaValueField;

    @FXML
    private DatePicker createdPicker;

    @FXML
    private ComboBox modelNameBox;

    @FXML
    private ComboBox statusIdBox;

    @FXML
    private CheckBox cashBox;

    @FXML
    private TextField noteField;

    private TextField customAfa;
    private boolean customAfaAdded = false;
    private ComboBox userBox;
    private ComboBox projectBox;
    private LocalDate currentDate;
    private PopOver parent;

    public NewBalanceController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDatePicker();
        initCustomAfaField();
        initAfaBox();
        initStatusBox();
        initUserBox();
        initProjectBox();
        initModelNameBox();
        initTextFields();
    }

    private void initDatePicker() {
        createdPicker.setValue(LocalDate.now());
    }

    private void initTextFields() {
        nettoField.setText("0");
        bruttoField.setText("0");
        afaValueField.setText("0");
        noteField.setText("");

        nettoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (bruttoField.isFocused() || "".equals(nettoField.getText())) return;
            double multi = 1.0 + (((customAfaAdded) ? Double.parseDouble(customAfa.getText()) : Double.parseDouble(afaBox.getSelectionModel().getSelectedItem().toString())) / 100);
            double bruttoValue = (Integer.parseInt(nettoField.getText())) * multi;
            bruttoField.setText(Integer.toString((int) bruttoValue));
            setAfaValueField();
        });

        bruttoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nettoField.isFocused() || "".equals(bruttoField.getText())) return;
            double multi = 1.0 + (((customAfaAdded) ? Double.parseDouble(customAfa.getText()) : Double.parseDouble(afaBox.getSelectionModel().getSelectedItem().toString())) / 100);
            double nettoValue = (Integer.parseInt(bruttoField.getText())) / multi;
            nettoField.setText(Integer.toString((int) nettoValue));
            setAfaValueField();
        });
    }

    private void setAfaValueField() {
        afaValueField.setText(Integer.toString(Integer.parseInt(bruttoField.getText()) - Integer.parseInt(nettoField.getText())));
    }

    private void showUserBox() {
        if (mainGridPane.getChildren().contains(userBox)) return;
        if (mainGridPane.getChildren().contains(projectBox)) {
            mainGridPane.getChildren().removeAll(projectBox);
        }
        mainGridPane.add(userBox, 1, 7);
        statusIdBox.getSelectionModel().select(1);
        modelIDLabel.setText("Alkalmazottak");
    }

    private void showProjectBox() {
        if (mainGridPane.getChildren().contains(projectBox)) return;
        if (mainGridPane.getChildren().contains(userBox)) {
            mainGridPane.getChildren().removeAll(userBox);
        }
        mainGridPane.add(projectBox, 1, 7);
        statusIdBox.getSelectionModel().select(0);
        modelIDLabel.setText("Munkák");
    }

    private void hideBoxes() {
        if (mainGridPane.getChildren().contains(projectBox)) {
            mainGridPane.getChildren().removeAll(projectBox);
        }
        if (mainGridPane.getChildren().contains(userBox)) {
            mainGridPane.getChildren().removeAll(userBox);
        }
        statusIdBox.getSelectionModel().select(3);
        modelIDLabel.setText("");
    }

    private void initUserBox() {
        //Platform.runLater(() -> {
        userBox = new ComboBox();
        userBox.setPrefHeight(36.0);
        userBox.setPrefWidth(200.0);
        GenericDao<UsersEntity> usersDao = DaoManager.getInstance().getUserDao();
        List<UsersEntity> allUsers = usersDao.findAll();
        userBox.setItems(FXCollections.observableArrayList(allUsers));
        userBox.getSelectionModel().selectFirst();
        userBox.setCellFactory(new Callback<ListView<UsersEntity>, ListCell<UsersEntity>>() {
            @Override
            public ListCell<UsersEntity> call(ListView<UsersEntity> param) {
                return new ListCell<UsersEntity>() {
                    @Override
                    public void updateItem(UsersEntity item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setText(item.getName());
                            setGraphic(null);
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        //});
    }

    private void initProjectBox() {
        //Platform.runLater(() -> {
        projectBox = new ComboBox();
        projectBox.setPrefHeight(36.0);
        projectBox.setPrefWidth(200.0);
        GenericDao<ProjectsEntity> projectsDao = DaoManager.getInstance().getProjectsDao();
        List<ProjectsEntity> allProjects = projectsDao.findAll();
        projectBox.setItems(FXCollections.observableArrayList(allProjects));
        projectBox.getSelectionModel().selectFirst();
        projectBox.setCellFactory(new Callback<ListView<ProjectsEntity>, ListCell<ProjectsEntity>>() {
            @Override
            public ListCell<ProjectsEntity> call(ListView<ProjectsEntity> param) {
                return new ListCell<ProjectsEntity>() {
                    @Override
                    public void updateItem(ProjectsEntity item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setText(item.getName());
                            setGraphic(null);
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        //});
    }

    private void initModelNameBox() {
        modelNameBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if ("Alkalmazott".equals(newValue)) {
                    showUserBox();
                } else if ("Munka".equals(newValue)) {
                    showProjectBox();
                } else {
                    hideBoxes();
                }
            }
        });
        modelNameBox.getSelectionModel().select(1);
    }

    private void initStatusBox() {
        //new Thread() {
        //@Override
        //public void run() {
        GenericDao<StatusEntity> statusDao = DaoManager.getInstance().getStatusDao();
        List<StatusEntity> statusList = statusDao.findAll();
        statusIdBox.setItems(FXCollections.observableArrayList(statusList));
        //}
        //}.start();
    }

    private void initCustomAfaField() {
        // new Thread() {
        //@Override
        //public void run() {
        customAfa = new TextField();
        customAfa.prefHeight(36.0);
        customAfa.setPromptText("Egyedi ÁFA");
        customAfa.setText("0");
        //}
        //}.start();
    }

    private void initAfaBox() {
        // new Thread() {
        //@Override
        //public void run() {
        afaBox.getItems().addAll("0", "27", "egyéb");
        afaBox.getSelectionModel().select(1);
        afaBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("egyéb".equals((String) newValue)) {
                if (!customAfaAdded) {
                    mainGridPane.add(customAfa, 2, 3);
                    customAfaAdded = true;
                }
            } else {
                if (customAfaAdded) {
                    mainGridPane.getChildren().removeAll(customAfa);
                    customAfaAdded = false;
                }
            }
        });
        //}
        //}.start();
    }

    @FXML
    private void onSaveAction(ActionEvent event) {
        BalanceEntity balanceEntity = createEntityFromForm();
        GenericDao<BalanceEntity> balanceDao = DaoManager.getInstance().getBalanceDao();
        balanceDao.create(balanceEntity);
        ControllerMediator.getInstance().refreshDailyTableData(createdPicker.getValue());
        onCancelAction(null);
    }

    private BalanceEntity setModelProperties(BalanceEntity balanceEntity) {
        String modelName = (String) modelNameBox.getSelectionModel().getSelectedItem();
        switch (modelName) {
            case "Alkalmazott":
                balanceEntity.setModelName("user");
                balanceEntity.setModelId(((PersistentEntity) userBox.getSelectionModel().getSelectedItem()).getId());
                break;
            case "Munka":
                balanceEntity.setModelName("project");
                balanceEntity.setModelId(((PersistentEntity) projectBox.getSelectionModel().getSelectedItem()).getId());
                break;
            case "Egyéb":
                balanceEntity.setModelName(null);
                balanceEntity.setModelId(null);
                break;
            default:
                System.out.println("baj van");
                break;
        }
        return balanceEntity;
    }


    private BalanceEntity createEntityFromForm() {
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setNetto(Integer.parseInt(nettoField.getText()));
        balanceEntity.setBrutto(Integer.parseInt(bruttoField.getText()));
        balanceEntity.setAfa(Integer.parseInt((customAfaAdded) ? customAfa.getText() : afaBox.getSelectionModel().getSelectedItem().toString()));
        balanceEntity.setAfaValue(Integer.parseInt(afaValueField.getText()));
        balanceEntity.setCreated(Date.valueOf(createdPicker.getValue()));
        balanceEntity.setStatusId(((StatusEntity) statusIdBox.getSelectionModel().getSelectedItem()).getId());
        balanceEntity.setCash(cashBox.isSelected());
        balanceEntity.setNote(noteField.getText());
        balanceEntity = setModelProperties(balanceEntity);
        return balanceEntity;
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        parent.hide();
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    void setParent(PopOver parent) {
        this.parent = parent;
    }
}
