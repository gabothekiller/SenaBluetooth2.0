package app.akexorcist.bluetoothspp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends Activity {
    CustomGraph graphTemperatura;
    CustomGraph graphOxigeno;//    ArrayList<Object[]> graphData;
    ArrayList<GraphDataPackage> graphData;
    LineGraphSeries<DataPoint> seriesSensor1 = new LineGraphSeries<>( new DataPoint[]{} );
    LineGraphSeries<DataPoint> seriesSensor2 = new LineGraphSeries<>( new DataPoint[]{} );

    final static private String TAG = "app.akexorcist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Log.i(TAG, "on Graph Activity");

        Bundle TerminalData = getIntent().getExtras();
        if (TerminalData == null){
            return;
        }
        // TODO
//        graphData = (ArrayList<Object[]>) TerminalData.get("graphData");
        graphData = (ArrayList<GraphDataPackage>) TerminalData.get("graphSomethin");

        makeDayGraphSensor1();
        makeDayGraphSensor2();
        setDayData();
    }

    private void makeDayGraphSensor1(){
        graphTemperatura = new CustomGraph( (GraphView) findViewById(R.id.graphSensor1) );
        graphTemperatura.setup(getString(R.string.TituloGraphSensor1), getString(R.string.GraphEjeHorozontal));
        graphTemperatura.set_Y_axis();

        GraphDataPackage firstElement = graphData.get(0);
        GraphDataPackage lastElement = graphData.get(graphData.size() - 1);
        int firstHour = convertToDay( firstElement.date );
        int lastHour =  convertToDay( lastElement.date);

        graphTemperatura.set_X_Axis(firstHour, lastHour);
        graphTemperatura.style();
        graphTemperatura.styleSeries(seriesSensor1, getString(R.string.TituloGraphSensor1), Color.BLUE);
        graphTemperatura.addSeries(seriesSensor1);
        graphTemperatura.setFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                int data = (int) value;
                if (isValueX) {
                    String str = String.valueOf(data);
                    try {
                        str = String.format("%04d", data);
                        return str.substring(0, 2) + ":" + str.substring(2);
                    } catch (Exception e) {
                        return str;
                    }
                } else {
                    return String.format("%d \u2103",data);
                }
            }
        });
    }


    private void makeDayGraphSensor2(){
        graphOxigeno = new CustomGraph( (GraphView) findViewById(R.id.graphSensor2) );
        graphOxigeno.setup(getString(R.string.TituloGraphSensor2), getString(R.string.GraphEjeHorozontal));
        graphOxigeno.set_Y_axis();

        GraphDataPackage firstElement2 = graphData.get(0);
        GraphDataPackage lastElement2 = graphData.get(graphData.size() - 1);
        int firstHour2 = convertToDay( firstElement2.date );
        int lastHour2 =  convertToDay( lastElement2.date);

        graphOxigeno.set_X_Axis(firstHour2, lastHour2);
        graphOxigeno.style();
        graphOxigeno.styleSeries(seriesSensor2, getString(R.string.TituloGraphSensor2), Color.RED);
        graphOxigeno.addSeries(seriesSensor2);
        graphOxigeno.setFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                int data = (int) value;
                if (isValueX) {
                    String str = String.valueOf(data);
                    try {
                        str = String.format("%04d", data);
                        return str.substring(0, 2) + ":" + str.substring(2);
                    } catch (Exception e) {
                        return str;
                    }
                } else {
                    return String.format("%d ppm", data);
                }
            }
        });
    }

    void setDayData(){
        for (GraphDataPackage line : graphData){
            //TODO
            int dia = convertToDay( line.date);
            seriesSensor1.appendData(new DataPoint( dia , line.sensor1), true, 9999 );
            seriesSensor2.appendData(new DataPoint( dia , line.sensor2), true, 9999 );
            Log.i(TAG, "sensor 1 : " + line.sensor1 + "    Sensor 2 : " + line.sensor2);
        }
    }

    static int convertToDay(String date){
        // DateAndHour = [ "15/10/2015" , "14:25"  ]
        String[] DateAndHour  = date.split("-");
        String[] HourAndMinute = DateAndHour[1].split(":");
        String hour = HourAndMinute[0];
        String minute = HourAndMinute[1];
        String formatMinute = String.format("%02d", Integer.valueOf(minute));
        return Integer.valueOf(hour + formatMinute);
    }
}





