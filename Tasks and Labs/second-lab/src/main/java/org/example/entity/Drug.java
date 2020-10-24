package org.example.entity;

import java.util.Objects;

public class Drug implements Comparable<Drug>{
    private int id;

    private String name;

    private String pharmName;

    private Group group;

    private String analogs;

    private Version version;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPharmName() {
        return pharmName;
    }

    public Group getGroup() {
        return group;
    }

    public String getAnalogs() {
        return analogs;
    }

    public Version getVersion() {
        return version;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPharmName(String pharmName) {
        this.pharmName = pharmName;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setAnalogs(String analogs) {
        this.analogs = analogs;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name).append('\n');
        result.append(pharmName).append('\n');
        result.append(group).append('\n');
        result.append(analogs).append('\n');
        result.append(version).append('\n');
        return result.toString();
    }

    @Override
    public int compareTo(Drug o) {
        return (Integer.compare(this.version.getCertificate(), o.getVersion().getCertificate()));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pharmName, group, analogs, version);
    }
}
