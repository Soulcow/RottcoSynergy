package synergy.rottco.bullet.black.rottcosynergy;

public class ModelServices {
    private int Numar;
    private String Service;

    public ModelServices(int numar, String service) {
        Numar = numar;
        Service = service;
    }

    public int getNumar() {
        return Numar;
    }

    public String getService() {
        return Service;
    }

    public void setNumar(int numar) {
        Numar = numar;
    }

    public void setService(String service) {
        Service = service;
    }
}
