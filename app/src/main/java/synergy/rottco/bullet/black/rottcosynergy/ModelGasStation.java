package synergy.rottco.bullet.black.rottcosynergy;

import java.util.ArrayList;

/**
 * Created by boghi on 1/2/2018.
 */

public class ModelGasStation {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String workHours;
    private String fleetCards;
    private ArrayList<String> services;
    private ArrayList<String> fuels;
    private ArrayList<String> cards;

    public ModelGasStation() {
    }

    public ModelGasStation(String name, String address, double latitude, double longitude, String workHours, String fleetCards, ArrayList<String> services, ArrayList<String> fuels, ArrayList<String> cards) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.workHours = workHours;
        this.fleetCards = fleetCards;
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

    public ArrayList<String> getServices() {
        return services;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public ArrayList<String> getFuels() {
        return fuels;
    }

    public void setFuels(ArrayList<String> fuels) {
        this.fuels = fuels;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setCards(ArrayList<String> cards) {
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
}
