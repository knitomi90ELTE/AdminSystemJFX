package hu.kniznertamas.adminsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.BalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.StatusEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.MainController;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.BalanceTableController;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.UploadTableController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.DailyViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.FinancesController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.OpenItemsViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.ProjectViewController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.FinancesTableController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.HoursTableController;

@Configuration
@ComponentScan(basePackages = { "hu.kniznertamas.adminsystem.db.dao" })
public class Config {

    @Autowired
    private ApplicationContext ctx;

    @Bean
    public GenericDao<BalanceEntity> balanceDao() {
        return ctx.getBean(DaoManager.class).getBalanceDao();
    }

    @Bean
    public GenericDao<ProjectsEntity> projectsDao() {
        return ctx.getBean(DaoManager.class).getProjectsDao();
    }

    @Bean
    public GenericDao<StatusEntity> statusDao() {
        return ctx.getBean(DaoManager.class).getStatusDao();
    }

    @Bean
    public GenericDao<UploadEntity> uploadDao() {
        return ctx.getBean(DaoManager.class).getUploadDao();
    }

    @Bean
    public GenericDao<UsersEntity> userDao() {
        return ctx.getBean(DaoManager.class).getUserDao();
    }

    @Bean
    public UploadTableController uploadTableController() {
        return ctx.getBean(UploadTableController.class);
    }

    @Bean
    public BalanceTableController balanceTableController() {
        return ctx.getBean(BalanceTableController.class);
    }

    @Bean
    public ProjectViewController projectViewController() {
        return ctx.getBean(ProjectViewController.class);
    }

    @Bean
    public FinancesTableController financesTableController() {
        return ctx.getBean(FinancesTableController.class);
    }

    @Bean
    public HoursTableController hoursTableController() {
        return ctx.getBean(HoursTableController.class);
    }

    @Bean
    public DailyViewController dailyViewController() {
        return ctx.getBean(DailyViewController.class);
    }

    @Bean
    public OpenItemsViewController openItemsViewController() {
        return ctx.getBean(OpenItemsViewController.class);
    }

    @Bean
    public FinancesController financesController() {
        return ctx.getBean(FinancesController.class);
    }

    @Bean
    public MainController mainController() {
        return ctx.getBean(MainController.class);
    }

}
