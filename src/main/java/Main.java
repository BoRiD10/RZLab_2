public class Main {
    public static void main(String[] args) {
//        ArrayList<String> signalName = new ArrayList<>();
//
//        for (int n = 1; n < 6; n++){
//            for (char c = 'A'; c <= 'C'; c++) {
//                signalName.add(c + String.valueOf(n));
//            }
//        }
//
//        for (int i = 0; i < 15; i ++){
//            Charts.createAnalogChart(signalName.get(i), i);
//            Charts.addSeries(signalName.get(i), i, 0);
//        }

//      Создание необходиммых графиков
        Charts.createAnalogChart("Фаза A", 0);
        Charts.addSeries("Дифф ток A", 0, 0);
        Charts.addSeries("Уставка А", 0, 1);

        Charts.createAnalogChart("Фаза B", 1);
        Charts.addSeries("Дифф ток B", 1, 0);
        Charts.addSeries("Уставка B", 1, 1);

        Charts.createAnalogChart("Фаза C", 2);
        Charts.addSeries("Дифф ток C", 2, 0);
        Charts.addSeries("Уставка C", 2, 1);

        Charts.createDiscreteChart("Срабатывание", 0);

//      Запуск основного метода
        ParseComtr parComtr = new ParseComtr();
        parComtr.start();
    }
}
