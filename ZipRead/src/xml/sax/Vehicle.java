package xml.sax;

public class Vehicle {
    public String vehicleId = null;
    public String name      = null;

    public String toString() {
        return  this.vehicleId + " : " +
                this.name;
    }
}
