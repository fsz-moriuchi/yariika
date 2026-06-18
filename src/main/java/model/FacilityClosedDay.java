package model;

public class FacilityClosedDay {
	private int closedDayID;
    private String facilityID;
    private String closedDay;
    
    public FacilityClosedDay(int closedDayID, String facilityID, String closedDay) {
    	this.closedDayID = closedDayID;
    	this.facilityID = facilityID;
    	this.closedDay = closedDay;
    }
    
    public int getClosedDay() {
    	return closedDayID;
    }
    public String getFacilityID() {
    	return facilityID;
    }
    public String getclosedDay() {
    	return closedDay;
    }
}
