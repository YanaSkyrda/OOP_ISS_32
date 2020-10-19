package org.example.entity;

public class Drug {
    private String name;

    private String pharmName;

    private String group;

    private String analogs;

    private Version version;

    public String getName() {
        return name;
    }

    public String getPharmName() {
        return pharmName;
    }

    public String getGroup() {
        return group;
    }

    public String getAnalogs() {
        return analogs;
    }

    public Version getVersion() {
        return version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPharmName(String pharmName) {
        this.pharmName = pharmName;
    }

    public void setGroup(String group) {
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
}
