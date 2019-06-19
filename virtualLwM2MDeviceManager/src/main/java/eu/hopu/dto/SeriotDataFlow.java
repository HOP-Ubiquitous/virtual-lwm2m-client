package eu.hopu.dto;

public class SeriotDataFlow {

    private String dataFlow;
    private int period;

    public SeriotDataFlow() {
    }

    public SeriotDataFlow(String dataFlow, int period) {
        this.dataFlow = dataFlow;
        this.period = period;
    }

    public String getDataFlow() {
        return dataFlow;
    }

    public void setDataFlow(String dataFlow) {
        this.dataFlow = dataFlow;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "SeriotDataFlow{" +
                "dataFlow='" + dataFlow + '\'' +
                ", period=" + period +
                '}';
    }
}
