package org.example.entity;

public class Version {
    private String type;

    private Certificate certificate;

    private Package packageType;

    private Dosage dosage;

    public String getType() {
        return type;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public Package getPackageType() {
        return packageType;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public void setPackageType(Package packageType) {
        this.packageType = packageType;
    }

    public void setDosage(Dosage dosage) {
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
