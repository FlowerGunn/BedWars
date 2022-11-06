package org.screamingsandals.bedwars.utils.flowergun.gameplay;

public class CompoundValueModifier {

    int flatIntModifier;
    double flatDoubleModifier;
    double exponentialModifier;

    public CompoundValueModifier() {
        flatIntModifier = 0;
        flatDoubleModifier = 0;
        exponentialModifier = 0;
    }

    public void addInt(int value) {
        this.flatIntModifier += value;
    }

    public void addDouble(double value) {
        this.flatDoubleModifier += value;
    }

    public void addExp(double value) {
        this.exponentialModifier += value;
    }

    public double processValueEffectiveIncrease(double value) {
        return ( value + flatDoubleModifier + flatIntModifier ) * ( 1 + exponentialModifier);
    }

    public double processValueEffectiveDecrease(double value) {
        return ( value ) * ( 1 + exponentialModifier) + flatDoubleModifier + flatIntModifier;
    }

    public int processValueEffectiveIncrease(int value) {
        return (int) (( value + flatIntModifier ) * ( 1 + exponentialModifier));
    }

    public int processValueEffectiveDecrease(int value) {
        return (int) (( value ) * ( 1 + exponentialModifier) + flatIntModifier);
    }


}
