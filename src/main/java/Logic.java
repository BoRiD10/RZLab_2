public class Logic {
//  Задаем уставки
    private double Ido = 1.0442; //Уставка по дифф току
    private double s = 0.53; //Коэф торможения
    private double It0 = Ido / s; //Ток начала торможения
    private OutputData outputData = new OutputData(); //экземпляр классы выходных данных

//  Логика срабатывания
    public double srab(double Itorm, double Idif, double Idif2){
        double israb = 0;
//      Формирование характеристики срабатывания
        if (Itorm < It0){
            israb = Ido;
        } else {
            israb = Ido + s * (Itorm - It0);
        }
//      Если дифф ток больше значения уставки и отношение второй гармоники к основной не более 15%, то защита срабатывает
        if (Idif > israb && (Idif2/Idif) < 0.2){
            outputData.trip(true);
//      Иначе не срабатывает
        }else {
            outputData.trip(false);
        }
        return israb;
    }
}
