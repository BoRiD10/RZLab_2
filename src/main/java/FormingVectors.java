import java.util.Arrays;
import java.util.Collections;

public class FormingVectors {
//  Создание необходимых экземпляров класса фильтр и доступа к хранилищу данных
    private int i;
    private RMSValue rms;
    private SampleValue sv;
    private Filter filterSumA = new Filter();
    private Filter filterSumB = new Filter();
    private Filter filterSumC = new Filter();
    private Filter filterSPA = new Filter();
    private Filter filterSNA = new Filter();
    private Filter filterSPB = new Filter();
    private Filter filterSNB = new Filter();
    private Filter filterSPC = new Filter();
    private Filter filterSNC = new Filter();

//  Формирование дифф тока фазы А
    public double sumValueA(){
        filterSumA.setSv(sv);
//      Сложение мгновенных значений всех токов
        double sumA = sv.getPhAbay1() + sv.getPhAbay2() + sv.getPhAbay3() + sv.getPhAbay4() + sv.getPhAbay5();
//      Выделение действующего значения
        sumA = filterSumA.getRms(sumA);
        return sumA;
    }

//  Формирование дифф тока фазы В
    public double sumValueB(){
        filterSumB.setSv(sv);
        double sumB = sv.getPhBbay1() + sv.getPhBbay2() + sv.getPhBbay3() + sv.getPhBbay4() + sv.getPhBbay5();
        sumB = filterSumB.getRms(sumB);
        return sumB;
    }

//  Формирование дифф тока фазы С
    public double sumValueC(){
        filterSumC.setSv(sv);
        double sumC = sv.getPhCbay1() + sv.getPhCbay2() + sv.getPhCbay3() + sv.getPhCbay4() + sv.getPhCbay5();
        sumC = filterSumC.getRms(sumC);
        return sumC;
    }

    public double CurrentModule(double[] X){
        double module = Math.sqrt(Math.pow(X[0], 2) + Math.pow(X[1], 2));
        return module;
    }

    public double CurrentAngle(double[] X){
        double angle = Math.atan2(X[0], X[1]);
        return Math.toDegrees(angle);
    }

//  Формирование тормозного тока фазы А
    public double brakingСurrentA(){
        filterSPA.setSv(sv);
        filterSNA.setSv(sv);
//      Положительные токовые отсчеты
        double SP = 0;
//      Отрицательные токовые отсчеты
        double SN = 0;
//      Список для определения максимального значения
        Double[] maxValue = new Double[2];
//      Матрица мгновенных значений токов присоединений фазы А
        double[] MSP = {sv.getPhAbay1(), sv.getPhAbay2(), sv.getPhAbay3(),sv.getPhAbay4(),sv.getPhAbay5()};
//      Фильтрация на положительные и отрицательные токовые отсчеты
        for (i = 0; i < MSP.length; i++){
            if (MSP[i] >= 0){
                SP += MSP[i];
            }
            else {
                SN += MSP[i];
            }
        }
//      Выделение действующего значения
        SP = filterSPA.getRms(SP);
        SN = filterSNA.getRms(SN);
//      Добавление в список
        maxValue[0] = SP; maxValue[1] = SN;
//      Возвращает максимальное значение из 2х отсчетов
        return Collections.max(Arrays.asList(maxValue));
    }

//  Формирование тормозного тока фазы В
    public double brakingСurrentB(){
        filterSPB.setSv(sv);
        filterSNB.setSv(sv);
        double SP = 0;
        double SN = 0;
        Double[] maxValue = new Double[2];
        double[] MSP = {sv.getPhBbay1(), sv.getPhBbay2(), sv.getPhBbay3(),sv.getPhBbay4(),sv.getPhBbay5()};
        for (i = 0; i < MSP.length; i++){
            if (MSP[i] >= 0){
                SP += MSP[i];
            }
            else {
                SN += MSP[i];
            }
        }
        SP = filterSPB.getRms(SP);
        SN = filterSNB.getRms(SN);
        maxValue[0] = SP; maxValue[1] = SN;
        return Collections.max(Arrays.asList(maxValue));
    }

//  Формирование тормозного тока фазы С
    public double brakingСurrentC(){
        filterSPC.setSv(sv);
        filterSNC.setSv(sv);
        double SP = 0;
        double SN = 0;
        Double[] maxValue = new Double[2];
        double[] MSP = {sv.getPhCbay1(), sv.getPhCbay2(), sv.getPhCbay3(),sv.getPhCbay4(),sv.getPhCbay5()};
        for (i = 0; i < MSP.length; i++){
            if (MSP[i] >= 0){
                SP += MSP[i];
            }
            else {
                SN += MSP[i];
            }
        }
        SP = filterSPC.getRms(SP);
        SN = filterSNC.getRms(SN);
        maxValue[0] = SP; maxValue[1] = SN;
        return Collections.max(Arrays.asList(maxValue));
    }

    public RMSValue getRms() {
        return rms;
    }

    public void setRms(RMSValue rms) {
        this.rms = rms;
    }

    public SampleValue getSv() {
        return sv;
    }

    public void setSv(SampleValue sv) {
        this.sv = sv;
    }
}
