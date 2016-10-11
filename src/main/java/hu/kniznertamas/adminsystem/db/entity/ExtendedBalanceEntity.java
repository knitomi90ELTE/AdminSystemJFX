package hu.kniznertamas.adminsystem.db.entity;

public class ExtendedBalanceEntity extends BalanceEntity {

    private String status_name;
    private Object model;
    private String display_name;

    public ExtendedBalanceEntity() {
    }

    public ExtendedBalanceEntity(BalanceEntity be) {
        this.id = be.getId();
        this.netto = be.getNetto();
        this.brutto = be.getBrutto();
        this.afa = be.getAfa();
        this.afaValue = be.getAfaValue();
        this.statusId = be.getStatusId();
        this.modelName = be.getModelName();
        this.modelId = be.getModelId();
        this.created = be.getCreated();
        this.completed = be.getCompleted();
        this.cash = be.getCash();
        this.note = be.getNote();
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

}
