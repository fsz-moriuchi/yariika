package model;

import dao.UsersDAO;

public class UserLoginLogic {
	public boolean execute(UserLogin login) {
		UsersDAO dao = new UsersDAO();
		User user = dao.findByLogin(login);
		return user != null;
	}
	
	 //  追加
    public User getUser(UserLogin login) {
        UsersDAO dao = new UsersDAO();
        return dao.findByLogin(login);
    }
}