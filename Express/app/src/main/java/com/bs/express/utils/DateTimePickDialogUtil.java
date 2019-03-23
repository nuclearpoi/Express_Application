package com.bs.express.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;


import com.bmob.express.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimePickDialogUtil implements OnDateChangedListener,
        OnTimeChangedListener {
    private DatePicker datePicker;
//    private TimePicker timePicker;
    private AlertDialog ad;
    private String dateTime;
    private String initDateTime;
    private Activity activity;
    private String selectTime;

    /**
     * 日期时间弹出选择框构造函数
     *
     * @param activity
     *            ：调用的父activity
     * @param initDateTime
     *            初始日期时间值，作为弹出窗口的标题和日期时间初始值
     */
    public DateTimePickDialogUtil(Activity activity, String initDateTime, String selectTime) {
        this.activity = activity;
        this.initDateTime = initDateTime;
        this.selectTime = selectTime;

    }

    public void init(DatePicker datePicker, TimePicker timePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initDateTime || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH)
                    ;
        }

        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
//        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
//        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }
    public void init(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initDateTime || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = calendar.get(Calendar.YEAR) + "年"
                    + calendar.get(Calendar.MONTH) + "月"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "日 "
                    + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                    + calendar.get(Calendar.MINUTE);
        }

        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);


    }
    public interface OnTimeClickListener{
        void onSetClick(String time);
        void onCancel();
    }
    /**
     * 弹出日期时间选择框方法
     *
     *            :为需要设置的日期时间文本编辑框
     * @return
     */
    public AlertDialog dateTimePicKDialog(final OnTimeClickListener listener) {
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.common_datetime, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        datePicker.setMinDate(new Date().getTime());
        init(datePicker);
//        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
//        init(datePicker, timePicker);
//        timePicker.setIs24HourView(true);
//        timePicker.setOnTimeChangedListener(this);

        ad = new AlertDialog.Builder(activity)
                .setTitle(initDateTime)
                .setView(dateTimeLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(listener!=null)
                            listener.onSetClick(dateTime);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(listener!=null)
                            listener.onCancel();
                    }
                }).show();

        onDateChanged(null, 0, 0, 0);

        if (!TextUtils.isEmpty(selectTime)) {
            String[] split = selectTime.split("-");
            datePicker.updateDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]) - 1,
                    Integer.parseInt(split[2]));
        }
        return ad;
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), 0,
                0);
//        calendar.set(datePicker.getYear(), datePicker.getMonth(),
//                datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
//                timePicker.getCurrentMinute());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        dateTime = sdf.format(calendar.getTime());
        ad.setTitle(dateTime);
    }

    /**
     * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
     *
     * @param initDateTime
     *            初始日期时间值 字符串型
     * @return Calendar
     */
    private Calendar getCalendarByInintData(String initDateTime) {
        Calendar calendar = Calendar.getInstance();

        // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
//        String date = spliteString(initDateTime, "日", "index", "front"); // 日期
//        String time = spliteString(initDateTime, "日", "index", "back"); // 时间
//
//        String yearStr = spliteString(date, "年", "index", "front"); // 年份
//        String monthAndDay = spliteString(date, "年", "index", "back"); // 月日
//
//        String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月
//        String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 日
//
//        String hourStr = spliteString(time, ":", "index", "front"); // 时
//        String minuteStr = spliteString(time, ":", "index", "back"); // 分

//        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
//        int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
//        int currentDay = Integer.valueOf(dayStr.trim()).intValue();
//        int currentHour = Integer.valueOf(hourStr.trim()).intValue();
//        int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();
//        String[] strs=initDateTime.split("-");
//        String yearStr=strs[0];
//        String monthStr=strs[1];
//        String dayStr=strs[2];
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(initDateTime);
            calendar.setTime(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
//        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
//        int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
//        int currentDay = Integer.valueOf(dayStr.trim()).intValue();
//        calendar.set(currentYear, currentMonth, currentDay);
        return calendar;
    }

    /**
     * 截取子串
     *
     * @param srcStr
     *            源串
     * @param pattern
     *            匹配模式
     * @param indexOrLast
     * @param frontOrBack
     * @return
     */
    public static String spliteString(String srcStr, String pattern,
                                      String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
        } else {
            loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); // 截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
        }
        return result;
    }

}
