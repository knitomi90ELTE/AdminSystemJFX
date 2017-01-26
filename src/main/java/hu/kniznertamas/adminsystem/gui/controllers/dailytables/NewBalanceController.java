package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.BalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.StatusEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.gui.elements.NumberTextField;
import hu.kniznertamas.adminsystem.gui.elements.PopupAbstractt;
import hu.kniznertamas.adminsystem.helper.CallbackInterface;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class NewBalanceController extends PopupAbstractt implements Initializable {

    @FXML
    private GridPane mainGridPane;

    @FXML
    private Label modelIDLabel;

    @FXML
    private NumberTextField nettoField;

    @FXML
    private NumberTextField bruttoField;

    @FXML
    private JFXComboBox<String> afaBox;

    @FXML
    private NumberTextField afaValueField;

    @FXML
    private JFXDatePicker createdPicker;

    @FXML
    private JFXCheckBox paidBox;

    @FXML
    private JFXComboBox<String> modelNameBox;

    @FXML
    private JFXComboBox<StatusEntity> statusIdBox;

    @FXML
    private JFXCheckBox cashBox;

    @FXML
    private JFXTextField noteField;

    private NumberTextField customAfa;
    private boolean customAfaAdded = false;
    private boolean editingMode;
    private JFXComboBox<UsersEntity> userBox;
    private JFXComboBox<ProjectsEntity> projectBox;
    private JFXDatePicker paidPicker;
    private PopOver parent;
    private BalanceEntity tempEntity;
    private CallbackInterface callbackFunction;

    private static final Logger LOGGER = LoggerFactory.getLogger(NewBalanceController.class);

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
        paidBox.setSelected(true);
    }

    private void initPaidPicker() {
        paidPicker = new JFXDatePicker(ControllerMediator.getInstance().getCurrentDate());
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
        createdPicker.setValue(ControllerMediator.getInstance().getCurrentDate());
    }

    private void initTextFields() {

        nettoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (bruttoField.isFocused() || "".equals(nettoField.getText())) {
                return;
            }
            double multi = getMultiplier();
            double bruttoValue = Integer.parseInt(nettoField.getText()) * multi;
            bruttoField.setText(Integer.toString((int) bruttoValue));
            setAfaValueField();
        });

        bruttoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nettoField.isFocused() || "".equals(bruttoField.getText())) {
                return;
            }
            double multi = getMultiplier();
            double nettoValue = Integer.parseInt(bruttoField.getText()) / multi;
            nettoField.setText(Integer.toString((int) nettoValue));
            setAfaValueField();
        });
    }

    private double getMultiplier() {
        double multi = 1.0;
        if (customAfaAdded && !"".equals(customAfa.getText())) {
            multi += Double.parseDouble(customAfa.getText()) / 100;
        } else {
            multi += Double.parseDouble(afaBox.getSelectionModel().getSelectedItem()) / 100;
        }
        return multi;
    }

    private void setAfaValueField() {
        afaValueField.setText(
                Integer.toString(Integer.parseInt(bruttoField.getText()) - Integer.parseInt(nettoField.getText())));
    }

    private void showUserBox() {
        if (mainGridPane.getChildren().contains(userBox)) {
            return;
        }
        if (mainGridPane.getChildren().contains(projectBox)) {
            mainGridPane.getChildren().removeAll(projectBox);
        }
        mainGridPane.add(userBox, 1, 8);
        statusIdBox.getSelectionModel().select(1);
        modelIDLabel.setText("Alkalmazottak");
    }

    private void showProjectBox() {
        if (mainGridPane.getChildren().contains(projectBox)) {
            return;
        }
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
        userBox = new JFXComboBox<>();
        userBox.setPrefHeight(36.0);
        userBox.setPrefWidth(200.0);
        GenericDao<UsersEntity> usersDao = DaoManager.getInstance().getUserDao();
        List<UsersEntity> allUsers = usersDao.findAll();
        userBox.setItems(FXCollections.observableArrayList(allUsers));
        userBox.getSelectionModel().selectFirst();
        EntityHelper.initComboBoxWithEntity(userBox);
    }

    private void initProjectBox() {
        projectBox = new JFXComboBox<>();
        projectBox.setPrefHeight(36.0);
        projectBox.setPrefWidth(200.0);
        GenericDao<ProjectsEntity> projectsDao = DaoManager.getInstance().getProjectsDao();
        List<ProjectsEntity> allProjects = projectsDao.findAll();
        projectBox.setItems(FXCollections.observableArrayList(allProjects));
        projectBox.getSelectionModel().selectFirst();
        EntityHelper.initComboBoxWithEntity(projectBox);
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
        customAfa = new NumberTextField();
        customAfa.prefHeight(36.0);
        customAfa.setPromptText("Egyedi ÁFA");
        customAfa.setText("0");
        customAfa.textProperty().addListener((observable, oldValue, newValue) -> {
            reloadNettoField();
        });
    }

    private void initAfaBox() {
        afaBox.getItems().addAll("0", "27", "egyéb");
        afaBox.getSelectionModel().select(0);
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
            reloadNettoField();
        });
    }

    private void reloadNettoField() {
        String nettoValue = nettoField.getText();
        nettoField.setText("");
        nettoField.setText(nettoValue);
    }

    @FXML
    private void onSaveAction() {
        if (editingMode) {
            modifyEntity();
        } else {
            saveNewEntity();
        }
    }

    private void modifyEntity() {
        GenericDao<BalanceEntity> balanceDao = DaoManager.getInstance().getBalanceDao();
        BalanceEntity editedEntity = balanceDao.findById(tempEntity.getId());
        editedEntity.setNetto(Integer.parseInt(nettoField.getText()));
        editedEntity.setBrutto(Integer.parseInt(bruttoField.getText()));
        editedEntity.setAfa(
                Integer.parseInt(customAfaAdded ? customAfa.getText() : afaBox.getSelectionModel().getSelectedItem()));
        editedEntity.setAfaValue(Integer.parseInt(afaValueField.getText()));
        editedEntity.setCreated(Date.valueOf(createdPicker.getValue()));
        editedEntity.setCompleted(paidBox.isSelected() ? Date.valueOf(paidPicker.getValue()) : null);
        editedEntity.setStatusId(statusIdBox.getSelectionModel().getSelectedItem().getId());
        editedEntity.setCash(cashBox.isSelected());
        editedEntity.setNote(noteField.getText());
        String modelName = modelNameBox.getSelectionModel().getSelectedItem();
        if ("Alkalmazott".equals(modelName)) {
            editedEntity.setModelName("user");
            editedEntity.setModelId(userBox.getSelectionModel().getSelectedItem().getId());
        } else if ("Munka".equals(modelName)) {
            editedEntity.setModelName("project");
            editedEntity.setModelId(projectBox.getSelectionModel().getSelectedItem().getId());
        } else if ("Egyéb".equals(modelName)) {
            editedEntity.setModelName(null);
            editedEntity.setModelId(null);
        }

        balanceDao.update(editedEntity);
        LOGGER.info("Modified entity: {}", editedEntity.getId());
        ControllerMediator.getInstance().refreshDailyTableData(createdPicker.getValue());
        onCancelAction();
    }

    private void saveNewEntity() {
        BalanceEntity balanceEntity = createEntityFromForm();
        GenericDao<BalanceEntity> balanceDao = DaoManager.getInstance().getBalanceDao();
        balanceDao.create(balanceEntity);
        LOGGER.info("Saved entity: {}", balanceEntity);
        ControllerMediator.getInstance().refreshDailyTableData(createdPicker.getValue());
        onCancelAction();
    }

    private BalanceEntity setModelProperties(BalanceEntity balanceEntity) {
        String modelName = modelNameBox.getSelectionModel().getSelectedItem();
        if ("Alkalmazott".equals(modelName)) {
            balanceEntity.setModelName("user");
            balanceEntity.setModelId(userBox.getSelectionModel().getSelectedItem().getId());
        } else if ("Munka".equals(modelName)) {
            balanceEntity.setModelName("project");
            balanceEntity.setModelId(projectBox.getSelectionModel().getSelectedItem().getId());
        } else if ("Egyéb".equals(modelName)) {
            balanceEntity.setModelName(null);
            balanceEntity.setModelId(null);
        }
        return balanceEntity;
    }

    private BalanceEntity createEntityFromForm() {
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setNetto(Integer.parseInt(nettoField.getText()));
        balanceEntity.setBrutto(Integer.parseInt(bruttoField.getText()));
        balanceEntity.setAfa(
                Integer.parseInt(customAfaAdded ? customAfa.getText() : afaBox.getSelectionModel().getSelectedItem()));
        balanceEntity.setAfaValue(Integer.parseInt(afaValueField.getText()));
        balanceEntity.setCreated(Date.valueOf(createdPicker.getValue()));
        balanceEntity.setCompleted(paidBox.isSelected() ? Date.valueOf(paidPicker.getValue()) : null);
        balanceEntity.setStatusId(statusIdBox.getSelectionModel().getSelectedItem().getId());
        balanceEntity.setCash(cashBox.isSelected());
        balanceEntity.setNote(noteField.getText());
        balanceEntity = setModelProperties(balanceEntity);
        return balanceEntity;
    }

    @Override
    public void loadEntityToFields(PersistentEntity entity) {
        if (entity == null) {
            editingMode = false;
            return;
        }
        editingMode = true;
        tempEntity = (BalanceEntity) entity;
        nettoField.setText(tempEntity.getNetto().toString());
        bruttoField.setText(tempEntity.getBrutto().toString());
        String afa = tempEntity.getAfa().toString();
        if ("0".equals(afa)) {
            afaBox.getSelectionModel().select(0);
        } else if ("27".equals(afa)) {
            afaBox.getSelectionModel().select(1);
        } else {
            afaBox.getSelectionModel().select(2);
            customAfa.setText(afa);
            customAfaAdded = true;
        }
        afaValueField.setText(tempEntity.getAfaValue().toString());
        createdPicker.setValue(tempEntity.getCreated().toLocalDate());
        if (tempEntity.getCompleted() != null) {
            paidBox.selectedProperty().setValue(true);
            paidPicker.setValue(tempEntity.getCompleted().toLocalDate());
        }
        String modelName = tempEntity.getModelName();
        if ("user".equals(modelName)) {
            modelNameBox.getSelectionModel().select(0);
            GenericDao<UsersEntity> userDao = DaoManager.getInstance().getUserDao();
            userBox.getSelectionModel().select(userDao.findById(tempEntity.getModelId()));
            showUserBox();
        } else if ("project".equals(modelName)) {
            modelNameBox.getSelectionModel().select(1);
            GenericDao<ProjectsEntity> projectDao = DaoManager.getInstance().getProjectsDao();
            projectBox.getSelectionModel().select(projectDao.findById(tempEntity.getModelId()));
            showProjectBox();
        } else if (modelName == null) {
            modelNameBox.getSelectionModel().select(2);
            hideBoxes();
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
