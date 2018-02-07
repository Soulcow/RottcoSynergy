package synergy.rottco.bullet.black.rottcosynergy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ModelGasStation implements Parcelable{
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String workHours;
    private String fleetCards;
    private String imageUrl;
    private String services;
    private String fuels;
    private String cards;

    public ModelGasStation() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ModelGasStation(String name, String address, double latitude, double longitude, String workHours, String fleetCards,String imageUrl, String services, String fuels, String cards) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.workHours = workHours;
        this.fleetCards = fleetCards;
        this.imageUrl=imageUrl;
        this.services = services;
        this.fuels = fuels;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public String getFleetCards() {
        return fleetCards;
    }

    public void setFleetCards(String fleetCards) {
        this.fleetCards = fleetCards;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getFuels() {
        return fuels;
    }

    public void setFuels(String fuels) {
        this.fuels = fuels;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "ModelGasStation{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", workHours='" + workHours + '\'' +
                ", fleetCards='" + fleetCards + '\'' +
                ", services=" + services +
                ", fuels=" + fuels +
                ", cards=" + cards +
                '}';
    }

    public ModelGasStation(Parcel in) {
        super();
        readFromParcel(in);
    }
    public static final Parcelable.Creator<ModelGasStation> CREATOR = new Parcelable.Creator<ModelGasStation>() {
        public ModelGasStation createFromParcel(Parcel in) {
            return new ModelGasStation(in);
        }

        public ModelGasStation[] newArray(int size) {

            return new ModelGasStation[size];
        }

    };

    public void readFromParcel(Parcel in) {
        address = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        workHours = in.readString();
        fleetCards= in.readString();
        services = in.readString();
        fuels= in.readString();
        cards= in.readString();
        /*
        * private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String workHours;
    private String fleetCards;
    private ArrayList<String> services;
    private ArrayList<String> fuels;
    private ArrayList<String> cards;
        * */
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(address);
//        parcel.writeDouble(latitude);
//        parcel.writeDouble(longitude);
//        parcel.writeString(wo);
//        dest.writeInt(Value2);
//        dest.writeInt(Value3);
    }
}
