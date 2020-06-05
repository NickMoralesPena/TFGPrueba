package Utils;

public class Vehiculo {
    String marca, modelo;
    int año, cv;
    String kilometros, matricula, dniConductor;

    public Vehiculo(String marca, String modelo, int año, int cv, String kilometros, String matricula, String dniConductor) {
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.cv = cv;
        this.kilometros = kilometros;
        this.matricula = matricula;
        this.dniConductor = dniConductor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getCv() {
        return cv;
    }

    public void setCv(int cv) {
        this.cv = cv;
    }

    public String getKilometros() {
        return kilometros;
    }

    public void setKilometros(String kilometros) {
        this.kilometros = kilometros;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDniConductor() {
        return dniConductor;
    }

    public void setDniConductor(String dniConductor) {
        this.dniConductor = dniConductor;
    }
}
