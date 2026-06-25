package dao;

import model.DashboardSummary;

public class DashboardDAO {

	private final ReserveDAO reserveDAO = new ReserveDAO();
	private final PetListDAO petListDAO = new PetListDAO();
	private final FacilityViewDAO facilityViewDAO = new FacilityViewDAO();
	private final MessageDAO messageDAO = new MessageDAO();

	public DashboardSummary getSummary(String facilityId) {
		DashboardSummary summary = new DashboardSummary();

		summary.setTodayReserveCount(reserveDAO.countTodayReserve(facilityId));
		summary.setPetCount(petListDAO.countByfacilityID(facilityId));
		summary.setNextReserve(reserveDAO.findnextReserve(facilityId));
		summary.setLatestPet(petListDAO.findLatestPetByFacilityID(facilityId));
		summary.setViewCount(facilityViewDAO.getViewCount(facilityId));
		summary.setUnreadMessageCount(messageDAO.countUnreadByFacilityId(facilityId));

		return summary;
	}
}