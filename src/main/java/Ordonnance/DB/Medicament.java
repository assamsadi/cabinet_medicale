package Ordonnance.DB;

public class Medicament {
    private String name;
    private int duree;
    private String desecrition;
    public Medicament(String name, int duree, String desecrition){
        this.desecrition=desecrition;
        this.duree = duree;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getDesecrition() {
        return desecrition;
    }

    public void setDesecrition(String desecrition) {
        this.desecrition = desecrition;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
