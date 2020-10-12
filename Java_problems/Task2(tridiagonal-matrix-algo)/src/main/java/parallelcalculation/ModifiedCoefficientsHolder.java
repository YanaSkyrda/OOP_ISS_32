package parallelcalculation;

import java.util.ArrayList;
import java.util.List;

class ModifiedCoefficientsHolder {
    private List<Double> CModified;
    private List<Double> DModified;

    ModifiedCoefficientsHolder(int listsSize) {
        this.CModified = new ArrayList<>(listsSize);
        this.DModified = new ArrayList<>(listsSize);
    }

    void addToCModified(double value) {
        CModified.add(value);
    }

    void addToDModified(double value) {
        DModified.add(value);
    }

    List<Double> getCModified() {
        return this.CModified;
    }

    List<Double> getDModified() {
        return this.DModified;
    }
}
