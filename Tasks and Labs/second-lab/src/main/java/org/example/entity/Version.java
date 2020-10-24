package org.example.entity;

public class Version {
    private String type;

    private int certificate;

    private String packageType;

    private double dosage;

    public String getType() {
        return type;
    }

    public int getCertificate() {
        return certificate;
    }

    public String getPackageType() {
        return packageType;
    }

    public double getDosage() {
        return dosage;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCertificate(int certificate) {
        this.certificate = certificate;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(type).append('\n');
        result.append(certificate).append('\n');
        result.append(packageType).append('\n');
        result.append(dosage).append('\n');
        return result.toString();
    }
}
