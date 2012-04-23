package go.web.model;

import com.mongodb.BasicDBObject;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Ride {
    
    private Date date;
    private double consumption;
    private String carBrand;
    private String carType;
    private int fare;
    private RidePoint startPoint;
    private RidePoint finishPoint;
    private List<RidePoint> stopPoints;
    private User owner;
    private List<User> onBoard;
    
    public BasicDBObject getDbRide() {
        BasicDBObject query = new BasicDBObject();
        query.put("date", date);
        query.put("consumption", consumption);
        query.put("carBrand", carBrand);
        query.put("carType", carType);
        query.put("fare", fare);
        query.put("startPoint", startPoint.getDbRidePoint());
        query.put("finishPoint", finishPoint.getDbRidePoint());
        Iterator it = stopPoints.iterator();
        List<BasicDBObject> stopPointsDb = null;
        while(it.hasNext() == true) {
            RidePoint rp = (RidePoint) it.next();
            stopPointsDb.add(rp.getDbRidePoint());
        }
        query.put("stopPoints", stopPointsDb);
        query.put("owner", owner.getId());        
        return query;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarType() {
        return carType;
    }

    public double getConsumption() {
        return consumption;
    }

    public Date getDate() {
        return date;
    }

    public int getFare() {
        return fare;
    }

    public RidePoint getFinishPoint() {
        return finishPoint;
    }

    public RidePoint getStartPoint() {
        return startPoint;
    }

    public List<RidePoint> getStopPoints() {
        return stopPoints;
    }

    public User getOwner() {
        return owner;
    }

    public List<User> getOnBoard() {
        return onBoard;
    }
    
    
    
    
}
