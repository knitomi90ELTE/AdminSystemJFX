package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.*;
import hu.kniznertamas.adminsystem.gui.controllers.PopupAbstractt;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.helper.CallbackInterface;
import javafx.collections.FXCollections;
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

public class NewBalanceController extends PopupAbstractt implements Initializable {

    @FXML
    private GridPane mainGridPane;

    @FXML
    private Label modelIDLabel;

    @FXML
    private TextField nettoField;

    @FXML
    private TextField bruttoField;

    @FXML
    private ComboBox<String> afaBox;

    @FXML
    private TextField afaValueField;

    @FXML
    private DatePicker createdPicker;

    @FXML
    private CheckBox paidBox;

    @FXML
    private ComboBox<String> modelNameBox;

    @FXML
    private ComboBox<StatusEntity> statusIdBox;

    @FXML
    private CheckBox cashBox;

    @FXML
    private TextField noteField;

    private TextField customAfa;
    private boolean customAfaAdded = false;
    private boolean editingMode;
    private ComboBox<UsersEntity> userBox;
    private ComboBox<ProjectsEntity> projectBox;
    private DatePicker paidPicker;
    private PopOver parent;
    private BalanceEntity tempEntity;
    private CallbackInterface callbackFunction;

    public NewBalanceController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDatePicker();
        initCustomAfaField();
        initAfaBox();
        initStatusBox();
        initPaidPicker();
        initPaidBox();
        initUserBox();
        initProjectBox();
        initModelNameBox();
        initTextFields();
    }

    private void initPaidBox() {
        paidBox.setOnAction(event -> {
            if (paidBox.isSelected()) {
                showPaidPicker();
            } else {
                hidePaidPicker();
            }
        });
    }

    private void initPaidPicker() {
        paidPicker = new DatePicker(LocalDate.now());
        paidPicker.setPrefHeight(36.0);
        paidPicker.setPrefWidth(200.0);
    }

    private void hidePaidPicker() {
        if (mainGridPane.getChildren().contains(paidPicker)) {
            mainGridPane.getChildren().removeAll(paidPicker);
        }
    }

    private void showPaidPicker() {
        if (!mainGridPane.getChildren().contains(paidPicker)) {
            mainGridPane.add(paidPicker, 2, 6);
        }
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
            double multi = 1.0 + (((customAfaAdded) ? Double.parseDouble(customAfa.getText()) : Double.parseDouble(afaBox.getSelectionModel().getSelectedItem())) / 100);
            double bruttoValue = (Integer.parseInt(nettoField.getText())) * multi;
            bruttoField.setText(Integer.toString((int) bruttoValue));
            setAfaValueField();
        });

        bruttoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nettoField.isFocused() || "".equals(bruttoField.getText())) return;
            double multi = 1.0 + (((customAfaAdded) ? Double.parseDouble(customAfa.getText()) : Double.parseDouble(afaBox.getSelectionModel().getSelectedItem())) / 100);
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
        mainGridPane.add(userBox, 1, 8);
        statusIdBox.getSelectionModel().select(1);
        modelIDLabel.setText("Alkalmazottak");
    }

    private void showProjectBox() {
        if (mainGridPane.getChildren().contains(projectBox)) return;
        if (mainGridPane.getChildren().contains(userBox)) {
            mainGridPane.getChildren().removeAll(userBox);
        }
        mainGridPane.add(projectBox, 1, 8);
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
        userBox = new ComboBox<>();
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
    }

    private void initProjectBox() {
        projectBox = new ComboBox<>();
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
    }

    private void initModelNameBox() {
        modelNameBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Alkalmazott".equals(newValue)) {
                showUserBox();
            } else if ("Munka".equals(newValue)) {
                showProjectBox();
            } else {
                hideBoxes();
            }
        });
        modelNameBox.getSelectionModel().select(1);
    }

    private void initStatusBox() {
        GenericDao<StatusEntity> statusDao = DaoManager.getInstance().getStatusDao();
        List<StatusEntity> statusList = statusDao.findAll();
        statusIdBox.setItems(FXCollections.observableArrayList(statusList));
    }

    private void initCustomAfaField() {
        customAfa = new TextField();
        customAfa.prefHeight(36.0);
        customAfa.setPromptText("Egyedi ÁFA");
        customAfa.setText("0");
    }

    private void initAfaBox() {
        afaBox.getItems().addAll("0", "27", "egyéb");
        afaBox.getSelectionModel().select(1);
        afaBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("egyéb".equals(newValue)) {
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
    }

    @FXML
    private void onSaveAction() {
        if(editingMode) {
            modifyEntity();
        } else {
            saveNewEntity();
        }
    }

    private void modifyEntity() {
        tempEntity.setNetto(Integer.parseInt(nettoField.getText()));
        tempEntity.setBrutto(Integer.parseInt(bruttoField.getText()));
        tempEntity.setAfa(Integer.parseInt((customAfaAdded) ? customAfa.getText() : afaBox.getSelectionModel().getSelectedItem()));
        tempEntity.setAfaValue(Integer.parseInt(afaValueField.getText()));
        tempEntity.setCreated(Date.valueOf(createdPicker.getValue()));
        tempEntity.setCompleted((paidBox.isSelected()) ? Date.valueOf(paidPicker.getValue()) : null);
        tempEntity.setStatusId((statusIdBox.getSelectionModel().getSelectedItem()).getId());
        tempEntity.setCash(cashBox.isSelected());
        tempEntity.setNote(noteField.getText());
        String modelName = modelNameBox.getSelectionModel().getSelectedItem();
        switch (modelName) {
            case "Alkalmazott":
                tempEntity.setModelName("user");
                tempEntity.setModelId(( userBox.getSelectionModel().getSelectedItem()).getId());
                break;
            case "Munka":
                tempEntity.setModelName("project");
                tempEntity.setModelId(( projectBox.getSelectionModel().getSelectedItem()).getId());
                break;
            case "Egyéb":
                tempEntity.setModelName(null);
                tempEntity.setModelId(null);
                break;
            default:
                System.out.println("baj van");
                break;
        }

        GenericDao<BalanceEntity> balanceDao = DaoManager.getInstance().getBalanceDao();
        balanceDao.update(tempEntity);
        ControllerMediator.getInstance().refreshDailyTableData(createdPicker.getValue());
        onCancelAction();
    }

    private void saveNewEntity(){
        BalanceEntity balanceEntity = createEntityFromForm();
        GenericDao<BalanceEntity> balanceDao = DaoManager.getInstance().getBalanceDao();
        balanceDao.create(balanceEntity);
        ControllerMediator.getInstance().refreshDailyTableData(createdPicker.getValue());
        onCancelAction();
    }

    private BalanceEntity setModelProperties(BalanceEntity balanceEntity) {
        String modelName = modelNameBox.getSelectionModel().getSelectedItem();
        switch (modelName) {
            case "Alkalmazott":
                balanceEntity.setModelName("user");
                balanceEntity.setModelId(( userBox.getSelectionModel().getSelectedItem()).getId());
                break;
            case "Munka":
                balanceEntity.setModelName("project");
                balanceEntity.setModelId(( projectBox.getSelectionModel().getSelectedItem()).getId());
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
        balanceEntity.setAfa(Integer.parseInt((customAfaAdded) ? customAfa.getText() : afaBox.getSelectionModel().getSelectedItem()));
        balanceEntity.setAfaValue(Integer.parseInt(afaValueField.getText()));
        balanceEntity.setCreated(Date.valueOf(createdPicker.getValue()));
        balanceEntity.setCompleted((paidBox.isSelected()) ? Date.valueOf(paidPicker.getValue()) : null);
        balanceEntity.setStatusId((statusIdBox.getSelectionModel().getSelectedItem()).getId());
        balanceEntity.setCash(cashBox.isSelected());
        balanceEntity.setNote(noteField.getText());
        balanceEntity = setModelProperties(balanceEntity);
        return balanceEntity;
    }

    @Override
    public void loadEntityToFields(PersistentEntity entity) {
        if(entity == null) {
            editingMode = false;
            return;
        }
        editingMode = true;
        tempEntity = (BalanceEntity)entity;
        nettoField.setText(tempEntity.getNetto().toString());
        bruttoField.setText(tempEntity.getBrutto().toString());
        String afa = tempEntity.getAfa().toString();
        if("0".equals(afa)){
            afaBox.getSelectionModel().select(0);
        } else if("27".equals(afa)) {
            afaBox.getSelectionModel().select(1);
        } else {
            afaBox.getSelectionModel().select(2);
            customAfa.setText(afa);
            customAfaAdded = true;
        }
        afaValueField.setText(tempEntity.getAfaValue().toString());
        createdPicker.setValue(tempEntity.getCreated().toLocalDate());
        if(tempEntity.getCompleted() != null) {
            paidBox.selectedProperty().setValue(true);
            paidPicker.setValue(tempEntity.getCompleted().toLocalDate());
        }
        switch (tempEntity.getModelName()){
            case "Alkalmazott":
                modelNameBox.getSelectionModel().select(0);
                GenericDao<UsersEntity> userDao = DaoManager.getInstance().getUserDao();
                userBox.getSelectionModel().select(userDao.findById(tempEntity.getModelId()));
                showUserBox();
                break;
            case "Munka":
                modelNameBox.getSelectionModel().select(1);
                GenericDao<ProjectsEntity> projectDao = DaoManager.getInstance().getProjectsDao();
                projectBox.getSelectionModel().select(projectDao.findById(tempEntity.getModelId()));
                showProjectBox();
                break;
            case "egyéb":
                modelNameBox.getSelectionModel().select(2);
                hideBoxes();
                break;
        }
        cashBox.selectedProperty().setValue(tempEntity.getCash());
        noteField.setText(tempEntity.getNote());

    }

    @Override
    public void setCallbackFunction(CallbackInterface callbackFunction) {
        this.callbackFunction = callbackFunction;
    }

    @Override
    public void setParent(PopOver parent) {
        this.parent = parent;
    }

    @Override
    protected void onCancelAction() {
        callbackFunction.callbackFunction();
        parent.hide();
    }
}
