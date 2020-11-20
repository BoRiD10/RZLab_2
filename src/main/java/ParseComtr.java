import java.io.*;

public class ParseComtr {

    // Инициализация переменных
    private File comtrCfg, comtrDat;
    private BufferedReader BufferRead;
    private String line;
    private double k1[], k2[];
    String[] lineData;
    private int i;
    private double sin [] = new double[20];
    private double cos [] = new double[20];
    private double sin2 [] = new double[20];
    private double cos2 [] = new double[20];
    private double OrtPara [] = new double[2];
    private SampleValue sv = new SampleValue();
    private RMSValue rms = new RMSValue();

    // Создане экземпляров для всех присоединений фильтра, формирования векторов, логики
    private Filter filterA1 = new Filter();
    private Filter filterB1 = new Filter();
    private Filter filterC1 = new Filter();
    private Filter filterA2 = new Filter();
    private Filter filterB2 = new Filter();
    private Filter filterC2 = new Filter();
    private Filter filterA3 = new Filter();
    private Filter filterB3 = new Filter();
    private Filter filterC3 = new Filter();
    private Filter filterA4 = new Filter();
    private Filter filterB4 = new Filter();
    private Filter filterC4 = new Filter();
    private Filter filterA5 = new Filter();
    private Filter filterB5 = new Filter();
    private Filter filterC5 = new Filter();
    private FormingVectors FormVect = new FormingVectors();
    private FormingVectors FormVect2 = new FormingVectors();
    private Logic logicA = new Logic();
    private Logic logicB = new Logic();
    private Logic logicC = new Logic();

    // Формирование пути к файду
    private String comtrName = "KZB";
    private String comtrPath = "C:\\Users\\Илья\\Desktop\\Program_RZ\\ЛР2_ДЗШ\\ОпытыComtrade\\DPB\\5 sections\\";
    private String cfg = comtrPath + comtrName + ".cfg";
    private String dat = comtrPath + comtrName + ".dat";


    // Загрузка файла
    public ParseComtr(){
        comtrCfg = new File(cfg);
        comtrDat = new File(dat);
    }

