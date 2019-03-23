package com.bs.express.utils;

import android.app.Activity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    //获取当前日期
    public static String getDate() {
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(day);
    }

    public static String getYesterdayData() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return sdf.format(date);
    }

    public static String getTodayDate() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date day=new Date();
        return sdf.format(day);
    }

    public static void showDatePicker(Activity activity, final TextView textView, String selectTime, final OnDateChangeListener listener){
//        String initEndDateTime = "2014-8-23"; // 初始化时间
        final String initEndDateTime=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                activity, initEndDateTime, selectTime);
        dateTimePicKDialog.dateTimePicKDialog(new DateTimePickDialogUtil.OnTimeClickListener() {
            @Override
            public void onSetClick(String time) {
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(time);
                    Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(initEndDateTime);
                    if(date1.getTime()<date2.getTime()){
                        textView.setText(initEndDateTime);
                    }else{
                        textView.setText(time);
                        if (listener != null) {
                            listener.change(time);
                        }
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onCancel() {

            }
        });
    }
    public interface OnDateChangeListener {
        void change(String time);
    }
}
