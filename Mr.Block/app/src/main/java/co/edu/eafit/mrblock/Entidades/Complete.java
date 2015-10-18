package co.edu.eafit.mrblock.Entidades;

/**
 * Created by juan on 17/10/15.
 */
public class Complete {
    private String blockName;
    private int inCalls;
    private int outCalls;
    private int inSms;
    private int outSms;
    private String type;

    public Complete(String blockName, int inCalls, int outCalls, int inSms, int outSms, String type) {
        this.blockName = blockName;
        this.inCalls = inCalls;
        this.outCalls = outCalls;
        this.inSms = inSms;
        this.outSms = outSms;
        this.type = type;
    }

    public Complete(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public int getInCalls() {
        return inCalls;
    }

    public void setInCalls(int inCalls) {
        this.inCalls = inCalls;
    }

    public int getOutCalls() {
        return outCalls;
    }

    public void setOutCalls(int outCalls) {
        this.outCalls = outCalls;
    }

    public int getInSms() {
        return inSms;
    }

    public void setInSms(int inSms) {
        this.inSms = inSms;
    }

    public int getOutSms() {
        return outSms;
    }

    public void setOutSms(int outSms) {
        this.outSms = outSms;
    }
}
