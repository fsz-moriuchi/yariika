package model;

import dao.UsersDAO;

public class LoginLogic {
	public boolean execute(Login login) {
		UsersDAO dao = new UsersDAO();
		User user = dao.findByLogin(login);
		return user != null;
	}
}