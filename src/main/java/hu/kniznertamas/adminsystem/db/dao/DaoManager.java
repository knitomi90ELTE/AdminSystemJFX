package hu.kniznertamas.adminsystem.db.dao;

import hu.kniznertamas.adminsystem.db.entity.BalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.StatusEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;

public class DaoManager {

	private GenericDao<BalanceEntity> balanceDao;
	private GenericDao<ProjectsEntity> projectsDao;
	private GenericDao<StatusEntity> statusDao;
	private GenericDao<UploadEntity> uploadDao;
	private GenericDao<UsersEntity> userDao;

	public GenericDao<BalanceEntity> getBalanceDao() {
		if (balanceDao == null) {
			balanceDao = new DefaultDao<>(BalanceEntity.class);
		}
		return balanceDao;
	}

	public GenericDao<ProjectsEntity> getProjectsDao() {
		if (projectsDao == null) {
			projectsDao = new DefaultDao<>(ProjectsEntity.class);
		}
		return projectsDao;
	}

	public GenericDao<StatusEntity> getStatusDao() {
		if (statusDao == null) {
			statusDao = new DefaultDao<>(StatusEntity.class);
		}
		return statusDao;
	}

	public GenericDao<UploadEntity> getUploadDao() {
		if (uploadDao == null) {
			uploadDao = new DefaultDao<>(UploadEntity.class);
		}
		return uploadDao;
	}

	public GenericDao<UsersEntity> getUserDao() {
		if (userDao == null) {
			userDao = new DefaultDao<>(UsersEntity.class);
		}
		return userDao;
	}

	
	//TIPP: guava class instance map, spring "canHandle"
	@SuppressWarnings("rawtypes")
	public GenericDao getDaoByType(DaoType type) {
		GenericDao dao = null;
		switch (type) {
		case BALANCE:
			dao = getBalanceDao();
			break;
		case PROJECT:
			dao = getProjectsDao();
			break;
		case STATUS:
			dao = getStatusDao();
			break;
		case UPLOAD:
			dao = getUploadDao();
			break;
		case USER:
			dao = getUserDao();
			break;
		}
		return dao;
	}

	private DaoManager() {
	}

	public static DaoManager getInstance() {
		return DaoManagerHolder.INSTANCE;
	}

	private static class DaoManagerHolder {

		private static final DaoManager INSTANCE = new DaoManager();
	}
}
