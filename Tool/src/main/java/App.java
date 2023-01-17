
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

/**
 * 做一个每月攒钱规划工具！
 */
public class App {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final String unit = Currency.getInstance(Locale.CHINA).getSymbol();

    public static void main(String[] args) throws ParseException {
        System.out.println("Hello World!");
        System.out.print(saveMoney("2021-12-10", "365", "1", "0.8"));
    }

    /**
     * @param date 日期
     * @return 第二天日期
     */
    private static Date nextDay(Date date) {
        long daytime = 24 * 60 * 60 * 1000;

        return new Date(date.getTime() + daytime);
    }

    /**
     * @param startDay       开始日期
     * @param dayCount       存款天数
     * @param startAmount    起始金额
     * @param intervalAmount 间隔金额
     * @return 当前打印日期，金额
     * @throws ParseException 解析字符串
     */
    private static String saveMoney(String startDay, String dayCount, String startAmount, String intervalAmount) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(startDay);
        BigDecimal money = new BigDecimal(startAmount);
        // 初始化0
        BigDecimal[] all = new BigDecimal[12];
        for (int i = 0; i < all.length; i++) {
            all[i] = new BigDecimal(0);
        }
        BigDecimal all_ = new BigDecimal(0);
        countMonth(all, date, money);
        for (int i = 0; i < Integer.parseInt(dayCount) - 1; i++) {
            money = money.add(new BigDecimal(intervalAmount));

            date = nextDay(date);
            countMonth(all, date, money);
            if (i == Integer.parseInt(dayCount) - 2) {
                int lastMonthIndex = getMonth(date) - 1;
                System.err.println("_____最后月份______" + (lastMonthIndex + 1) + "月总计：" + all[lastMonthIndex] + unit);
                for (BigDecimal j : all) {
                    all_ = all_.add(j);
                }
                System.err.println("总计：" + all_ + unit);
            }

        }
        return "";
    }

    /**
     * @param all   计算存钱数
     * @param date  日期
     * @param money 当前日期存钱数
     */
    private static void countMonth(BigDecimal[] all, Date date, BigDecimal money) {
        //等待20ms
        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //打印当前日期和钱数
        System.out.println(sdf.format(date) + " " + money + unit);
        int monthIndex = getMonth(date) - 1;
        all[monthIndex] = all[monthIndex].add(money);
        if (monthIndex != getMonth(nextDay(date)) - 1)
            System.err.println("___________" + getYear(date) + "年" + (monthIndex + 1) + "月总计：" + all[monthIndex] + unit);
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return 返回日期年份
     */
    private static Integer getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取日期月份
     *
     * @param date 日期
     * @return 返回日期月份
     */
    private static Integer getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        return month + 1;
    }

}