    public void start(){
//      Передача значений в классы фильтров и векторов
        filterA1.setSv(sv);
        filterB1.setSv(sv);
        filterC1.setSv(sv);
        filterA2.setSv(sv);
        filterB2.setSv(sv);
        filterC2.setSv(sv);
        filterA3.setSv(sv);
        filterB3.setSv(sv);
        filterC3.setSv(sv);
        filterA4.setSv(sv);
        filterB4.setSv(sv);
        filterC4.setSv(sv);
        filterA5.setSv(sv);
        filterB5.setSv(sv);
        filterC5.setSv(sv);
        FormVect.setRms(rms);
        FormVect.setSv(sv);
        FormVect2.setRms(rms);
        FormVect2.setSv(sv);

        // Иициализация массива cos и sin для фильтра Фурье первой гармоники
        for (i = 0; i < 20; i++){
            sin[i] = Math.sin((2 * Math.PI*i)/20);
            cos[i] = Math.cos((2 * Math.PI*i)/20);
        }
        sv.setCos(cos);
        sv.setSin(sin);

        // Иициализация массива cos и sin для фильтра Фурье второй гармоники
        for (i = 0; i < 20; i++){
            sin2[i] = Math.sin((4 * Math.PI*i)/20);
            cos2[i] = Math.cos((4 * Math.PI*i)/20);
        }
        sv.setCos2(cos2);
        sv.setSin2(sin2);

        try {
//          Чтение файла из Comtrade
            BufferRead = new BufferedReader(new FileReader(comtrCfg));
            int lineNumber = 0, count = 0, numberData = 100;
            try {
//              Перебор по строкам
                while ((line = BufferRead.readLine()) != null){
                    lineNumber++;
//                  Создание списка коэффициентов
                    if (lineNumber==2){
                        numberData = Integer.parseInt(line.split(",")[1].replaceAll("A",""));
                        k1 = new double[numberData];
                        k2 = new double[numberData];
                    }
//                  Заполнение списка коэффициентов
                    if(lineNumber > 2 && lineNumber < numberData + 3){
                        String sp1[] = line.split(",");
                        k1[count] = Double.parseDouble(line.split(",")[5]);
                        k2[count] = Double.parseDouble(line.split(",")[6]);
                        count++;
                    }
                }
                count = 0;
//              Чтение DAT файта и получение мгновенных значений тока
                BufferRead = new BufferedReader(new FileReader(comtrDat));
                while ((line = BufferRead.readLine())!= null) {
                    count++;
//                  Запись мгновенных значений в хранилище
                    if (!(count > 800 && count < 2000)) continue;
                    lineData = line.split(",");
                    sv.setPhAbay1(Double.parseDouble(lineData[2]) * k1[0] + k2[0]);
                    sv.setPhBbay1(Double.parseDouble(lineData[3]) * k1[1] + k2[1]);
                    sv.setPhCbay1(Double.parseDouble(lineData[4]) * k1[2] + k2[2]);
                    sv.setPhAbay2(Double.parseDouble(lineData[5]) * k1[3] + k2[3]);
                    sv.setPhBbay2(Double.parseDouble(lineData[6]) * k1[4] + k2[4]);
                    sv.setPhCbay2(Double.parseDouble(lineData[7]) * k1[5] + k2[5]);
                    sv.setPhAbay3(Double.parseDouble(lineData[8]) * k1[6] + k2[6]);
                    sv.setPhBbay3(Double.parseDouble(lineData[9]) * k1[7] + k2[7]);
                    sv.setPhCbay3(Double.parseDouble(lineData[10]) * k1[8] + k2[8]);
                    sv.setPhAbay4(Double.parseDouble(lineData[11]) * k1[9] + k2[9]);
                    sv.setPhBbay4(Double.parseDouble(lineData[12]) * k1[10] + k2[10]);
                    sv.setPhCbay4(Double.parseDouble(lineData[13]) * k1[11] + k2[11]);
                    sv.setPhAbay5(Double.parseDouble(lineData[14]) * k1[12] + k2[12]);
                    sv.setPhBbay5(Double.parseDouble(lineData[15]) * k1[13] + k2[13]);
                    sv.setPhCbay5(Double.parseDouble(lineData[16]) * k1[14] + k2[14]);

//                  Получение и запись действующих значений, пофазно
                    rms.setPhAbay1(filterA1.getRms(sv.getPhAbay1()));
                    rms.setPhBbay1(filterB1.getRms(sv.getPhBbay1()));
                    rms.setPhCbay1(filterC1.getRms(sv.getPhCbay1()));
                    rms.setPhAbay2(filterA2.getRms(sv.getPhAbay2()));
                    rms.setPhBbay2(filterB2.getRms(sv.getPhBbay2()));
                    rms.setPhCbay2(filterC2.getRms(sv.getPhCbay2()));
                    rms.setPhAbay3(filterA3.getRms(sv.getPhAbay3()));
                    rms.setPhBbay3(filterB3.getRms(sv.getPhBbay3()));
                    rms.setPhCbay3(filterC3.getRms(sv.getPhCbay3()));
                    rms.setPhAbay4(filterA4.getRms(sv.getPhAbay4()));
                    rms.setPhBbay4(filterB4.getRms(sv.getPhBbay4()));
                    rms.setPhCbay4(filterC4.getRms(sv.getPhCbay4()));
                    rms.setPhAbay5(filterA5.getRms(sv.getPhAbay5()));
                    rms.setPhBbay5(filterB5.getRms(sv.getPhBbay5()));
                    rms.setPhCbay5(filterC5.getRms(sv.getPhCbay5()));

//                  Получение и запись дифф тока и тока 2ой гармоники
                    rms.setSumValueA(FormVect.sumValueA());
                    rms.setSumValueAg2(filterA1.getRms2(FormVect2.sumValueA()));
                    rms.setSumValueB(FormVect.sumValueB());
                    rms.setSumValueBg2(filterB1.getRms2(FormVect2.sumValueB()));
                    rms.setSumValueC(FormVect.sumValueC());
                    rms.setSumValueCg2(filterC1.getRms2(FormVect2.sumValueC()));

//                  Формирование тормозного тока
                    rms.setBrakingCurrA(FormVect.brakingСurrentA());
                    rms.setBrakingCurrB(FormVect.brakingСurrentB());
                    rms.setBrakingCurrC(FormVect.brakingСurrentC());

//                  Вывод на графики уставки
                    Charts.addAnalogData(1, 1, logicB.srab(rms.getBrakingCurrB(), rms.getSumValueB(), rms.getSumValueBg2()));
                    Charts.addAnalogData(0, 1, logicA.srab(rms.getBrakingCurrA(), rms.getSumValueA(), rms.getSumValueAg2()));
                    Charts.addAnalogData(2, 1, logicC.srab(rms.getBrakingCurrC(), rms.getSumValueC(), rms.getSumValueCg2()));

//                  Вывод на графики дифф тока
                    Charts.addAnalogData(0, 0, rms.getSumValueA());
                    Charts.addAnalogData(1, 0, rms.getSumValueB());
                    Charts.addAnalogData(2, 0, rms.getSumValueC());
                }
//          Возможна проблема ввода-вывода (то есть мы можем получить на вход не то, чего ожидаем и неправильно обработаем).
            } catch (IOException e) {
                e.printStackTrace();
            }
//      Ошибка отсутствия файла
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
