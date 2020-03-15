package com.crusher.work.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
public class WorkshopRestController{
    public static class Equation{
        private Double x1;
        private Double x2;
        private String error;
        public Double getX1() {
            return x1;
        }

        public Double getX2() {
            return x2;
        }

        public String getError() {
            return error;
        }

        public Equation getQuadraticEquation(Double a, Double b, Double c){
            Double discriminant = b * b - 4 * a * c;
            if (discriminant < 0){
                error = "Уравнение не имеет корней!";
            } else if (discriminant == 0) {
                this.x1 = - b / (2 * a);
            }
            else{
                this.x1 = (- b - Math.sqrt(discriminant)) / (2 * a);
                this.x2 = (- b + Math.sqrt(discriminant)) / (2 * a);
            }
            return this;
        }
    }

    public static class Num{
        static String[] str1 = { ""," один"," два"," три"," четыре"," пять"," шесть"," семь"," восемь"," девять"};
        static String[] str100= {""," сто"," двести"," триста"," четыреста"," пятьсот"," шестьсот"," семьсот", " восемьсот"," девятьсот"};
        static String[] str11 = {" десять"," одиннадцать"," двенадцать"," тринадцать"," четырнадцать", " пятнадцать"," шестнадцать"," семнадцать"," восемнадцать"," девятнадцать"};
        static String[] str10 = {""," десять"," двадцать"," тридцать"," сорок"," пятьдесят"," шестьдесят", " семьдесят"," восемьдесят"," девяносто"};
        public static String toString(Integer number){
            String numberAsString = "";
            if (number == 0){
                return "ноль";
            }
            if (number < 0){
                numberAsString += "минус ";
                number = number * - 1;
            }
            if ((number / 1000000) > 0){
                if ((number / 1000000) == 1){
                    numberAsString += "Один миллион";
                }
                else {
                    numberAsString += toString(number / 1000000) + " миллион(-а|-ов)";
                }
                number %= 1000000;
            }
            if ((number / 1000) > 0){
                if ((number / 1000) == 1){
                    numberAsString += " одна тысяча";
                }
                else {
                    numberAsString += toString(number / 1000) + " тысяч(и)";
                }
                number %= 1000;
            }
            if ((number / 100) > 0){
                numberAsString += str100[number / 100];
                number %= 100;
            }
            if (number >= 20){
                numberAsString += str10[number / 10];
                number %= 10;
            }
            if (number >= 10){
                numberAsString += str11[number % 10];
                number %= 10;
            }
            numberAsString += str1[number];
            return numberAsString;
        }
    }

    @RequestMapping(value = "{number}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getString(@PathVariable("number") Integer number){
        return Num.toString(number);
    }

