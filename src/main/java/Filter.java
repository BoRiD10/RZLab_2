public class Filter {
//  Переменный для фильтра фурье
    private double[] buffer = new double[20];
    private double sumA = 0;
    private double sumB = 0;
    private int countA = 0;

//  Для второй гармоники
    private double[] buffer2 = new double[20];
    private double sumA2 = 0;
    private double sumB2 = 0;
    private int countA2 = 0;

    double k = 0.1;
    private SampleValue sv;
    private RMSValue rms;

//  Фильтр Фурье первой гармоники
    public double[] calculate(double Value){
        sumA += (Value - buffer[countA])*sv.getCos(countA);
        sumB += (Value - buffer[countA])*sv.getSin(countA);
        buffer[countA] = Value;
        if (++countA>=20){
            countA = 0;
        }
        return new double[] {sumA, sumB};
    }

//  Фильтр Фурье второй гармоники
    public double[] calculate2(double Value){
        sumA2 += (Value - buffer2[countA2])*sv.getCos2(countA2);
        sumB2 += (Value - buffer2[countA2])*sv.getSin2(countA2);
        buffer2[countA2] = Value;
        if (++countA2>=10){
            countA2 = 0;
        }
        return new double[] {sumA2, sumB2};
    }

    public SampleValue getSv() {
        return sv;
    }

    public void setSv(SampleValue sv) {
        this.sv = sv;
    }

//  Формирование действующего значения
    public double getRms(double Value) {
        calculate(Value);
        return Math.sqrt((Math.pow(sumA*k, 2) + Math.pow(sumB*k, 2))/2);
    }

//  Формирование действующего значения для второй гармоники
    public double getRms2(double Value) {
        calculate2(Value);
        return Math.sqrt((Math.pow(sumA2*(k * 2), 2) + Math.pow(sumB2*(k * 2), 2))/2);
    }

    public void setRms(RMSValue rms) {
        this.rms = rms;
    }
}
