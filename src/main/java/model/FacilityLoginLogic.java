package model;

import dao.FacilitiesDAO;

public class FacilityLoginLogic {
	public boolean execute(FacilityLogin login) {
		FacilitiesDAO dao = new FacilitiesDAO();
		Facility facility = dao.findByLogin(login);
		return facility != null;
	}
}
