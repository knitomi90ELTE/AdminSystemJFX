package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.BalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.StatusEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.DailyViewController;
import hu.kniznertamas.adminsystem.gui.elements.NumberTextField;
import hu.kniznertamas.adminsystem.gui.elements.PopupAbstractt;
import hu.kniznertamas.adminsystem.helper.CallbackInterface;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class NewBalanceController extends PopupAbstractt {

    @Autowired
    private GenericDao<UsersEntity> usersDao;

    @Autowired
    private GenericDao<ProjectsEntity> projectsDao;

    @Autowired
    private GenericDao<StatusEntity> statusDao;

    @Autowired
    private GenericDao<BalanceEntity> balanceDao;

    @Autowired
    private DailyViewController dailyViewController;

    @Autowired
    private BalanceTableController balanceTableController;

    @Autowired
    private UploadTableController uploadTableController;

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

    public void init() {
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
        paidPicker = new JFXDatePicker(dailyViewController.getCurrentDate());
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
        createdPicker.setValue(dailyViewController.getCurrentDate());
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
        List<UsersEntity> allUsers = usersDao.findAll();
        userBox.setItems(FXCollections.observableArrayList(allUsers));
        userBox.getSelectionModel().selectFirst();
        EntityHelper.initComboBoxWithEntity(userBox);
    }

    private void initProjectBox() {
        projectBox = new JFXComboBox<>();
        projectBox.setPrefHeight(36.0);
        projectBox.setPrefWidth(200.0);
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
        tempEntity.setNetto(Integer.parseInt(nettoField.getText()));
        tempEntity.setBrutto(Integer.parseInt(bruttoField.getText()));
        tempEntity.setAfa(
                Integer.parseInt(customAfaAdded ? customAfa.getText() : afaBox.getSelectionModel().getSelectedItem()));
        tempEntity.setAfaValue(Integer.parseInt(afaValueField.getText()));
        tempEntity.setCreated(Date.valueOf(createdPicker.getValue()));
        tempEntity.setCompleted(paidBox.isSelected() ? Date.valueOf(paidPicker.getValue()) : null);
        tempEntity.setStatusId(statusIdBox.getSelectionModel().getSelectedItem().getId());
        tempEntity.setCash(cashBox.isSelected());
        tempEntity.setNote(noteField.getText());
        String modelName = modelNameBox.getSelectionModel().getSelectedItem();
        switch (modelName) {
            case "Alkalmazott":
                tempEntity.setModelName("user");
                tempEntity.setModelId(userBox.getSelectionModel().getSelectedItem().getId());
                break;
            case "Munka":
                tempEntity.setModelName("project");
                tempEntity.setModelId(projectBox.getSelectionModel().getSelectedItem().getId());
                break;
            case "Egyéb":
                tempEntity.setModelName(null);
                tempEntity.setModelId(null);
                break;
            default:
                break;
        }
        balanceDao.update(tempEntity);
        LOGGER.info("Modified entity: {}", tempEntity.getId());
        refreshDailyTableData(createdPicker.getValue());
        onCancelAction();
    }

    private void saveNewEntity() {
        BalanceEntity balanceEntity = createEntityFromForm();
        balanceDao.create(balanceEntity);
        LOGGER.info("Saved entity: {}", balanceEntity);
        refreshDailyTableData(createdPicker.getValue());
        onCancelAction();
    }

    private void refreshDailyTableData(LocalDate currentDate) {
        uploadTableController.refreshTableData(currentDate);
        balanceTableController.refreshTableData(currentDate);
    }

    private BalanceEntity setModelProperties(BalanceEntity balanceEntity) {
        String modelName = modelNameBox.getSelectionModel().getSelectedItem();
        switch (modelName) {
            case "Alkalmazott":
                balanceEntity.setModelName("user");
                balanceEntity.setModelId(userBox.getSelectionModel().getSelectedItem().getId());
                break;
            case "Munka":
                balanceEntity.setModelName("project");
                balanceEntity.setModelId(projectBox.getSelectionModel().getSelectedItem().getId());
                break;
            case "Egyéb":
                balanceEntity.setModelName(null);
                balanceEntity.setModelId(null);
                break;
            default:
                break;
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
        switch (tempEntity.getModelName()) {
            case "Alkalmazott":
                modelNameBox.getSelectionModel().select(0);
                userBox.getSelectionModel().select(usersDao.findById(tempEntity.getModelId()));
                showUserBox();
                break;
            case "Munka":
                modelNameBox.getSelectionModel().select(1);
                projectBox.getSelectionModel().select(projectsDao.findById(tempEntity.getModelId()));
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
