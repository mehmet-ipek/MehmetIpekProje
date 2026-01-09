package _23010310039_Mehmet_İpek;

public class _23010310039_Mehmet_İpek_Girişler implements _23010310039_Mehmet_İpek_Gate {
    private String name;
    private boolean value;

    public _23010310039_Mehmet_İpek_Girişler(String name) {
        this.name = name;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean evaluate() {
        return value;
    }

    public String getInputName() {
        return name;
    }

    public String toString() {
        return name;
    }
}