    @RequestMapping(value = "/parameters", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Equation getEquation(Double a, Double b, Double c){
        return new Equation().getQuadraticEquation(a, b, c);
    }

    @RequestMapping(value = "/weekdays", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getWeekday(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.M.yyyy");
        Date parseDate = simpleDateFormat.parse(date);
        DateFormat strWeekdayFormat = new SimpleDateFormat("EEEE");
        String weekday = strWeekdayFormat.format(parseDate);
        return weekday;
    }

    @RequestMapping(value = "/fibonacci/{index}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public long getSequence(@PathVariable("index") Integer index){
        Double param1 = Math.pow(((1 + Math.sqrt(5)) / 2.0), index);
        Double param2 = Math.pow(((1 - Math.sqrt(5)) / 2.0), index);
        Double fib = (param1 - param2) / Math.sqrt(5);
        return Math.round(fib);
    }

    @RequestMapping(value = "/regions/{region}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRegion(@PathVariable("region") Integer regionCode){
        HashMap<Integer, String> regions = new HashMap<>();
        regions.put(1, "Республика Адыгея (Адыгея)");
        regions.put(2,	"Республика Башкортостан");
        regions.put(3,	"Республика Бурятия");
        regions.put(4,	"Республика Алтай");
        regions.put(5,	"Республика Дагестан");
        regions.put(6,	"Республика Ингушетия");
        regions.put(7,	"Кабардино-Балкарская Республика");
        regions.put(8,	"Республика Калмыкия");
        regions.put(9,	"Карачаево-Черкесская Республика");
        regions.put(10,	"Республика Карелия");
        regions.put(11,	"Республика Коми");
        regions.put(12,	"Республика Марий Эл");
        regions.put(13,	"Республика Мордовия");
        regions.put(14,	"Республика Саха (Якутия)");
        regions.put(15,	"Республика Северная Осетия - Алания");
        regions.put(16,	"Республика Татарстан (Татарстан)");
        regions.put(17,	"Республика Тыва");
        regions.put(18,	"Удмуртская Республика");
        regions.put(19,	"Республика Хакасия");
        regions.put(20,	"Чеченская Республика");
        regions.put(21,	"Чувашская Республика - Чувашия");
        regions.put(22,	"Алтайский край");
        regions.put(23,	"Краснодарский край");
        regions.put(24,	"Красноярский край");
        regions.put(25,	"Приморский край");
        regions.put(26,	"Ставропольский край");
        regions.put(27,	"Хабаровский край");
        regions.put(28,	"Амурская область");
        regions.put(29,	"Архангельская область");
        regions.put(30,	"Астраханская область");
        regions.put(31,	"Белгородская область");
        regions.put(32,	"Брянская область");
        regions.put(33,	"Владимирская область");
        regions.put(34,	"Волгоградская область");
        regions.put(35,	"Вологодская область");
        regions.put(36,	"Воронежская область");
        regions.put(37,	"Ивановская область");
        regions.put(38,	"Иркутская область");
        regions.put(39,	"Калининградская область");
        regions.put(40,	"Калужская область");
        regions.put(41,	"Камчатский край");
        regions.put(42,	"Кемеровская область");
        regions.put(43,	"Кировская область");
        regions.put(44,	"Костромская область");
        regions.put(45,	"Курганская область");
        regions.put(46,	"Курская область");
        regions.put(47,	"Ленинградская область");
        regions.put(48,	"Липецкая область");
        regions.put(49,	"Магаданская область");
        regions.put(50,	"Московская область");
        regions.put(51,	"Мурманская область");
        regions.put(52,	"Нижегородская область");
        regions.put(53,	"Новгородская область");
        regions.put(54,	"Новосибирская область");
        regions.put(55,	"Омская область");
        regions.put(56,	"Оренбургская область");
        regions.put(57,	"Орловская область");
        regions.put(58,	"Пензенская область");
        regions.put(59,	"Пермский край");
        regions.put(60,	"Псковская область");
        regions.put(61,	"Ростовская область");
        regions.put(62,	"Рязанская область");
        regions.put(63,	"Самарская область");
        regions.put(64,	"Саратовская область");
        regions.put(65,	"Сахалинская область");
        regions.put(66,	"Свердловская область");
        regions.put(67,	"Смоленская область");
        regions.put(68,	"Тамбовская область");
        regions.put(69,	"Тверская область");
        regions.put(70,	"Томская область");
        regions.put(71,	"Тульская область");
        regions.put(72,	"Тюменская область");
        regions.put(73,	"Ульяновская область");
        regions.put(74,	"Челябинская область");
        regions.put(75,	"Забайкальский край");
        regions.put(76,	"Ярославская область");
        regions.put(77,	"г. Москва");
        regions.put(78,	"Санкт-Петербург");
        regions.put(79,	"Еврейская автономная область");
        regions.put(83,	"Ненецкий автономный округ");
        regions.put(86,	"Ханты-Мансийский автономный округ - Югра");
        regions.put(87,	"Чукотский автономный округ");
        regions.put(89,	"Ямало-Ненецкий автономный округ");
        regions.put(91,	"Республика Крым");
        regions.put(92,	"Севастополь");
        regions.put(99,	"Иные территории, включая город и космодром Байконур");

        return regions.get(regionCode);
    }
}